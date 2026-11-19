package org.ainhoall.servicio;

import org.ainhoall.dto.AlbumResumen;
import org.ainhoall.dto.ArtistaDetalle;
import org.ainhoall.dto.ArtistaResumen;
import org.ainhoall.modelo.Album;
import org.ainhoall.modelo.Artista;
import org.ainhoall.repositorio.AlbumRepository;
import org.ainhoall.repositorio.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistaServiceImpl implements ArtistaService{
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    public ArtistaServiceImpl(ArtistaRepository artistaRepository, AlbumRepository albumRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistaResumen> buscarPorNombre(String texto) {
        List<ArtistaResumen> resultado = new ArrayList<>();
        for (Artista artista : artistaRepository.findByNombreContainingIgnoreCaseOrderByNombre(texto)) {
            resultado.add(new ArtistaResumen(artista.getId(), artista.getNombre()));
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistaDetalle buscarPorId(Integer id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isEmpty()) {
            throw new NoEncontradoException("No existe el artista " + id);
        }
        List<AlbumResumen> albumes = new ArrayList<>();
        for (Album album : albumRepository.findByArtistaIdOrderByTitulo(id)) {
            albumes.add(new AlbumResumen(album.getId(), album.getTitulo()));
        }
        return new ArtistaDetalle(artista.get().getId(), artista.get().getNombre(), albumes);
    }
}
