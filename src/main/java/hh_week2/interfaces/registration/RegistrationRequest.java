package hh_week2.interfaces.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private long userId;
    private long lectureItemId;
    private int capacity;
}
