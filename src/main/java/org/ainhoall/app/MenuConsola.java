package org.ainhoall.app;

import lombok.RequiredArgsConstructor;
import org.ainhoall.modelo.Album;
import org.ainhoall.modelo.Artista;
import org.ainhoall.modelo.Genero;
import org.ainhoall.modelo.Pista;
import org.ainhoall.repositorio.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Optional;

import java.util.Scanner;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuConsola implements CommandLineRunner {

    private final Scanner teclado = new Scanner(System.in);
    private final GeneroRepository generoRepository;

    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    private final PistaRepository pistaRepository;
    private final PlaylistRepository playlistRepository;

    @Override
    public void run(String... args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");
            try {
                switch (opcion) {
                    case 1 -> listarGeneros();
                    case 2 -> contarPistasPorGenero();
                    case 3 -> buscarArtistas();
                    case 4 -> artistasMasVendidos();
                    case 5 -> albumesDeArtista();
                    case 6 -> pistasDeGenero();
                    case 7 -> pistasDeAlbum();
                    case 8 -> insertarAlbum();
                    case 9 -> cambiarTituloAlbum();
                    case 10 -> borrarAlbum();
                    case 11 -> contarPistasPorPlaylist();
                    case 0 -> System.out.println("Hasta pronto.");
                    default -> System.out.println("Esa opción no existe.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("========= CHINOOK =========");
        System.out.println(" 1. Listar géneros");
        System.out.println(" 2. Géneros con su número de pistas");
        System.out.println(" 3. Buscar artistas");
        System.out.println(" 4. Artistas más vendidos");
        System.out.println(" 5. Álbumes de un artista");
        System.out.println(" 6. Pistas de un género");
        System.out.println(" 7. Pistas de un álbum");
        System.out.println(" 8. Añadir un álbum");
        System.out.println(" 9. Cambiar el título de un álbum");
        System.out.println("10. Borrar un álbum");
        System.out.println("11. Playlists con su número de pistas");
        System.out.println("12. Añadir una pista a una playlist");
        System.out.println("13. Títulos de los álbumes de un artista (colección LAZY)");
        System.out.println(" 0. Salir");
        System.out.println("===========================");
    }

    // ---------- Opciones ----------

    private void listarGeneros() {
        for (Genero genero : generoRepository.findAllByOrderByNombre()) {
            System.out.println(genero);
        }
    }

    private void insertarAlbum() {
        String titulo = leerTexto("Título del álbum: ");
        int artistaId = leerEntero("Id del artista: ");
        // TODO 1: busca el artista con artistaRepository.findById(...). Devuelve un Optional<Artista>
        Optional<Artista> artista = artistaRepository.findById(artistaId);
        // TODO 2: si la caja está vacía (isEmpty()), lanza
        //         new IllegalArgumentException("No existe el artista " + artistaId)
        if(artista.isEmpty()){
            throw new IllegalArgumentException("No existe el artista " + artistaId);
        }
        // TODO 3: guarda un álbum nuevo: albumRepository.save(new Album(null, titulo, artista.get())).
        //         get() saca el artista de la caja. save devuelve el álbum con su id ya asignado: guárdalo
        Album album = albumRepository.save(new Album(null, titulo, artista.get()));

        System.out.println("Álbum guardado con el id " + album.getId() + ".");
    }

    private void cambiarTituloAlbum() {
        int albumId = leerEntero("Id del álbum: ");
        String titulo = leerTexto("Nuevo título: ");
        // TODO 1: busca el álbum con albumRepository.findById(...). Devuelve un Optional<Album>
        Optional<Album> encontrado = albumRepository.findById(albumId);
        // TODO 2: si la caja está vacía, lanza IllegalArgumentException("No existe el álbum " + albumId)
        if(encontrado.isEmpty()){
            throw new IllegalArgumentException("No existe el álbum " + albumId);
        }
        // TODO 3: saca el álbum con get() y cambia su título con setTitulo(...)
        Album album = encontrado.get();
        album.setTitulo(titulo);
        // TODO 4: guárdalo con albumRepository.save(...). Como ya tiene id, será un UPDATE
        albumRepository.save(album);
        System.out.println("Álbum actualizado.");
    }

    private void borrarAlbum() {
        int albumId = leerEntero("Id del álbum que quieres borrar: ");
        if (!albumRepository.existsById(albumId)) {
            throw new IllegalArgumentException("No existe el álbum " + albumId);
        }
        // Si tiene pistas, la clave foránea impediría borrarlo: lo comprobamos antes
        if (pistaRepository.countByAlbumId(albumId) > 0) {
            throw new IllegalArgumentException("El álbum tiene pistas y no se puede borrar.");
        }
        albumRepository.deleteById(albumId);
        System.out.println("Álbum borrado.");
    }

    private void contarPistasPorGenero() {
        // Cada fila es un Object[] {nombre, número de pistas}
        for (Object[] fila : generoRepository.contarPistas()) {
            String nombre = (String) fila[0];
            Long pistas = (Long) fila[1];
            System.out.println(nombre + ": " + pistas);
        }
    }

    private void buscarArtistas() {
        String texto = leerTexto("Texto a buscar: ");
        List<Artista> artistas = artistaRepository.findByNombreContainingIgnoreCaseOrderByNombre(texto);
        if (artistas.isEmpty()) {
            System.out.println("Ningún artista contiene \"" + texto + "\".");
        }
        for (Artista artista : artistas) {
            System.out.println(artista);
        }
    }

    private void artistasMasVendidos() {
        int n = leerEntero("¿Cuántos artistas quieres ver? ");
        // La consulta es nativa: SUM puede llegar como BigDecimal o Long, por eso se lee como Number
        for (Object[] fila : artistaRepository.masVendidos(n)) {
            System.out.println(fila[0] + ": " + ((Number) fila[1]).longValue() + " unidades");
        }
    }

    private void albumesDeArtista() {
        int artistaId = leerEntero("Id del artista: ");
        List<Album> albumes = albumRepository.findByArtistaIdOrderByTitulo(artistaId);
        if (albumes.isEmpty()) {
            System.out.println("Ese artista no tiene álbumes.");
        }
        for (Album album : albumes) {
            System.out.println(album.getId() + " - " + album.getTitulo());
        }
    }

    private void pistasDeGenero() {
        int generoId = leerEntero("Id del género: ");
        System.out.println(pistaRepository.countByGeneroId(generoId) + " pistas. Importe total: "
                + pistaRepository.sumarPrecios(generoId).orElse(BigDecimal.ZERO) + " €. Las 20 primeras:");
        for (Pista pista : pistaRepository.findTop20ByGeneroIdOrderByNombre(generoId)) {
            mostrarPista(pista);
        }
    }

    private void pistasDeAlbum() {
        int albumId = leerEntero("Id del álbum: ");
        List<Pista> pistas = pistaRepository.findByAlbumIdOrderById(albumId);
        if (pistas.isEmpty()) {
            System.out.println("Ese álbum no tiene pistas.");
        }
        for (Pista pista : pistas) {
            mostrarPista(pista);
        }
    }

    private void contarPistasPorPlaylist() {
        // Cada fila: {id, nombre, número de pistas}
        for (Object[] fila : playlistRepository.contarPistas()) {
            System.out.println(fila[0] + " - " + fila[1] + ": " + fila[2] + " pistas");
        }
    }

    private void mostrarPista(Pista pista) {
        System.out.println(pista.getNombre() + " | " + pista.getDuracion() + " | " + pista.getPrecioUnitario() + " €");
    }

    // ---------- Utilidades ----------

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero.");
            }
        }
    }
/*
private void pruebaCrud(){
    System.out.println("--- Paso 4: CRUD ---");

    Genero nuevo = generoRepository.save(new Genero(null, "Género de prueba"));
    System.out.println("Insertado: " + nuevo);

    nuevo.setNombre("Género cambiado");
    generoRepository.save(nuevo);

    Optional<Genero> leido = generoRepository.findById(nuevo.getId());
    if (leido.isPresent()) {
        System.out.println("Leído: " + leido.get());
    }

    Genero inexistente = generoRepository.findById(9999).orElse(null);
    if (inexistente == null) {
        System.out.println("Género 9999: no existe");
    }

    Optional<Genero> otro = generoRepository.findById(9999);
    try {
        if (otro.isEmpty()) {
            throw new IllegalArgumentException("No existe el género 9999");
        }
    } catch (IllegalArgumentException e) {
        System.out.println("Error controlado: " + e.getMessage());
    }

    generoRepository.deleteById(nuevo.getId());
    System.out.println("Géneros en total: " + generoRepository.count());
}
*/
/*
    private void pruebaEntidades() {
        System.out.println("--- Paso 3: comprobar las entidades ---");

        Album album = albumRepository.findById(1).get();
        System.out.println("Álbum: " + album.getTitulo() + " de " + album.getArtista().getNombre());

        Pista pista = pistaRepository.findById(1).get();
        System.out.println("Pista: " + pista.getNombre() + " | " + pista.getDuracion() + " | " + pista.getGenero().getNombre());

        System.out.println("Playlists: " + playlistRepository.count());
    }
*/
/*
    private void pruebaRecursion() {
        Artista artista = new Artista();
        artista.setNombre("Banda de prueba");

        Album album = new Album();
        album.setTitulo("Disco de prueba");
        album.setArtista(artista);

        artista.getAlbumes().add(album);

        System.out.println(artista);
    }
*/
/*
    private void pruebaLazy() {
        Artista artista = artistaRepository.findById(1).get();
        System.out.println("Artista: " + artista.getNombre());
        System.out.println("Álbumes: " + artista.getAlbumes().size());
    }
*/
}
