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

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/lectureItems")
@RestController
@RequiredArgsConstructor
public class LectureItemController {

    private final LectureItemService lectureItemService;

    @GetMapping("/{date}")
    public ResponseEntity<List<LectureItemResponse>> getLectureItem(@PathVariable Timestamp date) {

        LectureItemRequest request = LectureItemRequest.builder()
                .date(date)
                .build();

        List<LectureItemResponse> response = lectureItemService.getLectureItemsByDate(request).stream()
                .map(LectureItemResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
