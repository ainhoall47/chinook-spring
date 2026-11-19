package org.ainhoall.controlador;

import org.ainhoall.modelo.Album;
import org.ainhoall.modelo.Pista;
import org.ainhoall.repositorio.AlbumRepository;
import org.ainhoall.repositorio.PistaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/albumnes")
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final PistaRepository pistaRepository;

    public AlbumController(AlbumRepository albumRepository, PistaRepository pistaRepository) {
        this.albumRepository = albumRepository;
        this.pistaRepository = pistaRepository;
    }

    // GET /albumes/{id}   Versión provisional, con los repositorios
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            return "<h1>No existe el álbum " + id + "</h1>";
        }
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(Html.esc(album.get().getTitulo())).append("</h1>");
        html.append("<ul>");
        html.append("<ol>");
        for (Pista pista : pistaRepository.findByAlbumIdOrderById(id)) {
            html.append("<li><a href=\"/pistas/").append(pista.getId()).append("\">")
                    .append(Html.esc(pista.getNombre())).append(" (").append(Html.esc(pista.getDuracion())).append(")</li>");
        }
        html.append("</ol>");
        return html.toString();
    }
}
