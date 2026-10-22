package org.ainhoall.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Playlist")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistId")
    private Integer id;

    @Column(name = "Name")
    private String nombre;

    @ManyToMany
    @JoinTable(name = "PlaylistTrack",
            joinColumns = @JoinColumn(name = "PlaylistId"),
            inverseJoinColumns = @JoinColumn(name = "TrackId"))
    private Set<Pista> pistas = new HashSet<>();
}
