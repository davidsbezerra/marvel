package br.com.david.web;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.david.service.AutenticarMarvelService;
import br.com.david.service.ConsultarRegistrosCompletosService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/marvel")
@RequiredArgsConstructor
public class MarvelController {

    private final AutenticarMarvelService autenticarMarvelService;
    private final ConsultarRegistrosCompletosService consultarRegistrosCompletosService;

    @GetMapping("/autorizar")
    @ResponseStatus(OK)
    public void authorization(
        @RequestParam(value = "ts") final String ts,
        @RequestParam(value = "apikey") final String apikey,
        @RequestParam(value = "hash") final String hash) {

        autenticarMarvelService.autorizar(ts, apikey, hash);
    }

    @GetMapping("/buscar-registros")
    @ResponseStatus(OK)
    public void buscarRegistros(@RequestParam(value = "paramBusca") final String paramBusca) {

        consultarRegistrosCompletosService.consultar(paramBusca);
    }

}
