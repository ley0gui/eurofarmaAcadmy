package com.example.projetoEurofarma.repository;

import com.example.projetoEurofarma.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Essencial para o login (busca por email)
    Usuario findByEmail(String email);

    // Essencial para o ranking (ordenado por pontos)
    java.util.List<Usuario> findAllByOrderByPontosDesc();
}