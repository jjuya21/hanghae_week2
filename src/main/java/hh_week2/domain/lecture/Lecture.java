package hh_week2.domain.lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    private Long id;

    private String lectureName;

    private String lecturerName;

    public static Lecture from(LectureEntity entity) {

        if (entity == null) {
            return null;
        }

        return Lecture.builder()
                .id(entity.getId())
                .lectureName(entity.getLectureName())
                .lecturerName(entity.getLecturerName())
                .build();
    }
}
