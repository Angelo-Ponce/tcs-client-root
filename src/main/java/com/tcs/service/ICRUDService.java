package com.tcs.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICRUDService<T, I>{

    Flux<T> findAll();
    Mono<T> findById(I id);
    Mono<Boolean> deleteById(I id);
}
