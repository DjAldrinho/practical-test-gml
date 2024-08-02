package dev.aldrinho.practicaltestgml.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientDto {

    @NotBlank
    private String sharedKey;

    @NotBlank
    private String businessId;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;
}
