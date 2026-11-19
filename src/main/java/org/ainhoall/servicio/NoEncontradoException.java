package org.ainhoall.servicio;

public class NoEncontradoException extends RuntimeException{
    public NoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
