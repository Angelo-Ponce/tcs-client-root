package com.tcs.service.impl;

import com.tcs.exception.ModelNotFoundException;
import com.tcs.model.ClientEntity;
import com.tcs.repository.IClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private IClientRepository repository;

    @Test
    void givenGetRepository_WhenCalled_ThenReturnCorrectRepositoryInstance() {
        assertEquals(repository, clientService.getRepository());
    }

    @Test
    void givenDeleteLogic_WhenClientExists_ThenMarkClientAsInactive() {
        Long id = 1L;
        String clientId = "angelo";
        ClientEntity client = new ClientEntity();
        client.setClientId(clientId);
        client.setStatus(Boolean.TRUE);
        client.setLastModifiedDate(null);
        client.setLastModifiedByUser(null);

        when(repository.findById(id)).thenReturn(Optional.of(client));

        clientService.deleteLogic(id);

        assertNotNull(client.getLastModifiedDate());
        assertEquals(Boolean.FALSE, client.getStatus());
        assertEquals("Angelo", client.getLastModifiedByUser());
        verify(repository, times(1)).save(client);
    }

    @Test
    void givenDeleteLogic_WhenClientDoesNotExist_ThenThrowModelNotFoundException() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ModelNotFoundException.class,
                () -> clientService.deleteLogic(id),
                "ID not found: " + id
        );

        verify(repository, never()).save(any());
    }
}