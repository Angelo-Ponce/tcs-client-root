package com.tcs.service.impl;

import com.tcs.model.ClientEntity;
import com.tcs.repository.IClientRepository;
import com.tcs.repository.IGenericRepository;
import com.tcs.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends CRUDImpl<ClientEntity, Long> implements IClientService {

    private final IClientRepository repository;

    @Override
    protected IGenericRepository<ClientEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public Mono<ClientEntity> save(ClientEntity client, String user) {
        client.setCreatedByUser(user);
        return repository.save(client);
    }

    @Override
    public Mono<ClientEntity> update(Long id, ClientEntity client, String user) {
        return repository.findById(id)
                .flatMap(existingClient -> {
                    existingClient.setIdentificacion(client.getIdentificacion());
                    existingClient.setName(client.getName());
                    existingClient.setGender(client.getGender());
                    existingClient.setAge(client.getAge());
                    existingClient.setAddress(client.getAddress());
                    existingClient.setPhone(client.getPhone());
                    existingClient.setClientId(client.getClientId());
                    existingClient.setPassword(client.getPassword());
                    existingClient.setStatus(client.getStatus());
                    existingClient.setLastModifiedByUser(user);
                    existingClient.setLastModifiedDate(LocalDateTime.now());
                    return repository.save(existingClient);
                });
    }

    @Override
    public Mono<ClientEntity> findByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }

    @Override
    public Mono<Boolean> deleteLogic(Long id, String user) {
        return repository.findById(id)
                .flatMap(existingClient -> {
                    existingClient.setStatus(false);
                    existingClient.setLastModifiedByUser(user);
                    existingClient.setLastModifiedDate(LocalDateTime.now());
                    return repository.save(existingClient).thenReturn(true);
                }).defaultIfEmpty(false);

    }
}
