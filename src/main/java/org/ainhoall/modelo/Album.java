package org.ainhoall.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Album")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Integer id;

    @Column(name = "Title")
    private String titulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ArtistId")
    private Artista artista;
}

