package hh_week2.application;

import hh_week2.domain.lecture.Lecture;
import hh_week2.domain.lecture.LectureService;
import hh_week2.domain.lectureitem.LectureItem;
import hh_week2.domain.lectureitem.LectureItemService;
import hh_week2.domain.registration.Registration;
import hh_week2.domain.registration.RegistrationService;
import hh_week2.interfaces.lectureitem.LectureItemRequest;
import hh_week2.interfaces.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetLectureFacade {

    private final LectureService lectureService;
    private final LectureItemService lectureItemService;
    private final RegistrationService registrationService;


    public List<Lecture> getLecture(GetLectureRequest request) {

        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .userId(request.getUserId())
                .build();

        List<Registration> registrations = registrationService.getLectureItemIds(registrationRequest);

        List<Lecture> lectures = new ArrayList<Lecture>();

        for (Registration registration : registrations) {
            LectureItemRequest lectureItemRequest = LectureItemRequest.builder()
                    .lectureItemId(registration.getLectureItemId())
                    .build();

            List<LectureItem> lectureItems = lectureItemService.getLectureItems(lectureItemRequest);

            for (LectureItem lectureItem : lectureItems) {
                lectures.add(lectureService.getLectureByLectureId(lectureItem.getLectureId()));
            }
        }

        return lectures;
    }
}
