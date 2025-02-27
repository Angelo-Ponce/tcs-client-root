package com.tcs.controller;

import com.tcs.dto.ClientDTO;
import com.tcs.model.ClientEntity;
import com.tcs.service.IClientService;
import com.tcs.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClientController.class)
class ClientControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private IClientService service;

    @MockitoBean
    private MapperUtil mapperUtil;

    @MockitoBean
    private WebProperties.Resources resources;

    private ClientDTO clientDTO;
    private ClientEntity clientEntity;

    @BeforeEach
    void setUp() {

        clientDTO = new ClientDTO();
        clientDTO.setPersonId(1L);
        clientDTO.setIdentificacion("654321");
        clientDTO.setName("Joel");
        clientDTO.setGender("Masculino");
        clientDTO.setAge(20);
        clientDTO.setAddress("Guayaquil");
        clientDTO.setPhone("0999");
        clientDTO.setClientId("joel");
        clientDTO.setPassword("123456");
        clientDTO.setStatus(true);

        clientEntity = ClientEntity.builder()
                .personId(1L)
                .identificacion("654321")
                .name("Joel")
                .gender("Masculino")
                .age(20)
                .address("Guayaquil")
                .phone("0999")
                .clientId("joel")
                .password("123456")
                .status(true)
                .build();

        // Configurar mocks
        when(mapperUtil.map(clientEntity, ClientDTO.class)).thenReturn(clientDTO);
        when(mapperUtil.map(clientDTO, ClientEntity.class)).thenReturn(clientEntity);

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void givenClients_whenFindAll_thenReturnClientList() {
        when(service.findAll()).thenReturn(Flux.just(clientEntity));

        webTestClient.get()
                .uri("/api/v1/clients")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ClientDTO.class)
                .hasSize(1);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void givenClientId_whenFindById_thenReturnClient() {
        when(service.findById(1L)).thenReturn(Mono.just(clientEntity));

        webTestClient.get()
                .uri("/api/v1/clients/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClientDTO.class)
                .isEqualTo(clientDTO);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void givenClientId_whenFindById_thenReturnNotFound() {
        when(service.findById(1L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/api/v1/clients/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}