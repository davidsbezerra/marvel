package br.com.david.integration.marvel.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MarvelAuthorizationResponse implements Serializable {

    private static final long serialVersionUID = 8381713744448876234L;

    @JsonProperty(value = "code")
    private final LocalDateTime code;

    @JsonProperty(value = "message")
    private final String message;


}