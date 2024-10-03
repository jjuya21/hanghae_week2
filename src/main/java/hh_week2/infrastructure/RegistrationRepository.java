package hh_week2.infrastructure;

import hh_week2.domain.registration.RegistrationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Boolean existsByUserIdAndLectureItemId(Long userId, Long lectureItemId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<RegistrationEntity> findByLectureItemId(Long lectureItemId);

    List<RegistrationEntity> findByUserId(Long userId);

    RegistrationEntity findByUserIdAndLectureItemId(Long userId, Long lectureItemId);
}
