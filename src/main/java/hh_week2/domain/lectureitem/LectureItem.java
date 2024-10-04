package hh_week2.domain.lectureitem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureItem {

    private long id;

    private long lectureId;

    private Timestamp date;

    private int capacity;

    public static LectureItem from(LectureItemEntity entity) {

        if (entity == null) {
            return null;
        }

        return LectureItem.builder()
                .id(entity.getId())
                .lectureId(entity.getLectureId())
                .date(entity.getDate())
                .capacity(entity.getCapacity())
                .build();
    }
}
