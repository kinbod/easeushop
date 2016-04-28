package org.networking.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gino on 8/28/2015.
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATEDATE")
    private Date createDate;

    @Column(name = "UPDATEDATE")
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public final boolean isNew() {
        return this.getId() == null || this.getId() <= 0;
    }
}
