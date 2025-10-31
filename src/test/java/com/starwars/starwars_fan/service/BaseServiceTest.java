package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class BaseServiceTest {

    private final DummyService service = new DummyService();

    @Test
    void validatePaginationParams_shouldThrowException_whenPageOrSizeInvalid() {
        assertThatThrownBy(() -> service.validatePaginationParams(0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("greater than zero");

        assertThatThrownBy(() -> service.validatePaginationParams(1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("greater than zero");
    }

    @Test
    void paginate_shouldReturnCorrectSublist_whenWithinRange() {
        List<String> data = List.of("A", "B", "C", "D", "E");

        PagedResponse<String> page1 = service.paginate(data, 1, 2);
        assertThat(page1.getItems()).containsExactly("A", "B");
        assertThat(page1.getPage()).isEqualTo(1);
        assertThat(page1.getTotalPages()).isEqualTo(3);

        PagedResponse<String> page3 = service.paginate(data, 3, 2);
        assertThat(page3.getItems()).containsExactly("E");
        assertThat(page3.getPage()).isEqualTo(3);
        assertThat(page3.getTotalPages()).isEqualTo(3);
    }

    @Test
    void paginate_shouldThrowException_whenPageOutOfRange() {
        List<String> data = List.of("A", "B");

        assertThatThrownBy(() -> service.paginate(data, 3, 1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("out of range");
    }

    @Test
    void ensureNotEmpty_shouldThrowException_whenListEmpty() {
        assertThatThrownBy(() -> service.ensureNotEmpty(List.of(), "No data"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No data");
    }

    @Test
    void ensureNotEmpty_shouldPass_whenListNotEmpty() {
        assertThatCode(() -> service.ensureNotEmpty(List.of("X"), "Error"))
                .doesNotThrowAnyException();
    }

    private static class DummyService extends BaseService<String> {
    }
}
