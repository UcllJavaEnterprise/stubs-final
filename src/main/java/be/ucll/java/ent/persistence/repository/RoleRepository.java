package be.ucll.java.ent.persistence.repository;

import be.ucll.java.ent.domain.Role;
import be.ucll.java.ent.persistence.entities.RoleEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEnt, Integer> {

    RoleEnt findByName(Role role);
    RoleEnt findByName(String name);
}
