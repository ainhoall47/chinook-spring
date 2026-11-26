package org.ainhoall.controlador;

import org.ainhoall.servicio.NoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(NoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String noEncontrado(NoEncontradoException ex) {
        return "<h1>No encontrado</h1><p>" + Html.esc(ex.getMessage()) + "</p>";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String datoIncorrecto(MethodArgumentTypeMismatchException ex) {
        return "<h1>Petición incorrecta</h1><p>El valor «" + Html.esc(String.valueOf(ex.getValue()))
                + "» no es válido para " + Html.esc(ex.getName()) + ".</p>";
    }
}
