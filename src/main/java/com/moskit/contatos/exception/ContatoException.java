package com.moskit.contatos.exception;

public class ContatoException extends RuntimeException {
    public ContatoException(String message) {
        super(message);
    }

    public ContatoException() {
        super();
    }
}
