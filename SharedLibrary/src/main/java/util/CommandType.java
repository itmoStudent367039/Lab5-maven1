package util;

import java.io.Serializable;

public enum CommandType implements Serializable {
    ADD("add"),
    ADD_IF_MAX("add_if_max"),
    CLEAR("clear"),
    COUNT_LESS_MEASURE("count_less_measure"),
    GROUP_ELEMENTS_BY_NAME("group_products_by_name"),
    HEAD("head"),
    HELP("help"),
    HISTORY("history"),
    INFO("info"),
    PRINT_OWNERS("print_owners"),
    REMOVE_BY_ID("remove_by_id"),
    SHOW("show"),
    UPDATE_BY_ID("update"),
    EXECUTE_SCRIPT("execute_script"),
    REGISTER("register"),
    LOG_IN("log_in");

    private final String name;
    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
