package br.com.david.web;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.david.domain.characters.Result;
import br.com.david.service.ConsultarMarvelComicsService;
import br.com.david.service.ConsultarCharactersFullService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/marvel")
@RequiredArgsConstructor
public class MarvelController {

    private final ConsultarMarvelComicsService autenticarMarvelService;
    private final ConsultarCharactersFullService consultarCharactersFullService;

    @GetMapping("/listar-characters")
    @ResponseStatus(OK)
    public List<Result> listarCharacters(
        @RequestParam(value = "ts") final String ts,
        @RequestParam(value = "apikey") final String apikey,
        @RequestParam(value = "hash") final String hash,
        @RequestParam(value = "offset", required = false) final Integer offset){

       return  consultarCharactersFullService.consultar(ts, apikey, hash, offset);
    }

}
