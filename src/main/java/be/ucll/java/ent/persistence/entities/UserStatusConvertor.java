package be.ucll.java.ent.persistence.entities;

import be.ucll.java.ent.domain.UserStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter (autoApply = true)
public class UserStatusConvertor implements AttributeConverter<UserStatus, Character> {

    @Override
    public Character convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        }
        return userStatus.getStatus();
    }

    @Override
    public UserStatus convertToEntityAttribute(Character code) {
        if (code == null) {
            return null;
        }

        return Stream.of(UserStatus.values())
                .filter(c -> c.getStatus().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
