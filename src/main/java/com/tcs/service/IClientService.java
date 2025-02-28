package com.tcs.service;

import com.tcs.model.ClientEntity;
import reactor.core.publisher.Mono;

public interface IClientService extends ICRUDService<ClientEntity, Long> {

    Mono<ClientEntity> save(ClientEntity client, String user);
    Mono<ClientEntity> update(Long id, ClientEntity client, String user);
    Mono<ClientEntity> findByClientId(String clientId);
    Mono<Boolean> deleteLogic(Long id, String user);
}
