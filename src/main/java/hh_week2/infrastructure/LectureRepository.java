package hh_week2.infrastructure;

import hh_week2.domain.lecture.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long> {

    LectureEntity findById(long id);
}
