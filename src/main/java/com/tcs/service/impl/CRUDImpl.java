package com.tcs.service.impl;

import com.tcs.repository.IGenericRepository;
import com.tcs.service.ICRUDService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl <T, I> implements ICRUDService<T, I> {

    protected abstract IGenericRepository<T, I> getRepository();

    @Override
    public Flux<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Mono<T> findById(I id) {
        return getRepository().findById(id);
    }

    @Override
    public Mono<Boolean> deleteById(I id) {
        return getRepository().findById(id)
                .hasElement()
                .flatMap(result -> {
                    if(Boolean.TRUE.equals(result)) {
                        return getRepository().deleteById(id)
                                .thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
