package org.networking.repository;

import org.networking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gino on 8/28/2015.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
