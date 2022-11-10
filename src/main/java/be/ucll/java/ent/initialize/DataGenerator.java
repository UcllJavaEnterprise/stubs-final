package be.ucll.java.ent.initialize;

import be.ucll.java.ent.controller.LeermoduleController;
import be.ucll.java.ent.controller.StudentController;
import be.ucll.java.ent.controller.UserController;
import be.ucll.java.ent.domain.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

@SpringComponent
public class DataGenerator {
    private static final Logger log = Logger.getLogger(DataGenerator.class.getName());

    @Autowired
    UserController uCtrl;

    @Autowired
    LeermoduleController lmCtrl;

    @Autowired
    StudentController studCtrl;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {

            if (uCtrl.countUsers() != 0L) {
                log.info("Using existing database. No demo data generated");
                return;
            }

            uCtrl.createRole(Role.USER);
            uCtrl.createRole(Role.ADMIN);

            // u0001
            User u = new User("Admin");
            u.setLoginId(uCtrl.generateLoginId());
            u.setPassword("StubsUCLLJavaPwd#123");
            u.setStatus(UserStatus.Active);
            u.setRoles(Arrays.asList(Role.USER, Role.ADMIN));
            long id = uCtrl.createUser(u);
            u = uCtrl.getUserById(id);
            log.info("Userid generated for user '" + u.getName() + "' with login ID: " + u.getLoginId());

            // u0002
            u = new User("Bogaert", "Kris");
            u.setLoginId(uCtrl.generateLoginId());
            u.setPassword("IK(%b3eCqEWy'37");
            u.setRoles(Collections.singletonList(Role.USER));
            id = uCtrl.createUser(u);
            u = uCtrl.getUserById(id);
            log.info("Userid generated for user '" + u.getName() + " " + u.getFirstName() + "' with login ID: " + u.getLoginId());

            // Test data. 2 leermodules
            LeermoduleDTO lm = new LeermoduleDTO(0, "MGP16A", "Java Mobile", "2021-22");
            lmCtrl.createLeermodule(lm);
            lm = new LeermoduleDTO(0, "MGP17A", "Java Enterprise", "2021-22");
            lmCtrl.createLeermodule(lm);

            // Test data. 1 student
            StudentDTO student = new StudentDTO();
            student.setNaam("D'hooghe");
            student.setVoornaam("Daniëlle");
            student.setGeboortedatum(new GregorianCalendar(1980, Calendar.AUGUST, 31).getTime());
            studCtrl.createStudent(student);
        };
    }

}