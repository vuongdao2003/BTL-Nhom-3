package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name="Roles")
@Table(name ="roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "roleName")
    private String roleName;
    @Column(name = "createDate")
    private Date createDate;
    @OneToMany(mappedBy = "roles")
    private Set<Users> users;

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
