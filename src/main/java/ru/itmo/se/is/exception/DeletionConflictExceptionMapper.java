package ru.itmo.se.is.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DeletionConflictExceptionMapper implements ExceptionMapper<DeletionConflictException> {
    @Override
    public Response toResponse(DeletionConflictException exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Could not delete entity")
                .detail(exception.getMessage())
                .status(Response.Status.CONFLICT.getStatusCode())
                .code(ErrorCode.DELETION_CONFLICT.name())
                .build();
        return Response.status(Response.Status.CONFLICT).entity(problemDetail).build();
    }
}
