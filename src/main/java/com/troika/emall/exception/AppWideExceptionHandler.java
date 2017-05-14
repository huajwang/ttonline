package com.troika.emall.exception;

import javax.persistence.PersistenceException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler {

  @ExceptionHandler(PersistenceException.class)
  public String handlePersistenceException() {
	  // TODO
    return "error/duplicate";
  }
  
  @ExceptionHandler(NumberFormatException.class)
  public String handleNumberFormatException() {
	  return "error/numberformat";
  }

}
