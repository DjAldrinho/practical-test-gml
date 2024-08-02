package dev.aldrinho.practicaltestgml.aop;

import dev.aldrinho.practicaltestgml.dto.ResponseDto;
import dev.aldrinho.practicaltestgml.exceptions.ResourceExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceExistsHandler {
    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<ResponseDto> handlerExistsByName(ResourceExistsException exception) {

        return new ResponseEntity<>(ResponseDto
                .builder()
                .message(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
