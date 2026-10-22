package org.ainhoall.repositorio;

import org.ainhoall.modelo.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
