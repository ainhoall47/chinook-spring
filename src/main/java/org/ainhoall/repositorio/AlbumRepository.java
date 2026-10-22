package org.ainhoall.repositorio;

import org.ainhoall.modelo.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByArtistaIdOrderByTitulo(Integer artistaId);
}
