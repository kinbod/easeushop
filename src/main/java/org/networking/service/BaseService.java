package org.networking.service;

import org.networking.entity.BaseEntity;

import java.util.List;

/**
 * Created by Gino on 8/28/2015.
 */
public interface BaseService<T extends BaseEntity> {

    T load(Long id);

    void save(T t);

    List<T> list();

    void delete(T t);

    void delete(Long id);

}
