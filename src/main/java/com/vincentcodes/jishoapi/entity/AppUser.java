package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.utils.DtoAsWell;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@DtoAsWell
@Entity
@Table(name = "appuser")
public class AppUser {
    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "userid")
    private final UUID userId;

    private String name;

    private String pass;

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

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
}
