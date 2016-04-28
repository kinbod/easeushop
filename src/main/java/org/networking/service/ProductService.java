package org.networking.service;

import org.networking.entity.Member;
import org.networking.entity.Product;

import java.util.List;

/**
 * Created by Gino on 9/20/2015.
 */
public interface ProductService extends BaseService<Product> {

    List<Product> findByNameLike(String name);

}
