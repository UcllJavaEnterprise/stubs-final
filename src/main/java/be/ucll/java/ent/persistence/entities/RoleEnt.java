package be.ucll.java.ent.persistence.entities;

import be.ucll.java.ent.domain.Role;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role")
public class RoleEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 10)
    private Role name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEnt> users;

    /* ***** Constructors ***** */
    public RoleEnt() {
        // Default constructor
    }

    public RoleEnt(Role name) {
        this.name = name;
    }

    // Getters and Setters hereunder

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getName() {
        return name;
    }

    public void setName(Role name) {
        this.name = name;
    }

    public Collection<UserEnt> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEnt> users) {
        this.users = users;
    }
}