package com.Vutata.ATM.Interface.service.exceptions;

public class EmailExist extends RuntimeException {
    public EmailExist(String message) {
        super(message);
    }
}
