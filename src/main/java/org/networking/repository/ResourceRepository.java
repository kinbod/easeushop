package org.networking.repository;

import org.networking.entity.Resource;
import org.networking.enums.ValidFlag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Resource's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface ResourceRepository extends RepositoryWithoutDelete<Resource, Long> {

    Resource findByName(String name);

    @Modifying
    @Query("update Resource r set r.validFlag=?1 where r.id=?2")
    void updateValidFlagFor(ValidFlag validFlag, Long id);
}
