package hh_week2.domain.registration;

import hh_week2.exception.AlreadyEnrolledInLectureException;
import hh_week2.exception.SeatsFullException;
import hh_week2.infrastructure.RegistrationRepository;
import hh_week2.interfaces.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public Registration getRegistration(RegistrationRequest request) {

        Registration registration = Registration.from(registrationRepository.findByUserIdAndLectureItemId(
                request.getUserId(), request.getLectureItemId()
        ));
        return registration;
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public Registration register(RegistrationRequest request) {

        var count = registrationRepository.findByLectureItemId(request.getLectureItemId()).size();

        if (count >= request.getCapacity()) {
            log.info("잔여석이 없습니다.");
            throw new SeatsFullException("잔여석이 없습니다.");
        }

        Boolean exists = registrationRepository.existsByUserIdAndLectureItemId(
                request.getUserId(),
                request.getLectureItemId()
        );

        if (exists) {
            throw new AlreadyEnrolledInLectureException("이미 신청된 강의입니다.");
        }

        RegistrationEntity newRegistration = RegistrationEntity.builder()
                .lectureItemId(request.getLectureItemId())
                .userId(request.getUserId())
                .build();

        registrationRepository.save(newRegistration);

        log.info("saved user id: {}", newRegistration.getUserId());
        return Registration.from(newRegistration);
    }

    public List<Registration> getLectureItemIds(RegistrationRequest request) {

        List<Registration> registrations = registrationRepository.findByUserId(request.getUserId()).stream()
                .map(Registration::from)
                .collect(Collectors.toList());

        return registrations;
    }
}
