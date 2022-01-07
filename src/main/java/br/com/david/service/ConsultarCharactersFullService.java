package br.com.david.service;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.david.domain.CacheName;
import br.com.david.domain.characters.Character;
import br.com.david.domain.characters.Data;
import br.com.david.domain.characters.Result;
import br.com.david.integration.marvel.Marvelntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarCharactersFullService {

    private final Marvelntegration marvelntegration;

    @Cacheable(CacheName.BUSCA_REGISTROS_TOTAIS_NOME_CORRETO)
    public List<Result> consultar(final String ts, final String apikey, final String hash, final Integer offset) {

        log.info("Consultando registros completo para o ts {}, apikey {}, hash {}", ts, apikey, hash);

        return this.buscarRegistrosResponseEmTodasPaginas(ts, apikey, hash, offset);
    }

    private List<Result> buscarRegistrosResponseEmTodasPaginas(final String ts, final String apikey,
        final String hash, final Integer offset) {

        final List<Result> registrosTotais = new ArrayList<>();

        final AtomicBoolean hasNextPage = new AtomicBoolean(false);
        final AtomicReference<Integer> offsetAtomic = new AtomicReference<Integer>(0);

        Optional.ofNullable(offset)
            .map(o -> {
                offsetAtomic.set(o);
                return o;
            });

        do {

            final Character registros = marvelntegration.consultarRegistros(
                gerarParametrosRequest(ts, apikey, hash, offsetAtomic.get(), 100));

            Optional.ofNullable(registros)
                .map(Character::getData)
                .map(d -> {
                    offsetAtomic.set(registros.getData().getOffset() + registros.getData().getCount());
                    if (offsetAtomic.get() < registros.getData().getTotal()) {
                        hasNextPage.set(true);
                    } else {
                        hasNextPage.set(false);
                    }
                    return d;
                })
                .map(Data::getResults)
                .map(registrosTotais::addAll);
        } while (hasNextPage.get());

        log.info("Registros completos consultados ");

        return registrosTotais;
    }

    private Map<String, Object> gerarParametrosRequest(final String ts, final String apikey, final String hash,
        final Integer Offset, final Integer limit) {

        final Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("ts", ts);
        queryParamMap.put("apikey", apikey);
        queryParamMap.put("hash", hash);

        ofNullable(limit)
            .map(l -> queryParamMap.put("limit", l));
        ofNullable(Offset)
            .map(o -> queryParamMap.put("offset", o));

        return queryParamMap;
    }

}