package org.networking.repository;

import org.networking.entity.Member;
import org.networking.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Gino on 9/20/2015.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameLikeIgnoreCase(String name);

}
