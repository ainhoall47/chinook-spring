package org.ainhoall.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InicioController {
    private int visitas = 0;

    @GetMapping({"/", "/inicio"})
    @ResponseBody
    public String portada() {
        visitas++;
        return "<h1>Chinook</h1>"
                + "<p>Bienvenida a la tienda de música Chinook.</p>"
                + "<ul>"
                + "<li><a href=\"/generos\">Ver los géneros</a></li>"
                + "<li><a href=\"/artistas/buscar?texto=metal\">Artistas que contienen «metal»</a></li>"
                + "<li><a href=\"/artistas/1\">Ficha de AC/DC</a></li>"
                + "</ul>"
                + "<p><small>Visitas a la portada desde que arrancó el servidor: " + visitas + "</small></p>";
    }
}
