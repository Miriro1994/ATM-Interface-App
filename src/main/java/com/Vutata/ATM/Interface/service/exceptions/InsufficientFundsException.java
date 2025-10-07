package com.Vutata.ATM.Interface.service.exceptions;

public class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(String message) {
    super(message);
  }
}
