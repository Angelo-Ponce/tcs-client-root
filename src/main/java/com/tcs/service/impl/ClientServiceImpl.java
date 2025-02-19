package com.tcs.service.impl;

import com.tcs.exception.ModelNotFoundException;
import com.tcs.model.ClientEntity;
import com.tcs.repository.IClientRepository;
import com.tcs.repository.IGenericRepository;
import com.tcs.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends CRUDImpl<ClientEntity, Long> implements IClientService {

    private final IClientRepository repository;

    @Override
    protected IGenericRepository<ClientEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public void deleteLogic(Long id) {
        ClientEntity client = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID not found: " + id));
        client.setStatus(Boolean.FALSE);
        client.setLastModifiedDate(new Date());
        // TODO: IMPLEMENTAR USUARIO
        client.setLastModifiedByUser("Angelo");
        repository.save(client);
    }
}
