package org.ainhoall.repositorio;

import org.ainhoall.modelo.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

}
