package br.com.david.service;

import org.springframework.stereotype.Service;

import br.com.david.integration.marvel.Marvelntegration;
import br.com.david.integration.marvel.response.MarvelAuthorizationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutenticarMarvelService {

    private final Marvelntegration marvelntegration;

    public MarvelAuthorizationResponse autorizar(final String ts, final String apikey, final String hash) {

        log.info("Autorizando para o hash {}", hash);

        return marvelntegration.authorization(ts, apikey, hash);
     }

}