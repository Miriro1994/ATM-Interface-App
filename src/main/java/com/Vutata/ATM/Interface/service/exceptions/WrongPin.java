package com.Vutata.ATM.Interface.service.exceptions;

public class WrongPin extends RuntimeException {
    public WrongPin(String message) {
        super(message);
    }
}
