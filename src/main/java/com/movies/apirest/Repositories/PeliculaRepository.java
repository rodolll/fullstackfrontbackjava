package com.movies.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.apirest.Entities.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    
}
