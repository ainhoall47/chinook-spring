package org.ainhoall.servicio;

import org.ainhoall.modelo.Genero;
import org.ainhoall.repositorio.GeneroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroServiceImpl implements GeneroService{
    private final GeneroRepository generoRepository;

    public GeneroServiceImpl(GeneroRepository generoRepository){
        this.generoRepository = generoRepository;
    }

    @Override
    public List<Genero> listar() {
        return generoRepository.findAllByOrderByNombre();
    }
}
