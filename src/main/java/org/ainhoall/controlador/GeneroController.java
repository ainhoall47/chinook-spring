package org.ainhoall.controlador;

import org.ainhoall.dto.GeneroResumen;
import org.ainhoall.modelo.Album;
import org.ainhoall.modelo.Genero;
import org.ainhoall.servicio.ArtistaService;
import org.ainhoall.servicio.GeneroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    @ResponseBody
    public String listar() {
        List<GeneroResumen> generos = generoService.listar();
        StringBuilder cuerpo = new StringBuilder();
        for (GeneroResumen genero : generos) {
            cuerpo.append("<li>").append(Html.esc(genero.nombre())).append(" (")
                    .append(genero.numeroPistas()).append(" pistas)</li>");
        }
        cuerpo.append("</ul>");
        return Html.pagina("Géneros", cuerpo.toString());
    }
}
