package com.tcs.controller;

import com.tcs.dto.ClientDTO;
import com.tcs.model.ClientEntity;
import com.tcs.service.IClientService;
import com.tcs.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService service;

    private final MapperUtil mapperUtil;

    @GetMapping
    public Mono<ResponseEntity<Flux<ClientDTO>>> findAll(){
        Flux<ClientDTO> clientDTOFlux = service.findAll()
                .map(e -> mapperUtil.map(e, ClientDTO.class));
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(clientDTOFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientDTO>> findById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(e -> mapperUtil.map(e, ClientDTO.class))
                .map(client -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(client)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    public Mono<ResponseEntity<ClientDTO>> findByClientId(@PathVariable("clientId") String clientId) {
        return service.findByClientId(clientId)
                .map(e -> mapperUtil.map(e, ClientDTO.class))
                .map(client -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(client)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ClientDTO>> save(@Valid @RequestBody ClientDTO dto, final ServerHttpRequest request) {
        return service.save(mapperUtil.map(dto, ClientEntity.class), "Angelo" )
                .map(e -> mapperUtil.map(e, ClientDTO.class))
                .map(e -> ResponseEntity.created(
                                        URI.create(request.getURI().toString().concat("/").concat(e.getPersonId().toString()))
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClientDTO>> update(@Valid @PathVariable("id") Long id, @RequestBody ClientDTO dto) {
        return service.update(id, mapperUtil.map(dto, ClientEntity.class), "Angelo")
                .map(e -> mapperUtil.map(e, ClientDTO.class))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id) {
        return service.deleteById(id)
                .flatMap( result -> {
                    if(Boolean.TRUE.equals(result)) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    @DeleteMapping("/deletelogic/{id}")
    public Mono<ResponseEntity<Void>> deleteLogic(@PathVariable("id") Long id) {
        return service.deleteLogic(id, "Angelo")
                .flatMap( result -> {
                    if(Boolean.TRUE.equals(result)) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
}
