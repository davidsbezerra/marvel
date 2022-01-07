package br.com.david.integration.marvel;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.david.domain.characters.Character;
import br.com.david.integration.marvel.response.MarvelAuthorizationResponse;
import br.com.david.rest.AbstractRestService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Marvelntegration extends AbstractRestService {

    @Value("${services.api-marvel.url}")
    private String baseUrl;

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    protected HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    public MarvelAuthorizationResponse comics(final String ts, final String apikey, final String hash) {
        log.info("Consumino serviço de autorização hash {} {} {}", hash);

        final String url = String.format("/v1/public/comics?ts=%s&apikey=%s&hash=%s", ts, apikey, hash);

        return super.get(url, MarvelAuthorizationResponse.class, getHttpHeaders());

    }

    public Character consultarRegistros(final Map<String, Object> queryParamMap) {

        final UriComponentsBuilder endpointUrl = UriComponentsBuilder.fromPath("/v1/public/characters");

        if (nonNull(queryParamMap)) {

            for (Entry<String, Object> queryParam : queryParamMap.entrySet()) {
                endpointUrl.queryParam(queryParam.getKey(), queryParam.getValue());
            }
        }

        log.info("Chamando serviço {}", endpointUrl.toUriString());

        return super.get(endpointUrl.toUriString(), Character.class, getHttpHeaders());
     }
}