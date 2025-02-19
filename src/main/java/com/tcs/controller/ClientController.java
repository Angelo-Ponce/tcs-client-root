package com.tcs.controller;

import com.tcs.dto.BaseResponse;
import com.tcs.dto.ClientDTO;
import com.tcs.model.ClientEntity;
import com.tcs.service.IClientService;
import com.tcs.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService service;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> list = mapperUtil.mapList(service.findAll(), ClientDTO.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable("id") Long id){
        ClientEntity obj = service.findById(id);
        return ResponseEntity.ok(BaseResponse.builder().data(mapperUtil.map(obj, ClientDTO.class)).build());
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ClientDTO dto){
        ClientEntity clientEntity = mapperUtil.map(dto, ClientEntity.class);
        clientEntity.setCreatedDate(new Date());
        clientEntity.setCreatedByUser("Angelo");
        ClientEntity obj = service.save(clientEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update( @PathVariable("id") Long id, @RequestBody ClientDTO dto){
        dto.setPersonId(id);
        ClientEntity clientEntity = mapperUtil.map(dto, ClientEntity.class);
        clientEntity.setLastModifiedDate(new Date());
        clientEntity.setLastModifiedByUser("Angelo");
        ClientEntity obj = service.update(id, clientEntity);

        return ResponseEntity.ok(BaseResponse.builder().data(mapperUtil.map(obj, ClientDTO.class)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete( @PathVariable("id") Long id){
        // Eliminar registro
        //service.delete(id);
        // Eliminado logico
        service.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }
}
