package org.ainhoall.controlador;

import org.ainhoall.dto.AlbumResumen;
import org.ainhoall.dto.ArtistaDetalle;
import org.ainhoall.dto.ArtistaResumen;
import org.ainhoall.servicio.ArtistaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public String inicio() {
        return "redirect:/artistas/buscar";
    }

    @GetMapping("/buscar")
    @ResponseBody
    public String buscar(@RequestParam(defaultValue = "") String texto) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("<form action=\"/artistas/buscar\" method=\"get\">")
                .append("<input name=\"texto\" value=\"").append(Html.esc(texto)).append("\">")
                .append(" <button>Buscar</button></form>");
        if (!texto.isBlank()) {
            List<ArtistaResumen> artistas = artistaService.buscarPorNombre(texto);
            cuerpo.append("<p>Artistas que contienen «").append(Html.esc(texto)).append("»: ")
                    .append(artistas.size()).append("</p><ul>");
            for (ArtistaResumen artista : artistas) {
                cuerpo.append("<li><a href=\"/artistas/").append(artista.id()).append("\">")
                        .append(Html.esc(artista.nombre())).append("</a></li>");
            }
            cuerpo.append("</ul>");
        }
        return Html.pagina("Buscar artistas", cuerpo.toString());
    }

    // GET /artistas/{id}
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        ArtistaDetalle artista = artistaService.buscarPorId(id);
        StringBuilder cuerpo = new StringBuilder();
        if (artista.albumes().isEmpty()) {
            cuerpo.append("<p>Este artista no tiene álbumes en la tienda.</p>");
        } else {
            cuerpo.append("<p>").append(artista.albumes().size()).append(" álbumes:</p><ul>");
            for (AlbumResumen album : artista.albumes()) {
                cuerpo.append("<li><a href=\"/albumes/").append(album.id()).append("\">")
                        .append(Html.esc(album.titulo())).append("</a></li>");
            }
            cuerpo.append("</ul>");
        }
        return Html.pagina(artista.nombre(), cuerpo.toString());
    }
}
