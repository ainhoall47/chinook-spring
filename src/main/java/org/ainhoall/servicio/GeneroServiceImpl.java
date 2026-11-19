package org.ainhoall.servicio;

import org.ainhoall.dto.GeneroResumen;
import org.ainhoall.modelo.Genero;
import org.ainhoall.repositorio.GeneroRepository;
import org.ainhoall.repositorio.PistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneroServiceImpl implements GeneroService{
    private final GeneroRepository generoRepository;
    private final PistaRepository pistaRepository;

    public GeneroServiceImpl(GeneroRepository generoRepository, PistaRepository pistaRepository){

        this.generoRepository = generoRepository;
        this.pistaRepository = pistaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeneroResumen> listar() {
        List<GeneroResumen> resultado = new ArrayList<>();
        for (Genero genero : generoRepository.findAllByOrderByNombre()) {
            long numero = pistaRepository.countByGeneroId(genero.getId());
            resultado.add(new GeneroResumen(genero.getId(), genero.getNombre(), numero));
        }
        return resultado;
    }
}
