package org.ainhoall.servicio;

import org.ainhoall.dto.ArtistaDetalle;
import org.ainhoall.dto.ArtistaResumen;
import org.ainhoall.modelo.Artista;

import java.util.List;

public interface ArtistaService {
    List<ArtistaResumen> buscarPorNombre(String texto);

    ArtistaDetalle buscarPorId(Integer id);
}
