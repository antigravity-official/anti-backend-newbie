package antigravity.service;

import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("서비스 - 제품 요청 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProductResponseServiceTest {

    @InjectMocks
    private ProductResponseService sut;
    @Mock
    private ProductRepository productRepository;

    @DisplayName("검색 조건 없이 제품 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchingProduct_thenReturnsEntireEventList() {

//        //Given
//        given(productRepository.findEvents(null, null, null, null, null))
//                .willReturn(List.of(
//                        createEventDTO(1L, "오전 운동", true),
//                        createEventDTO(1L, "오후 운동", false)));
//
//        // When
//        List<EventDTO> list = sut.getEvents(null, null, null, null, null);
//
//        assertThat(list).hasSize(2);
//        then(eventRepository).should().findEvents(null,null,null,null,null);

    }

//    private EventDTO createEventDTO(long placeId, String eventName, boolean isMorning) {
//        String hourStart = isMorning ? "09" : "13";
//        String hourEnd = isMorning ? "12" : "16";
//
//        return createEventDTO(
//                placeId,
//                eventName,
//                EventStatus.OPENED,
//                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourStart)),
//                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourEnd))
//        );
//    }
}