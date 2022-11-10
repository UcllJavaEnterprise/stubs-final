package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class User implements Serializable {

    private Long id;
    private String name;
    private String firstName;
    private String loginId;
    private String password;
    private String apikey;
    private UserStatus userStatus;
    private LocalDateTime dateOfCreation;

    private List<Role> roles;

    /* ***** Constructors ***** */
    public User() {
        userStatus = UserStatus.Undefined;
    }

    public User(String name) {
        this();
        this.name = name;
    }

    public User(String name, String firstName) {
        this(name);
        this.firstName = firstName;
    }

    public User(Long id, String name, String firstName) {
        this(name, firstName);
        this.id = id;
    }

    /* ***** Getters & Setters ***** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginid) {
        this.loginId = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public UserStatus getStatus() {
        return userStatus;
    }

    public void setStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
