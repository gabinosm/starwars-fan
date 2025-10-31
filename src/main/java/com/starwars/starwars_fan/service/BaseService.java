package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.exception.ResourceNotFoundException;
import com.starwars.starwars_fan.exception.SwapiClientException;

import java.util.List;
import java.util.function.Supplier;

public class BaseService<T> {

    protected void validatePaginationParams(int page, int size) {
        if (page <= 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be greater than zero");
        }
    }

    protected PagedResponse<T> paginate(List<T> elements, int page, int size) {
        int totalElements = elements.size();
        int fromIndex = Math.min((page - 1) * size, totalElements);
        int toIndex = Math.min(fromIndex + size, totalElements);

        if (fromIndex >= totalElements) {
            throw new ResourceNotFoundException("Page " + page + " is out of range");
        }

        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<T> paginated = elements.subList(fromIndex, toIndex);

        return new PagedResponse<>(page, size, totalElements, totalPages, paginated);
    }

    protected void ensureNotEmpty(List<T> list, String messageIfEmpty) {
        if (list == null || list.isEmpty()) {
            throw new ResourceNotFoundException(messageIfEmpty);
        }
    }

    protected <R> R safeCall(Supplier<R> supplier, String errorMessage) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new SwapiClientException(errorMessage, e);
        }
    }
}
