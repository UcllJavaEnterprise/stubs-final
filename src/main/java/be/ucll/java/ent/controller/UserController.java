package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.Role;
import be.ucll.java.ent.domain.User;
import be.ucll.java.ent.persistence.entities.RoleEnt;
import be.ucll.java.ent.persistence.entities.UserEnt;
import be.ucll.java.ent.persistence.repository.RoleRepository;
import be.ucll.java.ent.persistence.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@Transactional
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserDAO dao;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder pwEncoder;

    // Create methods

    public long createUser(User user) throws IllegalArgumentException {

        // Algemene input controle
        if (user == null) throw new IllegalArgumentException("Gebruiker aanmaken gefaald. Inputdata ontbreekt");

        // Controle op verplichte velden
        if (user.getName() == null || user.getName().length() == 0)
            throw new IllegalArgumentException("Gebruiker aanmaken gefaald. Familienaam ontbreekt");

        // Waarde te lang
        if (user.getName().trim().length() >= 256)
            throw new IllegalArgumentException("Gebruiker aanmaken gefaald. Naam langer dan 256 karakters");

        // Set some defaults if needed
        if (user.getRoles() == null) {
            user.setRoles(Collections.singletonList(Role.USER));
        }
        if (user.getLoginId() == null || user.getLoginId().trim().length() <= 0) {
            user.setLoginId(generateLoginId());
        }
        if (user.getDateOfCreation() == null) {
            user.setDateOfCreation(LocalDateTime.now());
        }

        UserEnt u = userToUserEnt(user);
        dao.create(u);

        user.setId(u.getId());
        return u.getId();
    }

    // Read / get-one methods

    public User getUserById(Long userid) throws IllegalArgumentException {
        if (userid <= 0L) throw new IllegalArgumentException("Gebruiker ID ontbreekt");

        Optional<UserEnt> value = dao.get(userid);
        if (value.isPresent()) {
            return userEntToUser(value.get());
        } else {
            throw new IllegalArgumentException("Geen gebruiker gevonden met ID: " + userid);
        }
    }

    public User getUserByLoginId(String loginId) throws IllegalArgumentException {
        if (loginId == null) throw new IllegalArgumentException("Ongeldige loginId meegegeven");
        if (loginId.trim().length() == 0) throw new IllegalArgumentException("Geen loginId meegegeven");

        Optional<UserEnt> value = dao.getOneByLoginId(loginId);
        if (value.isPresent()) {
            User u = userEntToUser(value.get());
            u.setPassword(value.get().getPassword());
            return u;
        } else {
            throw new IllegalArgumentException("Geen gebruiker gevonden met loginId: " + loginId);
        }
    }

    // Update / Modify / Change methods

    // Delete methods

    // Search methods

    // Other

    public String generateLoginId() {
        long l = countUsers() + 1L;
        return "u" + String.format("%04d", l);
    }

    public long countUsers() {
        return dao.countAll();
    }

    // private methods

    private User userEntToUser(UserEnt ent) {
        User user = new User();
        user.setId(ent.getId());
        user.setFirstName(ent.getFirstName());
        user.setName(ent.getName());

        user.setLoginId(ent.getLoginId());
        // Useless to show the encrypted password in the UI
        // user.getPassword()
        user.setStatus(ent.getStatus());

        user.setDateOfCreation(ent.getDateOfCreation());

        user.setRoles(ent.getRoles().stream().map(RoleEnt::getName).collect(Collectors.toList()));

        return user;
    }

    private UserEnt userToUserEnt(User user) {
        UserEnt ent = new UserEnt();
        if (user.getId() != null) {
            ent.setId(user.getId());
        }
        if (user.getName() != null) {
            ent.setName(user.getName().trim());
        }
        if (user.getFirstName() != null) {
            ent.setFirstName(user.getFirstName().trim());
        }
        ent.setLoginId(user.getLoginId());
        ent.setPassword(pwEncoder.encode(user.getPassword()));
        ent.setStatus(user.getStatus());
        ent.setDateOfCreation(user.getDateOfCreation());

        ent.setRoles(user.getRoles().stream().map(role -> roleRepo.findByName(role)).collect(Collectors.toList()));

        return ent;
    }


    public RoleEnt createRole(Role r) {
        RoleEnt role = roleRepo.findByName(r);
        if (role == null) {
            role = new RoleEnt(r);
            roleRepo.save(role);
            log.info("Role " + role.getName() + " created");
        } else {
            // log.info("Role " + role.getName() + " retrieved");
        }
        return role;
    }
}