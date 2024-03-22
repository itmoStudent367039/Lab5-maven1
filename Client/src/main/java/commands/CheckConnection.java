package commands;

import application.Authorization;
import exceptions.ExecuteException;
import util.Response;
import util.TypeResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;

public class CheckConnection extends Command {
    private final Client<Serializable> client;
    private final Authorization authorization;

    public CheckConnection(Client<Serializable> client, Authorization authorization) {
        this.client = client;
        this.authorization = authorization;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        if (client.checkConnection()) {
            System.out.println("Подключение активно");
        } else {
            try {
                client.reConnect();
                Response response = client.logIn();
                if (response.getResponse() == TypeResponse.LOG_IN) {
                    System.out.println("Произошло подключение к серверу");
                } else if (response.getResponse() == TypeResponse.BAD_RESPONSE) {
                    System.out.println(response.getMessage());
                } else if (response.getResponse() == TypeResponse.ALREADY_ACTIVE_SESSION) {
                    System.out.println(response.getMessage() + ". Можете попробовать войти или зарегестрироваться под другим аккаунтом");
                    try {
                        this.authorization.run(client);
                    } catch (NoSuchElementException e) {
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                System.out.println("Неудалось подключиться к серверу, попробуйте еще раз");
            }
        }
    }

    @Override
    public String getName() {
        return "connect";
    }
}
