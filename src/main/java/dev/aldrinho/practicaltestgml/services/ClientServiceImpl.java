package dev.aldrinho.practicaltestgml.services;

import dev.aldrinho.practicaltestgml.dto.ClientDto;
import dev.aldrinho.practicaltestgml.dto.CreateClientDto;
import dev.aldrinho.practicaltestgml.models.Client;
import dev.aldrinho.practicaltestgml.repository.IClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static dev.aldrinho.practicaltestgml.utils.DateUtil.parseDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements IClientService {

    private final IClientRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getClients() {
        log.info("Llamando al metodo getClients");
        List<Client> clients = repository.findAll();

        log.info("Retornando lista de clientes {}", clients.size());

        return clients.stream().map((client) -> modelMapper.map(client, ClientDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getClientsByBusinessId(String businessId) {
        log.info("Llamando al metodo getClientsByBusinessId");
        List<Client> clients = repository.findClientsByBusinessIdContainsIgnoreCase(businessId);
        log.info("Retornando lista de clientes {}", clients.size());
        return clients.stream().map((client) -> modelMapper
                        .map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getClientsByEmail(String email) {
        log.info("Llamando al metodo getClientsByEmail");
        List<Client> clients = repository.findClientsByEmail(email);
        log.info("Retornando lista de clientes {}", clients.size());
        return clients.stream().map((client) -> modelMapper
                        .map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getClientsBySharedKey(String sharedKey) {
        log.info("Llamando al metodo getClientsBySharedKey");
        List<Client> clients = repository.findClientsBySharedKeyContainsIgnoreCase(sharedKey);
        log.info("Retornando lista de clientes {}", clients.size());
        return clients.stream().map((client) -> modelMapper
                        .map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getClientsByPhone(String phone) {
        log.info("Llamando al metodo getClientsByPhone");
        List<Client> clients = repository.findClientsByPhone(phone);
        log.info("Retornando lista de clientes {}", clients.size());
        return clients.stream().map((client) -> modelMapper
                        .map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getClientsByDateAdded(String startDateStr, String endDateStr) {

        log.info("Llamando al metodo getClientsByDateAdded");

        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);

        List<Client> clients = new ArrayList<>();

        if (startDate != null && endDate != null) {
            clients = repository.findByDateAddedBetween(startDate, endDate);
        } else if (startDate != null) {
            clients = repository.findByDateAddedAfter(startDate);
        } else if (endDate != null) {
            clients = repository.findByDateAddedBefore(endDate);
        }

        if (!clients.isEmpty()) {
            log.info("Clientes encontrados {}", clients.size());
            return clients.stream().map(client -> modelMapper.map(client, ClientDto.class)).collect(Collectors.toList());
        }

        return List.of();
    }


    @Override
    @Transactional
    public ClientDto saveClient(CreateClientDto createClientDto) {
        log.info("Llamando al metodo saveClient");

        Client client = Client
                .builder()
                .businessId(createClientDto.getBusinessId())
                .email(createClientDto.getEmail())
                .sharedKey(createClientDto.getSharedKey())
                .phone(createClientDto.getPhone())
                .build();

        repository.save(client);

        log.info("Nuevo cliente guardado {}", client);

        return modelMapper.map(client, ClientDto.class);
    }
}
