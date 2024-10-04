package hh_week2.domain.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    private long id;

    private long lectureItemId;

    private long userId;

    public static Registration from(RegistrationEntity entity) {

        if (entity == null) {
            return null;
        }

        return Registration.builder()
                .id(entity.getId())
                .lectureItemId(entity.getLectureItemId())
                .userId(entity.getUserId())
                .build();
    }
}