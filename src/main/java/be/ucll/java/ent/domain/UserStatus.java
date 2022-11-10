package be.ucll.java.ent.domain;

public enum UserStatus {

    Active('A'),
    Expired('E'),
    Disabled('D'),
    Undefined('U');

    private Character status;

    // keep constructor private
    private UserStatus(Character status) {
        this.status = status;
    }

    public Character getStatus() {
        return status;
    }

}
