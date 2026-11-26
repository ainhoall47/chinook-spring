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

    /**
     * Muestra la ficha de los albumes de cada artista.
     *
     * @param id identificador del album, tomado de la ruta
     * @return la página de la ficha
     * @throws org.ainhoall.servicio.NoEncontradoException si el album no existe (la traduce a 404 ManejadorErrores)
     */
    // GET /albumes/{id}
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        AlbumDetalle album = albumService.buscarPorId(id);
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("<p>De <a href=\"/artistas/").append(album.artista().id()).append("\">")
                .append(Html.esc(album.artista().nombre())).append("</a></p>");
        cuerpo.append("<table border=\"1\"><tr><th>#</th><th>Pista</th><th>Duración</th></tr>");
        int numero = 1;
        for (PistaResumen pista : album.pistas()) {
            cuerpo.append("<tr><td>").append(numero).append("</td><td>")
                    .append(Html.esc(pista.nombre())).append("</td><td>")
                    .append(pista.duracion()).append("</td></tr>");
            numero++;
        }
        cuerpo.append("</table>");
        return Html.pagina(album.titulo(), cuerpo.toString());
    }
}
