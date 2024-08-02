package dev.aldrinho.practicaltestgml.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientSearchDto {

    private String param;
    private String value;
    private String value2;
}