package com.example.parking.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(String sessionId) {
        super("Сессия " + sessionId + " не найдена");
    }
}
