package org.ainhoall.dto;

import java.util.List;

public record ArtistaDetalle(Integer id, String nombre, List<AlbumResumen> albumes) {
}
