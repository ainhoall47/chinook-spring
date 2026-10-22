package org.ainhoall.repositorio;

import org.ainhoall.modelo.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {
}
