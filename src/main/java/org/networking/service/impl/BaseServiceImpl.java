package org.networking.service.impl;

import org.networking.entity.BaseEntity;
import org.networking.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Gino on 8/28/2015.
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    protected JpaRepository<T, Long> repository;

    @Override
    public T load(Long id) {
        return repository.findOne(id);
    }

    @Override
    public void save(T t) {
        repository.save(t);
    }

    @Override
    public List<T> list() {
        return repository.findAll();
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    protected abstract void setRepository(JpaRepository<T, Long> repository);

    public JpaRepository<T, Long> getRepository() {
        return repository;
    }
}
