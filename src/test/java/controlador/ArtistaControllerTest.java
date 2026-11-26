package controlador;

import org.ainhoall.controlador.ArtistaController;
import org.ainhoall.controlador.Html;
import org.ainhoall.dto.AlbumResumen;
import org.ainhoall.dto.ArtistaDetalle;
import org.ainhoall.servicio.ArtistaService;
import org.ainhoall.servicio.NoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistaController.class)
public class ArtistaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArtistaService artistaService;

    @Test
    void fichaMuestraElNombreYLosAlbumes() throws Exception {
        ArtistaDetalle acdc = new ArtistaDetalle(1, "AC/DC", List.of(new AlbumResumen(4, "Let There Be Rock")));
        when(artistaService.buscarPorId(1)).thenReturn(acdc);

        mockMvc.perform(get("/artistas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>AC/DC</h1>")))
                .andExpect(content().string(containsString("href=\"/albumes/4\"")));
    }

    @Test
    void fichaDeArtistaInexistenteDevuelve404() throws Exception {
        when(artistaService.buscarPorId(9999)).thenThrow(new NoEncontradoException("No existe el artista 9999"));

        mockMvc.perform(get("/artistas/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No existe el artista 9999")));
    }

    @Test
    void idQueNoEsUnNumeroDevuelve400() throws Exception {
        mockMvc.perform(get("/artistas/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void elBuscadorEscapaLoQueEscribeElUsuario() throws Exception {
        when(artistaService.buscarPorNombre("<b>hola")).thenReturn(List.of());

        // Si no se escapara, la respuesta contendría <b>hola y el navegador lo pondría en negrita
        mockMvc.perform(get("/artistas/buscar").param("texto", "<b>hola"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("«&lt;b&gt;hola»")));
    }

    @Test
    void unNombreConApostrofoSeEscapa() throws Exception {
        ArtistaDetalle guns = new ArtistaDetalle(88, "Guns N' Roses", List.of());
        // TODO 1: haz que el servicio simulado devuelva guns al pedir el artista 88
        when(artistaService.buscarPorId(88)).thenReturn(guns);
        // TODO 2: pide /artistas/88 y comprueba que el estado es 200
        //         y que el HTML contiene el nombre con el apóstrofo escapado (&#39;)
        mockMvc.perform(get("/artistas/88"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Guns N&#39; Roses")));
    }

    @Test
    void unaRutaGetPedidaConPostDevuelve405() throws Exception {
        // TODO 3: haz una petición POST a /artistas/1 y comprueba que el estado es 405 (isMethodNotAllowed)
        mockMvc.perform(post("/artistas/1"))
                .andExpect(status().isMethodNotAllowed());
    }
}
