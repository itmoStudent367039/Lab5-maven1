package commands;

import builders.ProductDirector;
import exceptions.ValidException;
import lombok.extern.slf4j.Slf4j;
import products.Product;
import util.CommandType;
import util.Response;
import util.Transit;
import util.TypeResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class Client<T extends Serializable> {
    private final ProductDirector productDirector;
    private String password;
    private String login;
    private SocketChannel channel;
    private final SocketAddress address;
    private final List<Transit<T>> requests = new ArrayList<>();
    private final List<String> requestsHistory = new ArrayList<>();
    private static final int EIGHT_KILOBYTES = 8192;
    private final ByteBuffer readWriteBuffer = ByteBuffer.allocate(EIGHT_KILOBYTES);

    public Client(ProductDirector productDirector, String sendHostName, int sendPort) throws IOException {
        this.productDirector = productDirector;
        address = new InetSocketAddress(sendHostName, sendPort);
    }

    public String getHistory() {
        if (requestsHistory.size() > 11) {
            return requestsHistory.stream()
                    .skip(requestsHistory.size() - 11)
                    .collect(Collectors.joining(System.lineSeparator()));
        } else {
            if (requestsHistory.isEmpty()) {
                return "Empty commands' history";
            } else {
                return requestsHistory.stream()
                        .collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    public void addHistory(String history) {
        requestsHistory.add(history);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void showPendingRequests() {
        for (Transit<T> request : requests) {
            if (Objects.isNull(request.getObject())) {
                System.out.printf("%d) %s \n", requests.indexOf(request), request.getCommand().getName());
            } else {
                System.out.printf("%d) %s - %s \n", requests.indexOf(request), request.getCommand().getName(), Arrays.toString(request.getObject()));
            }
        }
    }

    public Response sendPendingRequest(int value) throws IOException {
        Transit<T> object = requests.remove(value);
        return send(object.getCommand(), object.getObject());
    }

    public Product createProduct(String... args) throws ValidException, InvocationTargetException, IllegalAccessException {
        return productDirector.buildProduct(args);
    }

    private void sendMessage(ByteBuffer byteBuffer) throws IOException {
        this.channel.write(byteBuffer);
    }

    private void connect() throws IOException {
        channel = SocketChannel.open();
        channel.connect(address);
        channel.configureBlocking(false);
    }

    void reConnect() throws IOException {
        channel.close();
        connect();
    }

    public Response register() {
        return send(CommandType.REGISTER, null);
    }

    public Response logIn() {
        return send(CommandType.LOG_IN, null);
    }

    private Response receiveMessage() throws IOException, ClassNotFoundException {

        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        int readBytes = 0;

        while (readBytes == 0) {
            readBytes = this.channel.read(lengthBuffer);
        }

        lengthBuffer.flip();
        int countOfParts = lengthBuffer.getInt();
        List<byte[]> data = new ArrayList<>();
        readByteArraysFromChannelToList(data, countOfParts);
        return deserializeMessage(mergeByteArrays(data));
    }
    private void readByteArraysFromChannelToList(List<byte[]> list, int countOfParts) throws IOException {

        int readData = 0;

        while (countOfParts > 0) {

            readWriteBuffer.clear();

            while (readData == 0) {
                readData = this.channel.read(readWriteBuffer);
            }

            readWriteBuffer.flip();
            byte[] part = new byte[readData];
            System.arraycopy(readWriteBuffer.array(), 0, part, 0, readData);
            list.add(part);
            countOfParts--;
            readData = 0;

        }
    }

    private byte[] mergeByteArrays(List<byte[]> data) {
        int totalLength = 0;
        for (byte[] array : data) {
            totalLength += array.length;
        }
        byte[] result = new byte[totalLength];
        int currentIndex = 0;
        for (byte[] array : data) {
            System.arraycopy(array, 0, result, currentIndex, array.length);
            currentIndex += array.length;
        }
        return result;
    }

    public Response send(CommandType type, T[] args) {
        try {

            if (Objects.isNull(channel)) {
                connect();
            }

            if (!channel.isOpen()) {
                reConnect();
            }

            Transit<T> object = packCommandToTransit(type, args);

            if (object.getCommand() != CommandType.REGISTER && object.getCommand() != CommandType.LOG_IN) {
                requestsHistory.add(object.getCommand().getName());
            }

            ByteBuffer buffer = serializeTransit(object);
            sendMessage(buffer);
            return receiveMessage();

        } catch (IOException e) {
            return new Response(TypeResponse.EMPTY_CONNECTION_WITH_SERVER, e.getMessage());
        } catch (ClassNotFoundException e) {
            return new Response(TypeResponse.BAD_RESPONSE, e.getMessage());
        }
    }

    public boolean checkConnection() {
        Response response = send(CommandType.HEAD, null);
        if (response.getResponse() == TypeResponse.EMPTY_REGISTRATION) {
            return logIn().getResponse() == TypeResponse.LOG_IN;
        } else return response.getResponse() != TypeResponse.BAD_RESPONSE &&
                response.getResponse() != TypeResponse.EMPTY_CONNECTION_WITH_SERVER;
    }

    private Transit<T> packCommandToTransit(CommandType type, T[] args) {
        return new Transit<>(type, args, login, password);
    }

    private ByteBuffer serializeTransit(Transit<T> object) throws IOException {
        var byteStream = new ByteArrayOutputStream();
        try (var objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(object);
            return ByteBuffer.wrap(byteStream.toByteArray());
        }
    }

    private Response deserializeMessage(byte[] data) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (Response) stream.readObject();
        }
    }

    public void exit() {
        System.out.println("Good Bye!");
        System.exit(0);
    }
}
