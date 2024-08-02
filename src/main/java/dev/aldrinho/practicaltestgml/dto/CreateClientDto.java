package dev.aldrinho.practicaltestgml.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientDto {

    @NotBlank
    @NotNull
    private String sharedKey;

    @NotBlank
    @NotNull
    private String businessId;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String phone;
}
