package org.networking.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.networking.enums.ValidFlag;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by Gino on 8/28/2015.
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "USER")
public class User extends BaseEntity {
	
	
    @Column(name="USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="MIDDLE_NAME")
    private String middleName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="PASSWORD", nullable = false)
    private String password;

    /**
     * For changing of password...
     */
    @Transient
    private String oldPassword;
    
    @Transient
    private String newPassword;
    
    @Transient
    private String confirmPassword;

    @Column(nullable = false)
    private boolean isAccountNonExpiredAlias = Boolean.TRUE;

    @Column(nullable = false)
    private boolean isAccountNonLockedAlias = Boolean.TRUE;

    @Column(nullable = false)
    private boolean isCredentialsNonExpiredAlias = Boolean.TRUE;

    @Column(nullable = false)
    private boolean isEnabledAlias = Boolean.TRUE;

    @Column(nullable = false)
    private ValidFlag validFlag = ValidFlag.VALID;

    @Column(columnDefinition="TEXT")
    private String description;

    // Last login time
    private Date lastLoginTime;

    // Last login IP address
    @Column(name = "last_login_ip")
    private String lastLoginIP;

    @CreatedDate
    @Column(nullable = false)
    private Date createdDate = new Date();

    @CreatedBy
    @Column(nullable = false)
    private Long createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private Date lastModifiedDate = new Date();

    @LastModifiedBy
    @Column(nullable = false)
    private Long lastModifiedBy;

    @Version
    @Column(nullable = false)
    private int version;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "users_has_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();
    
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Authority> authorities;

    public User(Long id, String name, String usr, String pwd) {
        this.id = id;
        username = name;
        password = pwd;
    }

    public User(User user) {
        super();
        this.id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
    }
    
    public User() {
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public void grantRole(Role role) {
        if (authorities == null) {
            authorities = new HashSet<Authority>();
        }
        authorities.add(new Authority(this, role));
    }

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

    public String getCompleteName() {
        return firstName + " " + lastName;
    }

	public boolean isAccountNonExpiredAlias() {
		return isAccountNonExpiredAlias;
	}

	public void setAccountNonExpiredAlias(boolean isAccountNonExpiredAlias) {
		this.isAccountNonExpiredAlias = isAccountNonExpiredAlias;
	}

	public boolean isAccountNonLockedAlias() {
		return isAccountNonLockedAlias;
	}

	public void setAccountNonLockedAlias(boolean isAccountNonLockedAlias) {
		this.isAccountNonLockedAlias = isAccountNonLockedAlias;
	}

	public boolean isCredentialsNonExpiredAlias() {
		return isCredentialsNonExpiredAlias;
	}

	public void setCredentialsNonExpiredAlias(boolean isCredentialsNonExpiredAlias) {
		this.isCredentialsNonExpiredAlias = isCredentialsNonExpiredAlias;
	}

	public boolean isEnabledAlias() {
		return isEnabledAlias;
	}

	public void setEnabledAlias(boolean isEnabledAlias) {
		this.isEnabledAlias = isEnabledAlias;
	}

	public ValidFlag getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(ValidFlag validFlag) {
		this.validFlag = validFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
    
}
