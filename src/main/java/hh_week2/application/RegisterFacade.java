package hh_week2.application;

import hh_week2.domain.lectureitem.LectureItem;
import hh_week2.domain.lectureitem.LectureItemService;
import hh_week2.domain.registration.RegistrationService;
import hh_week2.interfaces.lectureitem.LectureItemRequest;
import hh_week2.interfaces.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegisterFacade {

    private final RegistrationService registrationService;
    private final LectureItemService lectureItemService;

    @Transactional
    public LectureItem register(RegisterRequest request) {

        LectureItemRequest lectureItemRequest = LectureItemRequest.builder()
                .lectureItemId(request.getLectureItemId())
                .build();

        int capacity = lectureItemService.getLectureItem(lectureItemRequest).getCapacity();

        registrationService.register(RegistrationRequest.builder()
                .lectureItemId(request.getLectureItemId())
                .userId(request.getUserId())
                .capacity(capacity)
                .build());

        LectureItem lectureItem = lectureItemService.getLectureItem(lectureItemRequest);

        return lectureItem;
    }
}
