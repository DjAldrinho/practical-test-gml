package dev.aldrinho.practicaltestgml.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResponseDto {
    private Object data;
    private String message;
}
