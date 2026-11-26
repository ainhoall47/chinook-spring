package servicio;

import org.ainhoall.dto.ArtistaDetalle;
import org.ainhoall.modelo.Album;
import org.ainhoall.modelo.Artista;
import org.ainhoall.repositorio.AlbumRepository;
import org.ainhoall.repositorio.ArtistaRepository;
import org.ainhoall.servicio.ArtistaServiceImpl;
import org.ainhoall.servicio.NoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

// Prueba unitaria del servicio: los repositorios son simulados (mocks), no hay base de datos
@ExtendWith(MockitoExtension.class)
public class ArtistaServiceImplTest {
    @Mock
    private ArtistaRepository artistaRepository;

    @Mock
    private AlbumRepository albumRepository;

    // Mockito crea el servicio real y le pasa los dos repositorios simulados por el constructor
    @InjectMocks
    private ArtistaServiceImpl artistaService;

    @Test
    void buscarPorIdDevuelveLaFichaConSusAlbumes() {
        // Preparar: lo que devolverán los repositorios simulados
        Artista acdc = new Artista();
        acdc.setId(1);
        acdc.setNombre("AC/DC");
        Album album = new Album(4, "Let There Be Rock", acdc);
        when(artistaRepository.findById(1)).thenReturn(Optional.of(acdc));
        when(albumRepository.findByArtistaIdOrderByTitulo(1)).thenReturn(List.of(album));

        // Ejecutar
        ArtistaDetalle ficha = artistaService.buscarPorId(1);

        // Comprobar
        assertEquals("AC/DC", ficha.nombre());
        assertEquals(1, ficha.albumes().size());
        assertEquals("Let There Be Rock", ficha.albumes().get(0).titulo());
    }

    @Test
    void buscarPorIdLanzaNoEncontradoSiNoExiste() {
        when(artistaRepository.findById(9999)).thenReturn(Optional.empty());

        try {
            artistaService.buscarPorId(9999);
            fail("Tendría que haber lanzado NoEncontradoException");
        } catch (NoEncontradoException e) {
            assertEquals("No existe el artista 9999", e.getMessage());
        }
    }
}
