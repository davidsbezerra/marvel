package br.com.david.domain;

import static java.util.Arrays.stream;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorType {

    BUSINESS(HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    VALIDATION(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    public static ErrorType getByHttpStatus(final HttpStatus httpStatus) {
        return stream(ErrorType.values())
            .filter(errorType -> errorType.getHttpStatus().equals(httpStatus))
            .findFirst()
            .orElse(null);
    }

}
