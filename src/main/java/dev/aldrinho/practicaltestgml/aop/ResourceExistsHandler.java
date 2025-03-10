package dev.aldrinho.practicaltestgml.aop;

import dev.aldrinho.practicaltestgml.dto.ResponseDto;
import dev.aldrinho.practicaltestgml.exceptions.ResourceExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ResourceExistsHandler {
    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<ResponseDto> handlerExistsByName(ResourceExistsException exception) {
        log.info("Entrando a handlerExistsByName");
        log.error("Ha ocurrido un error {}", exception.getMessage());

        return new ResponseEntity<>(ResponseDto
                .builder()
                .message(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
