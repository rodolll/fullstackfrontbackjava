package com.movies.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.apirest.Entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
