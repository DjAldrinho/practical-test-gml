package dev.aldrinho.practicaltestgml.controllers;

import dev.aldrinho.practicaltestgml.dto.ClientDto;
import dev.aldrinho.practicaltestgml.dto.CreateClientDto;
import dev.aldrinho.practicaltestgml.dto.ResponseDto;
import dev.aldrinho.practicaltestgml.exceptions.ResourceExistsException;
import dev.aldrinho.practicaltestgml.services.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Client", description = "API for client operations")
public class ClientController {

    private final IClientService service;

    @Operation(summary = "Get All Clients")
    @GetMapping
    public ResponseEntity<ResponseDto> findClients() {

        log.info("Entrando a GET /api/clients");

        List<ClientDto> clients = service.getClients();

        return ResponseEntity.ok(
                ResponseDto.
                        builder()
                        .data(clients)
                        .message("Clientes listados")
                        .build()
        );
    }

    @Operation(summary = "Create a new client")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> createHero(@Valid @RequestBody CreateClientDto createClientDto) {
        log.info("Entrando a POST /api/clients");
        log.info("Nuevo cliente: {}", createClientDto);

        if (!service.getClientsBySharedKey(createClientDto.getSharedKey()).isEmpty()) {
            throw new ResourceExistsException(createClientDto.getSharedKey(), "Client");
        }

        ClientDto clientDto = this.service.saveClient(createClientDto);

        return ResponseEntity.ok(
                ResponseDto.
                        builder()
                        .data(clientDto)
                        .message("Nuevo cliente creado")
                        .build()
        );

    }

    @Operation(summary = "Search clients by various parameters")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> searchClients(@RequestParam String param, @RequestParam String value) {

        log.info("Entrando a GET /api/clients/search");

        List<ClientDto> clients = switch (param) {
            case "businessId" -> service.getClientsByBusinessId(value);
            case "sharedKey" -> service.getClientsBySharedKey(value);
            case "email" -> service.getClientsByEmail(value);
            case "phone" -> service.getClientsByPhone(value);
            default -> List.of();
        };

        return ResponseEntity.ok(
                ResponseDto.
                        builder()
                        .data(clients)
                        .message("Clientes listados")
                        .build()
        );
    }


}
