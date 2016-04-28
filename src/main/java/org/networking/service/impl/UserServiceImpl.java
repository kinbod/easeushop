package org.networking.service.impl;

import org.networking.entity.User;
import org.networking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gino on 8/28/2015.
 */
@Transactional
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    @Override
    protected void setRepository(JpaRepository<User, Long> repository) {
        this.repository = repository;
    }
}
