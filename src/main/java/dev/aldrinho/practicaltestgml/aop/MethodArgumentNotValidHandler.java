package dev.aldrinho.practicaltestgml.aop;

import dev.aldrinho.practicaltestgml.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class MethodArgumentNotValidHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handlerValid(MethodArgumentNotValidException exception) {
        log.info("Entrando a handlerValid");
        log.error("Ha ocurrido un error {}", exception.getMessage());

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(ResponseDto
                .builder()
                .data(errors)
                .message("You have %s errors " + errors.size())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
