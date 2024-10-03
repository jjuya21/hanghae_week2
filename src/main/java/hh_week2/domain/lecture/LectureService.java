package hh_week2.domain.lecture;

import hh_week2.infrastructure.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public Lecture getLectureByLectureId(long lectureId) {

        Lecture lecture = Lecture.from(lectureRepository.findById(lectureId));

        return lecture;
    }
}
