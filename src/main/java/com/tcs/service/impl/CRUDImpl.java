package com.tcs.service.impl;

import com.tcs.exception.ModelNotFoundException;
import com.tcs.repository.IGenericRepository;
import com.tcs.service.ICRUD;

import java.util.List;

public abstract class CRUDImpl <T, I> implements ICRUD<T, I> {

    protected abstract IGenericRepository<T, I> getRepository();

    @Override
    public T save(T t) {
        return getRepository().save(t);
    }

    @Override
    public T update(I id, T t) {
        this.findById(id);
        return getRepository().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T findById(I id) {
        return getRepository().findById(id).orElseThrow(() -> new ModelNotFoundException("ID not found: " + id));
    }

    @Override
    public void delete(I id) {
        this.findById(id);
        getRepository().deleteById(id);
    }
}
