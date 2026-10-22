package org.ainhoall.repositorio;

import org.ainhoall.modelo.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    @Query("select p.id, p.nombre, count(t) from Playlist p left join p.pistas t "
            + "group by p.id, p.nombre order by p.id")
    List<Object[]> contarPistas();
}
