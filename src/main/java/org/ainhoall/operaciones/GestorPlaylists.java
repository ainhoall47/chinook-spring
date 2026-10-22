package org.ainhoall.operaciones;

import lombok.RequiredArgsConstructor;
import org.ainhoall.modelo.Pista;
import org.ainhoall.modelo.Playlist;
import org.ainhoall.repositorio.PistaRepository;
import org.ainhoall.repositorio.PlaylistRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GestorPlaylists {
    private final PlaylistRepository playlistRepository;
    private final PistaRepository pistaRepository;

    @Transactional
    public Playlist crearConPistas(String nombre, List<Integer> idsPistas) {
        Playlist playlist = new Playlist();
        playlist.setNombre(nombre);
        playlist = playlistRepository.save(playlist);

        for (Integer id : idsPistas) {
            Optional<Pista> pista = pistaRepository.findById(id);
            if (pista.isEmpty()) {
                throw new IllegalArgumentException("No existe la pista " + id);
            }
            playlist.getPistas().add(pista.get());
        }
        return playlistRepository.save(playlist);
    }

    @Transactional
    public boolean anadirPista(Integer playlistId, Integer pistaId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (playlist.isEmpty()) {
            throw new IllegalArgumentException("No existe la playlist " + playlistId);
        }
        Optional<Pista> pista = pistaRepository.findById(pistaId);
        if (pista.isEmpty()) {
            throw new IllegalArgumentException("No existe la pista " + pistaId);
        }
        return playlist.get().getPistas().add(pista.get());
    }
}
