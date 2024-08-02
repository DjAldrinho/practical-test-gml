package dev.aldrinho.practicaltestgml.aop;

import dev.aldrinho.practicaltestgml.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        log.info("Entrando a handleGlobalException");
        log.error("Ha ocurrido un error {}", ex.getMessage());
        return new ResponseEntity<>(
                ResponseDto
                        .builder()
                        .message("Ha ocurrido un error: " + ex.getMessage())
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
