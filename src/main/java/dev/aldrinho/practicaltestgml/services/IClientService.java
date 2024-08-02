package dev.aldrinho.practicaltestgml.services;

import dev.aldrinho.practicaltestgml.dto.ClientDto;
import dev.aldrinho.practicaltestgml.dto.CreateClientDto;

import java.util.Date;
import java.util.List;

public interface IClientService {
    List<ClientDto> getClients();

    List<ClientDto> getClientsByBusinessId(String businessId);

    List<ClientDto> getClientsByEmail(String email);

    List<ClientDto> getClientsBySharedKey(String sharedKey);

    List<ClientDto> getClientsByPhone(String phone);

    List<ClientDto> getClientsByDateAdded(String startDate, String endDate);

    ClientDto saveClient(CreateClientDto createClientDto);
}
