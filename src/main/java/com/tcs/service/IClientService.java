package com.tcs.service;

import com.tcs.model.ClientEntity;

public interface IClientService extends ICRUD<ClientEntity, Long> {

    void deleteLogic(Long id);
}
