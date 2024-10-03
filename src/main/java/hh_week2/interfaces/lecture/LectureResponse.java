package hh_week2.interfaces.lecture;

import hh_week2.domain.lecture.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureResponse {

    private long id;

    private String lectureName;

    private String lecturerName;

    public static LectureResponse from(Lecture lecture) {

        if (lecture == null) {
            return null;
        }

        return LectureResponse.builder()
                .id(lecture.getId())
                .lectureName(lecture.getLectureName())
                .lecturerName(lecture.getLecturerName())
                .build();
    }
}
