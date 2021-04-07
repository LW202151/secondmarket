package com.Secondgood.secondhang.good.exceptions;

public class GoodNotFoundException extends SecondRuntimeException {

    private String noFoundId;

    public GoodNotFoundException(String message, String noFoundId) {
        super(message);
        this.noFoundId = noFoundId;
    }

    @Override
    public String getMessage() {
        return "物品" + this.noFoundId + "未找到";
    }
}
