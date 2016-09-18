package org.networking.repository;

import org.networking.entity.User;
import org.networking.enums.ValidFlag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by Gino on 8/28/2015.
 */
public interface UserRepository extends  JpaRepository<User, Long> {

	User findByusername(String username);

    Page<User> findAll(Pageable pageable);

    @Modifying
    @Query("update User u set u.password=?1 where u.id=?2")
    void updatePwdFor(String pwd, Long id);

    @Modifying
    @Query("update User u set u.validFlag=?1 where u.id=?2")
    void updateValidFlagFor(ValidFlag validFlag, Long id);
}
