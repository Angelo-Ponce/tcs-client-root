package com.tcs.repository;

import com.tcs.model.ClientEntity;
import reactor.core.publisher.Mono;

public interface IClientRepository extends IGenericRepository<ClientEntity, Long>{

    Mono<ClientEntity> findByClientId(String clientId);
}
