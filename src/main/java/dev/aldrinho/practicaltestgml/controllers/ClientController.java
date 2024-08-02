package dev.aldrinho.practicaltestgml.controllers;

import dev.aldrinho.practicaltestgml.dto.ClientDto;
import dev.aldrinho.practicaltestgml.dto.ClientSearchDto;
import dev.aldrinho.practicaltestgml.dto.CreateClientDto;
import dev.aldrinho.practicaltestgml.dto.ResponseDto;
import dev.aldrinho.practicaltestgml.exceptions.ResourceExistsException;
import dev.aldrinho.practicaltestgml.services.IClientService;
import dev.aldrinho.practicaltestgml.utils.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = {"http://localhost:4200"})
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
    public ResponseEntity<ResponseDto> createClient(@Valid @RequestBody CreateClientDto createClientDto) {
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
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> searchClients(@RequestBody ClientSearchDto clientSearchDto) {

        log.info("Entrando a POST /api/clients/search");

        List<ClientDto> clients = switch (clientSearchDto.getParam()) {
            case "businessId" -> service.getClientsByBusinessId(clientSearchDto.getValue());
            case "sharedKey" -> service.getClientsBySharedKey(clientSearchDto.getValue());
            case "email" -> service.getClientsByEmail(clientSearchDto.getValue());
            case "phone" -> service.getClientsByPhone(clientSearchDto.getValue());
            case "dataAdded" -> service.getClientsByDateAdded(clientSearchDto.getValue(), clientSearchDto.getValue2());
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

    @Operation(summary = "Export clients to CSV")
    @GetMapping("/export-csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        log.info("Entrando a GET /api/clients/export-csv");
        List<ClientDto> clients = service.getClients();
        log.info("Exportando clientes CSV");
        ExcelUtil.exportToExcel(response, clients);
        log.info("Exportacion completa");
    }

}
