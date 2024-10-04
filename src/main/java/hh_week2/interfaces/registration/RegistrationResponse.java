package hh_week2.interfaces.registration;

import hh_week2.domain.registration.Registration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

    private long id;
    private long userId;
    private long lectureItemId;

    public static RegistrationResponse from(Registration registration) {

        if (registration == null) {
            return null;
        }

        return RegistrationResponse.builder()
                .id(registration.getId())
                .userId(registration.getUserId())
                .lectureItemId(registration.getLectureItemId())
                .build();
    }
}
