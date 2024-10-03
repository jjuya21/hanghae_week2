package hh_week2.unit;

import hh_week2.domain.lecture.Lecture;
import hh_week2.domain.lecture.LectureEntity;
import hh_week2.domain.lecture.LectureService;
import hh_week2.infrastructure.LectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    private final static long DEFAULT_LECTURE_ID = 1L;

    @Test
    void getLectureByLectureIdTest() {
        // given
        LectureEntity lectureEntity = LectureEntity.builder()
                .id(DEFAULT_LECTURE_ID)
                .build();

        // Mocking: 강의 ID로 강의 정보 조회
        given(lectureRepository.findById(DEFAULT_LECTURE_ID)).willReturn(lectureEntity);

        // when
        Lecture result = lectureService.getLectureByLectureId(DEFAULT_LECTURE_ID);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(DEFAULT_LECTURE_ID);
        verify(lectureRepository).findById(DEFAULT_LECTURE_ID);
    }

    @Test
    void getLectureByLectureId_NonExistentId_Test() {
        // given
        given(lectureRepository.findById(DEFAULT_LECTURE_ID)).willReturn(null);

        // when
        Lecture result = lectureService.getLectureByLectureId(DEFAULT_LECTURE_ID);

        // then
        assertThat(result).isNull();
        verify(lectureRepository).findById(DEFAULT_LECTURE_ID);
    }
}