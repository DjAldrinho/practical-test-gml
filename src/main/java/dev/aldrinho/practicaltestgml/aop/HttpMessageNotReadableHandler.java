package dev.aldrinho.practicaltestgml.aop;

import dev.aldrinho.practicaltestgml.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class HttpMessageNotReadableHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> handlerNoBody(HttpMessageNotReadableException exception) {

        log.info("Entrando a handlerNoBody");
        log.error("Ha ocurrido un error {}", exception.getMessage());

        return new ResponseEntity<>(ResponseDto
                .builder()
                .message("Ha ocurrido un error: " + exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
