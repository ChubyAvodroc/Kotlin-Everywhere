package dev.chuby.ke_backend_java.error;

import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    // 400

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
        final String bodyOfResponse = "This is a 400, Motherfuckers!, ConstraintViolationException";
        return new ResponseEntity<Object>(new CustomError(ex.getMessage(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        final String bodyOfResponse = "This is a 400, Motherfuckers!, DataIntegrityViolation";
        return new ResponseEntity<Object>(new CustomError(ex.getMessage(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String bodyOfResponse = "This is a 400 dude, your message is not readable, check it";
        // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
        return new ResponseEntity<Object>(new CustomError(ex.getMessage(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String bodyOfResponse = "This 400 is worst, you are sending additional parameter asshole";
        return new ResponseEntity<Object>(new CustomError(ex.getMessage(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }


    // 404

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "This shit doesn't exist, maybe i was too high to create it";
        return new ResponseEntity<Object>(new CustomError(ex.getMessage(), bodyOfResponse), HttpStatus.NOT_FOUND);
    }

    // 409

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "How did you get here?, go away";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 412

    // 500

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    @ResponseBody
    /*500*/ public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final String bodyOfResponse = "Sorry, my bad, i will fix it soon... or not";
        return new ResponseEntity<Object>(new CustomError(ex.getMessage(), bodyOfResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}