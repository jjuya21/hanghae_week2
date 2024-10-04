package hh_week2.interfaces.lectureitem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureItemRequest {

    private long lectureItemId;
    private long lectureId;
    private Timestamp date;
}
