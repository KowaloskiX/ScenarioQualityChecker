package pl.put.poznan.transformer.rest.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Catches errors from the REST controllers and turns them into JSON
 * responses with HTTP 400.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Logger for failed requests. */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles a request body that fails validation (for example a missing
     * title) and returns 400 with the list of problems.
     *
     * @param ex error thrown by Spring when validation fails
     * @return a 400 response with the error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult().getFieldErrors().stream()
                .map(GlobalExceptionHandler::formatFieldError)
                .collect(Collectors.toList());
        logger.info("Validation failed: {}", messages);
        return badRequest(messages);
    }

    /**
     * Handles a request body that is not valid JSON and returns 400.
     *
     * @param ex error thrown when the body cannot be read
     * @return a 400 response with a short error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        String detail = cause instanceof JsonProcessingException
                ? "Malformed JSON: " + ((JsonProcessingException) cause).getOriginalMessage()
                : "Malformed request body";
        logger.info("Unreadable request body: {}", detail);
        return badRequest(List.of(detail));
    }

    private static String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }

    private static ResponseEntity<Map<String, Object>> badRequest(List<String> messages) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("messages", messages);
        return ResponseEntity.badRequest().body(body);
    }
}
