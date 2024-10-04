package hh_week2.infrastructure;

import hh_week2.domain.lectureitem.LectureItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LectureItemRepository extends JpaRepository<LectureItemEntity, Long> {

    List<LectureItemEntity> findByLectureId(Long lectureId);

    List<LectureItemEntity> findByDate(Timestamp date);
}
