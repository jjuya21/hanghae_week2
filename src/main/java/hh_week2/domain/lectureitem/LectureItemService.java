package hh_week2.domain.lectureitem;

import hh_week2.infrastructure.LectureItemRepository;
import hh_week2.interfaces.lectureitem.LectureItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureItemService {

    private final LectureItemRepository lectureItemRepository;

    public LectureItem getLectureItem(LectureItemRequest request) {

        return LectureItem.from(lectureItemRepository.findById(request.getLectureItemId()).orElseThrow());
    }

    public List<LectureItem> getLectureItems(LectureItemRequest request) {

        return lectureItemRepository.findByLectureId(request.getLectureItemId()).stream()
                .map(LectureItem::from)
                .collect(Collectors.toList());
    }

    public List<LectureItem> getLectureItemsByDate(LectureItemRequest request) {

        return lectureItemRepository.findByDate(request.getDate())
                .stream()
                .map(LectureItem::from)
                .collect(Collectors.toList());
    }
}
