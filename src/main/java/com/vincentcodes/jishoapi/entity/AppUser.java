package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.sterotype.DtoAsWell;
import org.hibernate.annotations.Type;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

/**
 * This is class matches the database schema. There is also a wrapper
 * class made to be compatible with {@link UserDetails}.
 * @see AppUserDetailsWrapper
 */
@DtoAsWell
@Entity
@Table(name = "appuser")
@JsonView({AppUser.ALL.class, AppUser.CENSORED.class})
public class AppUser implements Serializable {
    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "userid")
    private final UUID userId;

    private String name;

    private String pass;

    //private String desc;

    @Column(name = "can_userpass")
    private boolean canUserPassLogin = true;

    public AppUser(String name, String pass) {
        this.userId = UUID.randomUUID();
        this.name = name;
        this.pass = pass;
    }

    // no param constructor for JPA
    public AppUser() {
        this(null, null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Enabling User-Password login mechanism
     * @param allowUserPassLogin uses password to login?
     */
    public void setAllowUserPassLogin(boolean allowUserPassLogin){
        canUserPassLogin = allowUserPassLogin;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @JsonView({AppUser.ALL.class})
    public String getPass() {
        return pass;
    }

    public boolean getAllowUserPassLogin(){
        return canUserPassLogin;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", canUserPassLogin=" + canUserPassLogin +
                '}';
    }

    public static class ALL{}
    public static class CENSORED{}
}
