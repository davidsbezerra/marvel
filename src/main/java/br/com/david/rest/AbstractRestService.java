package br.com.david.rest;

import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.david.exception.ClientErrorException;
import br.com.david.domain.ErrorType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRestService {

    @Autowired
    private RestTemplate restTemplate;

    public <T> T post(final String url, final Object request, final Class<T> responseClass,
                      final HttpHeaders httpHeaders) {
        return abstractRequest(HttpMethod.POST, url, request, responseClass, httpHeaders);
    }

    public <T> T get(final String url, final Class<T> responseClass, final HttpHeaders httpHeaders) {
        return abstractRequest(HttpMethod.GET, url, null, responseClass, httpHeaders);
    }
    
    public <T> T delete(final String url, final Class<T> responseClass, final HttpHeaders httpHeaders) {
        return abstractRequest(HttpMethod.DELETE, url, null, responseClass, httpHeaders);
    }

    protected <T> T abstractRequest(final HttpMethod requestMethod, final String url, final Object request,
                                    final Class<T> responseClass, final HttpHeaders httpHeaders) {
        final HttpEntity<?> httpEntity = getHttpEntity(request, httpHeaders);

        try {
            final ResponseEntity<T> response = restTemplate.exchange(getBaseUrl() + url, requestMethod, httpEntity, responseClass);
            return response.getBody();
        } catch (final IllegalStateException responseBodyNullException) {
            return null;
        } catch (final HttpStatusCodeException ex) {
            throw getError(ex);
        }
    }

    protected <T> HttpEntity<T> getHttpEntity(final T request, final HttpHeaders httpHeaders) {
        return new HttpEntity<>(request, httpHeaders);
    }



    protected ClientErrorException getError(final HttpStatusCodeException ex) {
        log.error("Falha na API: [httpStatusCode:{}] [responseBody:{}]", ex.getRawStatusCode(),
                ex.getResponseBodyAsString());
        final ErrorType errorType = ofNullable(ErrorType.getByHttpStatus(ex.getStatusCode()))
                .orElse(ErrorType.BUSINESS);
        return new ClientErrorException(errorType, ex.getMessage());
    }

    protected abstract String getBaseUrl();
}
