package ru.practicum.shareit.global.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.global.error.InvalidPageSizeException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ValidatorTest {

    @Test
    void validatePageSize_whenFromIsZero_thenThrowInvalidPageSizeException() {
        Assertions.assertThrows(
                InvalidPageSizeException.class,
                () -> Validator.validatePageSize(0, 0));
    }

    @Test
    void validatePageSize_whenValid_thenReturnVoid() {
        Assertions.assertDoesNotThrow(() -> Validator.validatePageSize(1, 1));
    }

    @Test
    void isEmptyPageSize_whenNull_thenReturnTrue() {
        Boolean emptyPage = Validator.isEmptyPageSize(null, null);
        assertThat(emptyPage, equalTo(true));
    }

    @Test
    void isEmptyPageSize_whenNotNull_thenReturnfalse() {
        Boolean emptyPage = Validator.isEmptyPageSize("1", "5");
        assertThat(emptyPage, equalTo(false));
    }
}