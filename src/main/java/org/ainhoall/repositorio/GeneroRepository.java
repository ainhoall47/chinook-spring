package org.ainhoall.repositorio;

import org.ainhoall.modelo.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    List<Genero> findAllByOrderByNombre();

    @Query("select g.nombre, count(p) from Genero g left join Pista p on p.genero = g "
            + "group by g.id, g.nombre order by g.nombre")
    List<Object[]> contarPistas();
}
