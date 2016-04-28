package org.networking.service.impl;

import org.networking.entity.Member;
import org.networking.entity.Product;
import org.networking.repository.ProductRepository;
import org.networking.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Gino on 9/20/2015.
 */
@Transactional
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    @Override
    protected void setRepository(JpaRepository<Product, Long> repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findByNameLike(String name) {
        return ((ProductRepository)repository).findByNameLikeIgnoreCase("%" + name + "%");
    }
}
