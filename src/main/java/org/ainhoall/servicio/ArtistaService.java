package org.ainhoall.servicio;

import org.ainhoall.modelo.Artista;

import java.util.List;

public interface ArtistaService {
    List<Artista> buscarPorNombre(String texto);

    Artista buscarPorId(Integer id);
}
