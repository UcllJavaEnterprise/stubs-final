package be.ucll.java.ent.persistence.entities;

import be.ucll.java.ent.domain.UserStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "stubs_user")
public class UserEnt extends StubsEntity {

    // Primary key in database
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "useq")
    @SequenceGenerator(name = "useq", sequenceName = "user_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(length = 256)
    private String name;

    @Column(length = 128)
    private String firstName;

    @Column(length = 64, unique = true)
    private String loginId;

    @Column(length = 64)
    private String password;

    @Column(length = 20)
    private String apikey;

    @Column(length = 1)
    private UserStatus status = UserStatus.Undefined;

    @Column
    private LocalDateTime dateOfCreation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "stubs_users_roles",
            joinColumns = @JoinColumn(name = "stubs_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEnt> roles;

    /* ***** Constructors ***** */
    public UserEnt() {
        // Default constructor
    }

    public UserEnt(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    public UserEnt(long id, String name, String firstName) {
        this(name, firstName);
        this.id = id;
    }

    // Getters and Setters hereunder

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String lastName) {
        this.name = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean isEnabled() {
        if (status.equals(UserStatus.Active)) return true;
        return false;
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
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Collection<RoleEnt> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEnt> roles) {
        this.roles = roles;
    }

}
