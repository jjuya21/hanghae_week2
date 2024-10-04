package hh_week2.domain.lectureitem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "LECTUREITEM")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureItemEntity {

    @Id
    @Column(name = "lecture_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_id")
    private Long lectureId;

    private Timestamp date;

    private Integer capacity;
}
