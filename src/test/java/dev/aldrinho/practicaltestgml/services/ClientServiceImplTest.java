package dev.aldrinho.practicaltestgml.services;

import dev.aldrinho.practicaltestgml.dto.ClientDto;
import dev.aldrinho.practicaltestgml.dto.CreateClientDto;
import dev.aldrinho.practicaltestgml.models.Client;
import dev.aldrinho.practicaltestgml.repository.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;

    private Client client1;
    private Client client2;
    private ClientDto clientDto1;
    private ClientDto clientDto2;
    private SimpleDateFormat dateFormat;
    private CreateClientDto createClientDto;

    @BeforeEach
    void setUp() {
        client1 = new Client(1L, "businessId1", "sharedKey1", "email1@example.com", "phone1", new Date());
        client2 = new Client(2L, "businessId2", "sharedKey2", "email2@example.com", "phone2", new Date());

        clientDto1 = new ClientDto();
        clientDto1.setId(1L);
        clientDto1.setBusinessId("businessId1");
        clientDto1.setSharedKey("sharedKey1");
        clientDto1.setEmail("email1@example.com");
        clientDto1.setPhone("phone1");
        clientDto1.setDataAdded(new Date());

        clientDto2 = new ClientDto();
        clientDto2.setId(2L);
        clientDto2.setBusinessId("businessId2");
        clientDto2.setSharedKey("sharedKey2");
        clientDto2.setEmail("email2@example.com");
        clientDto2.setPhone("phone2");
        clientDto2.setDataAdded(new Date());

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        createClientDto = CreateClientDto.builder()
                .businessId("businessId1")
                .email("email1@example.com")
                .sharedKey("sharedKey1")
                .phone("phone1")
                .build();

    }

    @Test
    void testGetClients() {

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        when(modelMapper.map(client1, ClientDto.class)).thenReturn(clientDto1);

        when(modelMapper.map(client2, ClientDto.class)).thenReturn(clientDto2);

        List<ClientDto> clients = clientService.getClients();

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals(clientDto1, clients.get(0));
        assertEquals(clientDto2, clients.get(1));
    }

    @Test
    void testGetClientsEmpty() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());
        List<ClientDto> clients = clientService.getClients();
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }


    @Test
    void testGetClientsByBusinessId() {
        String businessId = client1.getBusinessId();
        when(clientRepository.findClientsByBusinessIdContainsIgnoreCase(businessId)).thenReturn(Collections.singletonList(client1));
        when(modelMapper.map(client1, ClientDto.class)).thenReturn(clientDto1);


        List<ClientDto> clients = clientService.getClientsByBusinessId(businessId);

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(clientDto1, clients.get(0));
    }

    @Test
    void testGetClientsByBusinessIdEmpty() {
        String businessId = "businessId3";
        when(clientRepository.findClientsByBusinessIdContainsIgnoreCase(businessId)).thenReturn(Collections.emptyList());
        List<ClientDto> clients = clientService.getClientsByBusinessId(businessId);
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

    @Test
    void testGetClientsByEmail() {
        String email = client2.getEmail();

        when(clientRepository.findClientsByEmail(email)).thenReturn(Collections.singletonList(client2));

        when(modelMapper.map(client2, ClientDto.class)).thenReturn(clientDto2);

        List<ClientDto> clients = clientService.getClientsByEmail(email);

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(clientDto2, clients.get(0));
    }


    @Test
    void testGetClientsByEmailEmpty() {
        String email = "email3";
        when(clientRepository.findClientsByEmail(email)).thenReturn(Collections.emptyList());
        List<ClientDto> clients = clientService.getClientsByEmail(email);
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

    @Test
    void testGetClientsBySharedKey() {
        String sharedKey = clientDto1.getSharedKey();
        when(clientRepository.findClientsBySharedKeyContainsIgnoreCase(sharedKey)).thenReturn(Collections.singletonList(client1));
        when(modelMapper.map(client1, ClientDto.class)).thenReturn(clientDto1);

        List<ClientDto> clients = clientService.getClientsBySharedKey(sharedKey);

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(clientDto1, clients.get(0));
    }

    @Test
    void testGetClientsBySharedKeyEmpty() {
        String sharedKey = "sharedKey3";
        when(clientRepository.findClientsBySharedKeyContainsIgnoreCase(sharedKey)).thenReturn(Collections.emptyList());

        List<ClientDto> clients = clientService.getClientsBySharedKey(sharedKey);

        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

    @Test
    void testGetClientsByPhone() {
        String phone = clientDto1.getPhone();
        when(clientRepository.findClientsByPhone(phone)).thenReturn(Collections.singletonList(client1));
        when(modelMapper.map(client1, ClientDto.class)).thenReturn(clientDto1);

        List<ClientDto> clients = clientService.getClientsByPhone(phone);

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(clientDto1, clients.get(0));

    }

    @Test
    void testGetClientsByPhoneEmpty() {
        String phone = "phone3";
        when(clientRepository.findClientsByPhone(phone)).thenReturn(Collections.emptyList());
        List<ClientDto> clients = clientService.getClientsByPhone(phone);
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

    @Test
    void testGetClientsByDateAdded() {
        String startDateStr = "2023-01-01";
        String endDateStr = "2023-12-31";
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);

        when(clientRepository.findByDateAddedBetween(startDate, endDate)).thenReturn(Arrays.asList(client1, client2));
        when(modelMapper.map(client1, ClientDto.class)).thenReturn(clientDto1);
        when(modelMapper.map(client2, ClientDto.class)).thenReturn(clientDto2);

        List<ClientDto> clients = clientService.getClientsByDateAdded(startDateStr, endDateStr);

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals(clientDto1, clients.get(0));
        assertEquals(clientDto2, clients.get(1));
    }

    @Test
    void testGetClientsByDateAdded_empty() {
        String startDateStr = "2023-01-01";
        String endDateStr = "2023-12-31";
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);

        when(clientRepository.findByDateAddedBetween(startDate, endDate)).thenReturn(Collections.emptyList());

        List<ClientDto> clients = clientService.getClientsByDateAdded(startDateStr, endDateStr);

        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

    @Test
    void testSaveClient() {

        when(clientRepository.save(any(Client.class))).thenReturn(client1);

        when(modelMapper.map(any(Client.class), any(Class.class))).thenReturn(clientDto1);

        ClientDto result = clientService.saveClient(createClientDto);

        assertNotNull(result);
        assertEquals(clientDto1.getBusinessId(), result.getBusinessId());
        assertEquals(clientDto1.getSharedKey(), result.getSharedKey());
        assertEquals(clientDto1.getEmail(), result.getEmail());
        assertEquals(clientDto1.getPhone(), result.getPhone());

        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();

        assertEquals(createClientDto.getBusinessId(), capturedClient.getBusinessId());
        assertEquals(createClientDto.getSharedKey(), capturedClient.getSharedKey());
        assertEquals(createClientDto.getEmail(), capturedClient.getEmail());
        assertEquals(createClientDto.getPhone(), capturedClient.getPhone());
    }

    private Date parseDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
