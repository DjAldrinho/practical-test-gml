package dev.aldrinho.practicaltestgml.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aldrinho.practicaltestgml.dto.ClientDto;
import dev.aldrinho.practicaltestgml.dto.ClientSearchDto;
import dev.aldrinho.practicaltestgml.dto.CreateClientDto;
import dev.aldrinho.practicaltestgml.services.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Mock
    private IClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void testFindClients() throws Exception {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setBusinessId("businessId1");
        clientDto.setSharedKey("sharedKey1");
        clientDto.setEmail("email1@example.com");
        clientDto.setPhone("phone1");

        when(clientService.getClients()).thenReturn(List.of(clientDto));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"data\":[{\"id\":1,\"businessId\":\"businessId1\",\"sharedKey\":\"sharedKey1\",\"email\":\"email1@example.com\",\"phone\":\"phone1\"}],\"message\":\"Clientes listados\"}"));
    }

    @Test
    void testCreateClient() throws Exception {
        CreateClientDto createClientDto = CreateClientDto.builder()
                .businessId("businessId1")
                .email("email1@example.com")
                .sharedKey("sharedKey1")
                .phone("phone1")
                .build();

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setBusinessId("businessId1");
        clientDto.setSharedKey("sharedKey1");
        clientDto.setEmail("email1@example.com");
        clientDto.setPhone("phone1");

        when(clientService.getClientsBySharedKey(eq("sharedKey1"))).thenReturn(Collections.emptyList());
        when(clientService.saveClient(any(CreateClientDto.class))).thenReturn(clientDto);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createClientDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"data\":{\"id\":1,\"businessId\":\"businessId1\",\"sharedKey\":\"sharedKey1\",\"email\":\"email1@example.com\",\"phone\":\"phone1\"},\"message\":\"Nuevo cliente creado\"}"));
    }

    @Test
    void testSearchClients() throws Exception {
        ClientSearchDto clientSearchDto = new ClientSearchDto();
        clientSearchDto.setParam("sharedKey");
        clientSearchDto.setValue("sharedKey1");

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setBusinessId("businessId1");
        clientDto.setSharedKey("sharedKey1");
        clientDto.setEmail("email1@example.com");
        clientDto.setPhone("phone1");

        when(clientService.getClientsBySharedKey(eq("sharedKey1"))).thenReturn(List.of(clientDto));

        mockMvc.perform(post("/clients/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientSearchDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"data\":[{\"id\":1,\"businessId\":\"businessId1\",\"sharedKey\":\"sharedKey1\",\"email\":\"email1@example.com\",\"phone\":\"phone1\"}],\"message\":\"Clientes listados\"}"));
    }
}
