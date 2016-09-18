package org.networking.repository;

import org.networking.entity.Group;
import org.networking.enums.ValidFlag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Group's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface GroupRepository extends RepositoryWithoutDelete<Group, Long> {

    Group findByName(String name);

    @Modifying
    @Query("update Group g set g.validFlag=?1 where g.id=?2")
    void updateValidFlagFor(ValidFlag validFlag, Long id);
}
