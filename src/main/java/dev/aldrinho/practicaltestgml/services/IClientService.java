package dev.aldrinho.practicaltestgml.services;

import dev.aldrinho.practicaltestgml.dto.ClientDto;
import dev.aldrinho.practicaltestgml.dto.CreateClientDto;
import dev.aldrinho.practicaltestgml.dto.UpdateClientDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IClientService {
    List<ClientDto> getClients();

    List<ClientDto> getClientsByBusinessId(String businessId);

    List<ClientDto> getClientsByEmail(String email);

    List<ClientDto> getClientsBySharedKey(String sharedKey);

    List<ClientDto> getClientsByPhone(String phone);

    List<ClientDto> getClientsByDateAddBetweenStartAndEnd(Date startDate, Date endDate);

    ClientDto saveClient(CreateClientDto createClientDto);
}
