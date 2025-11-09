package ru.itmo.se.is.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Validation failed")
                .detail("One or more validation errors occurred.")
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.VALIDATION_ERROR.name())
                .build();

        List<Violation> errors = exception.getConstraintViolations()
                .stream()
                .map(cv -> new Violation(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Entity not found")
                .detail(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .code(ErrorCode.NOT_FOUND.name())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(DeletionConflictException.class)
    public ResponseEntity<ProblemDetail> handleDeletionConflictException(DeletionConflictException exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Could not delete entity")
                .detail(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .code(ErrorCode.DELETION_CONFLICT.name())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Invalid JSON")
                .detail(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.INVALID_JSON.name())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .title("Processing error")
                .detail("An error occurred while processing the request")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCode.PROCESSING_ERROR.name())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    public static class Violation {
        private String field;
        private String message;

        public Violation() {
        }

        public Violation(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

