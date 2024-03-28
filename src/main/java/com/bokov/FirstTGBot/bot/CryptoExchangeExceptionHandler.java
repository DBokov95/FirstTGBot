package com.bokov.FirstTGBot.bot;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class CryptoExchangeExceptionHandler {

    @ExceptionHandler(CryptoExchangeException.class)
    public ResponseEntity<String> handleTelegramAPIException(CryptoExchangeException ex) {
        log.error("Crypto Exchange API Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error communicating with Crypto Exchange API");
    }
}
