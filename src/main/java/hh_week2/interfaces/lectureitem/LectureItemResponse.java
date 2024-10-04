package hh_week2.interfaces.lectureitem;

import hh_week2.domain.lectureitem.LectureItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureItemResponse {

    private long id;

    private long lectureId;

    private Timestamp date;

    public static LectureItemResponse from(LectureItem lectureItem) {

        if (lectureItem == null) {
            return null;
        }

        return LectureItemResponse.builder()
                .id(lectureItem.getId())
                .lectureId(lectureItem.getLectureId())
                .date(lectureItem.getDate())
                .build();
    }
}
