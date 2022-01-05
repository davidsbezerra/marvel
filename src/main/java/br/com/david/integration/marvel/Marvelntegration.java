package br.com.david.integration.marvel;

import java.util.List;

 import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

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

    public MarvelAuthorizationResponse authorization(final String ts, final String apikey, final String hash) {
        log.info("Consumino serviço de autorização hash {} {} {}", hash);

        final String url = String.format("/v1/public/comics?ts=%s&apikey=%s&hash=%s", ts, apikey, hash);

        return super.get(url, MarvelAuthorizationResponse.class, getHttpHeaders());

    }

    public List<Object> consultarRegistros(final String paramBusca, final int pagina) {
        return null; //TODO: CHAMAR SERVIÇO CORRETO DE ACORDO COM PARÂMETROS
    }
}