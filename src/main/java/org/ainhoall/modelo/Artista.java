package org.ainhoall.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Artist")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private Integer id;

    @Column(name = "Name")
    private String nombre;

    @OneToMany(mappedBy = "artista")
    private Set<Album> albumes = new HashSet<>();
}
