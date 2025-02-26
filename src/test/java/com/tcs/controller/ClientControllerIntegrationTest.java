package com.tcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.dto.ClientDTO;
import com.tcs.model.ClientEntity;
import com.tcs.service.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IClientService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    void testFindAllClients() throws Exception {
//        ClientEntity client1 = new ClientEntity("CL001", "password1", true);
//        client1.setPersonId(1L);
//        ClientEntity client2 = new ClientEntity("CL002", "password2", true);
//        client2.setPersonId(2L);
//
//        when(service.findAll()).thenReturn(List.of(client1, client2));
//
//        mockMvc.perform(get("/api/v1/clients")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].clientId", is("CL001")))
//                .andExpect(jsonPath("$[1].clientId", is("CL002")));
//
//        verify(service, times(1)).findAll();
//    }
//
//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    void testFindClientById() throws Exception {
//        ClientEntity client = new ClientEntity("CL001", "password1", true);
//        client.setPersonId(1L);
//
//        when(service.findById(1L)).thenReturn(client);
//
//        mockMvc.perform(get("/api/v1/clients/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.clientId", is("CL001")))
//                .andExpect(jsonPath("$.data.personId", is(1)));
//
//        verify(service, times(1)).findById(1L);
//    }
}