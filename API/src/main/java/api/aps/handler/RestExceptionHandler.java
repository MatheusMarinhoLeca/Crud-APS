package api.logap.handler;

import api.logap.exception.*;
import jakarta.annotation.Nullable;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre){
    return new ResponseEntity<>(BadRequestExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .titulo("Bad Request, cheque a documentação")
            .detalhes(bre.getMessage())
            .mensagemDev(bre.getClass().getName())
            .build(),HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields =  fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(ValidationExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .titulo("Bad Request,campos inválidos")
                .detalhes(exception.getMessage())
                .mensagemDev(exception.getClass().getName())
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .build(),HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ConstraintViolationExceptionDetails> handlerConstraintViolationException(
            ConstraintViolationException exception){
        return new ResponseEntity<>(ConstraintViolationExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .titulo("Bad Request,violação de requisitos")
                .detalhes(exception.getMessage())
                .mensagemDev(exception.getClass().getName())
                .build(),HttpStatus.BAD_REQUEST);


    }


    @ExceptionHandler()
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .titulo(ex.getCause().getMessage())
                .detalhes(ex.getMessage())
                .mensagemDev(ex.getClass().getName())
                .build();


        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
