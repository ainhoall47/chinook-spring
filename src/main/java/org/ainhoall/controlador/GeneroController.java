package org.ainhoall.controlador;

import org.ainhoall.modelo.Album;
import org.ainhoall.modelo.Genero;
import org.ainhoall.servicio.ArtistaService;
import org.ainhoall.servicio.GeneroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        StringBuilder html = new StringBuilder();
        html.append("<h1>Géneros</h1><ul>");
        for (Genero genero : generoService.listar()) {
            html.append("<li>").append(Html.esc(genero.getNombre())).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }

}
