package hh_week2.interfaces.api;

import hh_week2.application.GetLectureFacade;
import hh_week2.application.GetLectureRequest;
import hh_week2.application.RegisterFacade;
import hh_week2.application.RegisterRequest;
import hh_week2.domain.registration.RegistrationService;
import hh_week2.interfaces.lecture.LectureResponse;
import hh_week2.interfaces.lectureitem.LectureItemResponse;
import hh_week2.interfaces.registration.RegistrationRequest;
import hh_week2.interfaces.registration.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/registration")
@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegisterFacade registerFacade;
    private final GetLectureFacade getLectureFacade;
    private final RegistrationService registrationService;

    @PostMapping("/{userId}/{lectureItemId}")
    public ResponseEntity<LectureItemResponse> register(
            @PathVariable("userId") long userId, @PathVariable("lectureItemId") long lectureItemId
    ) {
        RegisterRequest request = RegisterRequest.builder()
                .userId(userId)
                .lectureItemId(lectureItemId)
                .build();

        LectureItemResponse response = LectureItemResponse.from(registerFacade.register(request));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/{lectureItemId}")
    public ResponseEntity<RegistrationResponse> registerExists(
            @PathVariable("userId") long userId, @PathVariable("lectureItemId") long lectureItemId
    ) {
        RegistrationRequest request = RegistrationRequest.builder()
                .userId(userId)
                .lectureItemId(lectureItemId)
                .build();

        RegistrationResponse response = RegistrationResponse.from(registrationService.getRegistration(request));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/lecture")
    public ResponseEntity<List<LectureResponse>> listLecture(@PathVariable("userId") long userId) {

        GetLectureRequest request = GetLectureRequest.builder().userId(userId).build();

        List<LectureResponse> response = getLectureFacade.getLecture(request).stream()
                .map(LectureResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
