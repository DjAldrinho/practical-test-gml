package dev.aldrinho.practicaltestgml.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClientDto {
    private Long id;
    private String businessId;
    private String sharedKey;
    private String email;
    private String phone;
}
