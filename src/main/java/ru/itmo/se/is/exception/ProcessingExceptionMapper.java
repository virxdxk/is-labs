package ru.itmo.se.is.exception;

import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {
    @Override
    public Response toResponse(ProcessingException exception) {
        if (containsJsonbException(exception))
            return processJsonbException((JsonbException) exception.getCause());
        return processProcessingException(exception);
    }

    private Response processJsonbException(JsonbException exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Invalid JSON")
                .detail(exception.getMessage())
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .code(ErrorCode.INVALID_JSON.name())
                .build();
        return Response.status(Response.Status.BAD_REQUEST).entity(problemDetail).build();
    }

    private boolean containsJsonbException(ProcessingException exception) {
        if (exception.getCause() == null) return false;
        return exception.getCause() instanceof JsonbException;
    }

    private Response processProcessingException(ProcessingException exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Processing error")
                .detail("An error occurred while processing the request")
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .code(ErrorCode.PROCESSING_ERROR.name())
                .build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(problemDetail).build();
    }
}
