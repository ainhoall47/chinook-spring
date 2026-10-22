package org.ainhoall.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok escribe por nosotros, al compilar, los getters, los setters, los constructores y toString().
@Entity
@Table(name = "Genre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreId")
    private Integer id;

    @Column(name = "Name")
    private String nombre;
}