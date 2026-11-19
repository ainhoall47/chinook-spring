package org.ainhoall.controlador;

import org.ainhoall.dto.AlbumDetalle;
import org.ainhoall.dto.ArtistaResumen;
import org.ainhoall.dto.PistaResumen;
import org.ainhoall.modelo.Album;
import org.ainhoall.modelo.Pista;
import org.ainhoall.repositorio.AlbumRepository;
import org.ainhoall.repositorio.PistaRepository;
import org.ainhoall.servicio.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/albumes")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // GET /albumes/{id}   Versión provisional, con los repositorios
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        AlbumDetalle album = albumService.buscarPorId(id);
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(Html.esc(album.titulo())).append("</h1>");
        html.append("<p>De <a href=\\\"/artistas/\"").append(album.artista().id()).append("\">")
                .append(Html.esc(album.artista().nombre())).append("</a></p>");
        html.append("<ol>");
        for (PistaResumen pista : album.pistas()) {
            html.append("<li><a href=\"/pista/").append(pista.nombre()).append("\">")
                    .append(Html.esc(pista.duracion())).append("</a></li>");
        }
        html.append("</ol>");
        return html.toString();
    }
}
