package hh_week2.interfaces.api;

import hh_week2.domain.lectureitem.LectureItemService;
import hh_week2.interfaces.lectureitem.LectureItemRequest;
import hh_week2.interfaces.lectureitem.LectureItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureItemService lectureItemService;

    @GetMapping("/{lectureId}")
    public ResponseEntity<List<LectureItemResponse>> getLectureItemsByLectureId(@PathVariable("lectureId") Long lectureId) {

        LectureItemRequest request = LectureItemRequest.builder()
                .lectureId(lectureId)
                .build();

        List<LectureItemResponse> response = lectureItemService.getLectureItems(request).stream()
                .map(LectureItemResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
