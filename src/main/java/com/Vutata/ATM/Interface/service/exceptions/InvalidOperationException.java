package com.Vutata.ATM.Interface.service.exceptions;

public class InvalidOperationException extends RuntimeException {
  public InvalidOperationException(String message) {
    super(message);
  }
}
