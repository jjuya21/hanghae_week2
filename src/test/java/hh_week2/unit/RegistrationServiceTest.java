package hh_week2.unit;

import hh_week2.domain.registration.Registration;
import hh_week2.domain.registration.RegistrationEntity;
import hh_week2.domain.registration.RegistrationService;
import hh_week2.infrastructure.RegistrationRepository;
import hh_week2.interfaces.registration.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class RegistrationServiceTest {

    @Mock
    RegistrationRepository registrationRepository;

    @InjectMocks
    RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final static long DEFAULT_USER_ID = 1L;
    private final static long DEFAULT_LECTURE_ITEM_ID = 1L;

    @Test
    void getRegistrationTest() {
        // given
        RegistrationEntity registration = RegistrationEntity.builder()
                .id(1L)
                .userId(DEFAULT_USER_ID)
                .lectureItemId(DEFAULT_LECTURE_ITEM_ID)
                .build();
        RegistrationRequest request = RegistrationRequest.builder()
                .userId(DEFAULT_USER_ID)
                .lectureItemId(DEFAULT_LECTURE_ITEM_ID)
                .build();

        given(registrationRepository.findByUserIdAndLectureItemId(DEFAULT_USER_ID, DEFAULT_LECTURE_ITEM_ID))
                .willReturn(registration);

        // when
        Registration result = registrationService.getRegistration(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(DEFAULT_USER_ID);
        then(registrationRepository).should().findByUserIdAndLectureItemId(DEFAULT_USER_ID, DEFAULT_LECTURE_ITEM_ID);
    }

    @Test
    void registerTest() {
        // given
        RegistrationEntity savedRegistration = RegistrationEntity.builder()
                .id(1L)
                .userId(DEFAULT_USER_ID)
                .lectureItemId(DEFAULT_LECTURE_ITEM_ID)
                .build();
        RegistrationRequest request = RegistrationRequest.builder()
                .userId(DEFAULT_USER_ID)
                .lectureItemId(DEFAULT_LECTURE_ITEM_ID)
                .capacity(30)
                .build();

        given(registrationRepository.existsByUserIdAndLectureItemId(DEFAULT_USER_ID, DEFAULT_LECTURE_ITEM_ID)).willReturn(false);
        given(registrationRepository.findByLectureItemId(DEFAULT_LECTURE_ITEM_ID)).willReturn(new ArrayList<>());
        given(registrationRepository.save(any(RegistrationEntity.class))).willReturn(savedRegistration);

        // when
        Registration result = registrationService.register(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(result.getLectureItemId()).isEqualTo(DEFAULT_LECTURE_ITEM_ID);
        then(registrationRepository).should().save(any(RegistrationEntity.class));
    }

    @Test
    void getLectureItemIdsTest() {
        // given
        RegistrationEntity registrationEntity1 = RegistrationEntity.builder()
                .id(1L)
                .userId(DEFAULT_USER_ID)
                .lectureItemId(100L)
                .build();

        RegistrationEntity registrationEntity2 = RegistrationEntity.builder()
                .id(2L)
                .userId(DEFAULT_USER_ID)
                .lectureItemId(200L)
                .build();

        List<RegistrationEntity> registrationEntities = new ArrayList<>();
        registrationEntities.add(registrationEntity1);
        registrationEntities.add(registrationEntity2);

        RegistrationRequest request = RegistrationRequest.builder()
                .userId(DEFAULT_USER_ID)
                .build();

        given(registrationRepository.findByUserId(DEFAULT_USER_ID)).willReturn(registrationEntities);

        // when
        List<Registration> result = registrationService.getLectureItemIds(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(registrationEntity1.getId());
        assertThat(result.get(1).getId()).isEqualTo(registrationEntity2.getId());
        then(registrationRepository).should().findByUserId(DEFAULT_USER_ID);
    }
}
