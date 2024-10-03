package hh_week2.unit;

import hh_week2.domain.lectureitem.LectureItem;
import hh_week2.domain.lectureitem.LectureItemEntity;
import hh_week2.domain.lectureitem.LectureItemService;
import hh_week2.infrastructure.LectureItemRepository;
import hh_week2.interfaces.lectureitem.LectureItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LectureItemServiceTest {

    @Mock
    private LectureItemRepository lectureItemRepository;

    @InjectMocks
    private LectureItemService lectureItemService;

    private static final long DEFAULT_LECTURE_ITEM_ID = 1L;
    private static final long DEFAULT_LECTURE_ID = 2L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLectureItemTest() {
        // given
        LectureItemRequest request = LectureItemRequest.builder()
                .lectureItemId(DEFAULT_LECTURE_ITEM_ID)
                .build();

        LectureItemEntity lectureItemEntity = LectureItemEntity.builder()
                .id(DEFAULT_LECTURE_ITEM_ID)
                .build();

        given(lectureItemRepository.findById(DEFAULT_LECTURE_ITEM_ID)).willReturn(Optional.of(lectureItemEntity));

        // when
        LectureItem result = lectureItemService.getLectureItem(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(DEFAULT_LECTURE_ITEM_ID);
        verify(lectureItemRepository).findById(DEFAULT_LECTURE_ITEM_ID);
    }

    @Test
    void getLectureItemsTest() {
        // given
        LectureItemRequest request = LectureItemRequest.builder()
                .lectureItemId(DEFAULT_LECTURE_ID)
                .build();

        LectureItemEntity lectureItemEntity = LectureItemEntity.builder()
                .id(DEFAULT_LECTURE_ITEM_ID)
                .build();

        given(lectureItemRepository.findByLectureId(DEFAULT_LECTURE_ID)).willReturn(Collections.singletonList(lectureItemEntity));

        // when
        List<LectureItem> result = lectureItemService.getLectureItems(request);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(DEFAULT_LECTURE_ITEM_ID);
        verify(lectureItemRepository).findByLectureId(DEFAULT_LECTURE_ID);
    }

    @Test
    void getLectureItemsByDateTest() {
        // given
        LectureItemRequest request = LectureItemRequest.builder()
                .date(Timestamp.valueOf("2024-10-14"))
                .build();

        LectureItemEntity lectureItemEntity = LectureItemEntity.builder()
                .id(DEFAULT_LECTURE_ITEM_ID)
                .build();

        given(lectureItemRepository.findByDate(request.getDate())).willReturn(Collections.singletonList(lectureItemEntity));

        // when
        List<LectureItem> result = lectureItemService.getLectureItemsByDate(request);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(DEFAULT_LECTURE_ITEM_ID);
        verify(lectureItemRepository).findByDate(request.getDate());
    }
}
