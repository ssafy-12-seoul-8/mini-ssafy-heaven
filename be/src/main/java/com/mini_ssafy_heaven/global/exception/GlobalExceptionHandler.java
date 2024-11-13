package com.mini_ssafy_heaven.global.exception;

import java.util.NoSuchElementException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String UNHANDLED = "서버에서 불명확한 예외가 발생했습니다.";

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
    log.warn(exception.getMessage());

    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.badRequest()
        .body(response);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchElement(NoSuchElementException exception) {
    log.warn(exception.getMessage());

    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(response);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ErrorResponse> handleConflict(DuplicateKeyException exception) {
    log.warn(exception.getMessage());

    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnhandledException(Exception exception) {
    log.error(exception.getMessage(), exception);

    ErrorResponse response = new ErrorResponse(UNHANDLED);

    return ResponseEntity.internalServerError()
        .body(response);
  }

}
