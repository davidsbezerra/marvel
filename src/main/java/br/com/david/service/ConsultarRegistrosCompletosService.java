package br.com.david.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.david.integration.marvel.Marvelntegration;
import br.com.david.domain.CacheName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarRegistrosCompletosService {

    private final Marvelntegration marvelntegration;

    @Cacheable(CacheName.BUSCA_REGISTROS_TOTAIS_NOME_CORRETO)
    public List<Object> consultar(final String paramBusca) {

        log.info("Consultando registros completo para o param {}", paramBusca);

        return this.buscarRegistrosResponseEmTodasPaginas(paramBusca);
    }

    private List<Object> buscarRegistrosResponseEmTodasPaginas(final String paramBusca) {

        final List<Object> registrosTotais = new ArrayList<>();

        boolean hasNextPage = true;

        for (int pagina = 0; hasNextPage; pagina++) {

            final List<Object> registros = marvelntegration.consultarRegistros(
                paramBusca, pagina);

            Optional.ofNullable(registros)
                .map(registrosTotais::addAll);

            hasNextPage = false; //TODO: VERIRICAR LÓGICA DE ACORDO COM RETORNO DO SERVIÇO DA MARVEL
        }

        log.info("Registros completos consultados {}", paramBusca);

        return registrosTotais;
    }

}