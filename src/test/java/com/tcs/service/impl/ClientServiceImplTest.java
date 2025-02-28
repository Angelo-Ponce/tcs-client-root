package com.tcs.service.impl;

import com.tcs.model.ClientEntity;
import com.tcs.repository.IClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl service;

    @Mock
    private IClientRepository mockRepository;

    private final ClientEntity mockClient = new ClientEntity(1L, "123", "Angelo","Masculino", 20, "Guayaquil", "0999","angelo","12345",true,"",null,"",null);

    @Test
    void givenGetRepository_WhenCalled_ThenReturnCorrectRepositoryInstance() {
        assertEquals(mockRepository, service.getRepository());
    }

    @Test
    void givenSave_WhenTheEntityHasData_ThenSuccessfullySaveTheEntity() {
        when(mockRepository.save(mockClient)).thenReturn(Mono.just(mockClient));

        Mono<ClientEntity> result = service.save(mockClient, "Angelo");

        StepVerifier.create(result)
                .expectNextMatches(saveClient -> saveClient.getCreatedByUser().equals("Angelo"))
                .verifyComplete();
        verify(mockRepository, times(1)).save(mockClient);
    }

    @Test
    void givenUpdateEntity_WhenEntityExists_ThenReturnUpdatedEntity(){
        String username = "Angelo";
        ClientEntity update = ClientEntity.builder()
                .identificacion("654321")
                .name("Joel")
                .gender("Masculino")
                .age(20)
                .address("Guayaquil")
                .phone("0999")
                .clientId("joel")
                .password("123456")
                .status(true)
                .lastModifiedByUser(username)
                .build();

        when(mockRepository.findById(mockClient.getPersonId())).thenReturn(Mono.just(mockClient));
        when(mockRepository.save(mockClient)).thenReturn(Mono.just(mockClient));

        Mono<ClientEntity> result = service.update(mockClient.getPersonId(), update, username);
        StepVerifier.create(result)
                .expectNextMatches(savedClient ->
                        savedClient.getIdentificacion().equals("654321") &&
                                savedClient.getName().equals("Joel") &&
                                savedClient.getLastModifiedByUser().equals(username) &&
                                savedClient.getLastModifiedDate() != null)
                .verifyComplete();

        verify(mockRepository, times(1)).findById(mockClient.getPersonId());
        verify(mockRepository, times(1)).save(mockClient);
    }

    @Test
    void givenDeleteLogic_whenDeleteLogic_thenClientStatusIsUpdatedToFalse() {
        when(mockRepository.findById(mockClient.getPersonId())).thenReturn(Mono.just(mockClient));
        when(mockRepository.save(mockClient)).thenReturn(Mono.just(mockClient));

        Mono<Boolean> result = service.deleteLogic(mockClient.getPersonId(), "admin");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(mockRepository, times(1)).findById(mockClient.getPersonId());
        verify(mockRepository, times(1)).save(mockClient);
    }

    @Test
    void givenNonExistentIdClient_whenDeleteLogic_thenReturnFalse() {
        when(mockRepository.findById(mockClient.getPersonId())).thenReturn(Mono.empty());

        Mono<Boolean> result = service.deleteLogic(mockClient.getPersonId(), "admin");

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();

        verify(mockRepository, times(1)).findById(mockClient.getPersonId());
        verify(mockRepository, never()).save(any());
    }
}