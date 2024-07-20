package br.paliar.mayarafurtado.backend.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.paliar.mayarafurtado.backend.dto.ErrorDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Order
@Log4j2
@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlerException(Exception e) {

        ErrorDTO errorCaptured =
                ErrorDTO.builder() //
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()) //
                        .details(e.getMessage())
                        .build();

        log.info("Erros", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorCaptured);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlerException(NoSuchElementException e) {

        ErrorDTO errorCaptured =
                ErrorDTO.builder() //
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase()) //
                        .details(e.getMessage())
                        .build();

        log.info("Erros", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorCaptured);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlerException(MethodArgumentNotValidException e) {

        List<String> constraintsViolated = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage())
                .collect(Collectors.toList());

        constraintsViolated.addAll(
                e.getBindingResult().getGlobalErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList())
        );

        ErrorDTO erroCapturado = ErrorDTO.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .details(constraintsViolated.toString())
                .build();


        log.info("Erros", constraintsViolated);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroCapturado);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlerException(IllegalArgumentException e) {

            ErrorDTO erroCapturado =
                    ErrorDTO.builder() //
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase()) //
                            .details(e.getMessage())
                            .build();

            log.info("Erros", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroCapturado);
    }
    
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlerException(IllegalStateException e) {
        
        ErrorDTO erroCapturado =
                ErrorDTO.builder() //
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase()) //
                        .details(e.getMessage())
                        .build();

        log.info("Erros", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroCapturado);

    }

}
