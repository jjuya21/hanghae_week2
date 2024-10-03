package hh_week2.integration;

import hh_week2.domain.registration.Registration;
import hh_week2.domain.registration.RegistrationEntity;
import hh_week2.domain.registration.RegistrationService;
import hh_week2.exception.AlreadyEnrolledInLectureException;
import hh_week2.exception.SeatsFullException;
import hh_week2.infrastructure.RegistrationRepository;
import hh_week2.interfaces.registration.RegistrationRequest;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RegistrationServiceTest {

    @MockBean
    private RegistrationRepository registrationRepository;

    @Autowired
    private RegistrationService registrationService;

    private final static long DEFAULT_USER_ID = 1L;
    private final static long DEFAULT_LECTURE_ITEM_ID = 1L;
    private final static int DEFAULT_CAPACITY = 30;

    @DisplayName("강의 참가 신청을 하면 userid와 lectureItemId가 저장이 되어야한다")
    @Test
    void 신청() {
        // given
        RegistrationRequest request = new RegistrationRequest(DEFAULT_USER_ID, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY);

        // when
        Registration result = registrationService.register(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(request.getUserId());
    }

    @DisplayName("동시의 40번의 강의신청을 하면 capacity 수 만큼만 성공해야한다.")
    @Test
    void 동시에_강의신청() throws InterruptedException {
        // given
        var threadCount = 40;

        // when
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            final int userId = i;
            executor.execute(() -> {
                try {
                    var request = new RegistrationRequest(userId, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY);
                    registrationService.register(request);
                } catch (Exception ignored) {

                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();

        // then
        int result = registrationRepository.findByLectureItemId(DEFAULT_LECTURE_ITEM_ID).size();
        assertThat(result).isEqualTo(DEFAULT_CAPACITY);
    }

    @DisplayName("capacity보다 많은 신청이 발생하면 SeatsFullException이 발생한다.")
    @Test
    void 강의신청_30번_이상() {
        // given
        var count = 40;

        // when
        for (int i = 1; i <= count; i++) {
            final int userId = i;
            var request = new RegistrationRequest(userId, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY);
            registrationService.register(request);
        }

        // then
        AssertionsForClassTypes.assertThatThrownBy(() -> registrationService.register((new RegistrationRequest(100L, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY))))
                .isInstanceOf(SeatsFullException.class);
    }

    @DisplayName("한 강의에 복수 신청을 하면 한 번만 신청되어야한다.")
    @Test
    void 같은_강의_복수신청() throws InterruptedException {
        // given
        long userId_1 = 101L;
        long userId_2 = 102L;

        // when
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> registrationService.register(new RegistrationRequest(userId_2, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY)));
            executor.submit(() -> registrationService.register(new RegistrationRequest(userId_1, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY)));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertThat(registrationRepository.findByUserId(userId_1)).hasSize(1);
        assertThat(registrationRepository.findByUserId(userId_2)).hasSize(1);
        verify(registrationRepository, times(2)).save(any(RegistrationEntity.class));
    }

    @DisplayName("한 유저가 같은 강의를 신청하면 AlreadyEnrolledInLectureException이 발생한다.")
    @Test
    void 같은_강의신청() {
        // given
        var count = 5;

        // when
        for (int i = 1; i <= count; i++) {
            var request = new RegistrationRequest(DEFAULT_USER_ID, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY);
            registrationService.register(request);
        }

        // then
        AssertionsForClassTypes.assertThatThrownBy(() -> registrationService.register((new RegistrationRequest(DEFAULT_USER_ID, DEFAULT_LECTURE_ITEM_ID, DEFAULT_CAPACITY))))
                .isInstanceOf(AlreadyEnrolledInLectureException.class);
    }
}
