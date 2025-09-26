package ru.itmo.se.is.exception;


import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Собираем все ошибки в одну строку или JSON
        String errors = exception.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + " " + cv.getMessage())
                .collect(Collectors.joining("; "));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errors) // можно вернуть JSON вместо строки
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
