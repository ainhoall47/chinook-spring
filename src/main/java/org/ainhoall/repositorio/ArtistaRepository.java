package org.ainhoall.repositorio;

import org.ainhoall.modelo.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {
    List<Artista> findByNombreContainingIgnoreCaseOrderByNombre(String texto);

    @Query("select a from Artista a where lower(a.nombre) like lower(concat('%', :texto, '%')) order by a.nombre")
    List<Artista> buscarConJpql(@Param("texto") String texto);

    @Query(value = "SELECT ar.Name, SUM(il.Quantity) AS Unidades "
            + "FROM InvoiceLine il "
            + "JOIN Track t ON t.TrackId = il.TrackId "
            + "JOIN Album al ON al.AlbumId = t.AlbumId "
            + "JOIN Artist ar ON ar.ArtistId = al.ArtistId "
            + "GROUP BY ar.ArtistId, ar.Name "
            + "ORDER BY Unidades DESC, ar.Name "
            + "LIMIT :n", nativeQuery = true)
    List<Object[]> masVendidos(@Param("n") int n);
}
