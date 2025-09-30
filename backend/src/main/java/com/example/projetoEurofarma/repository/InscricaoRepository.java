package com.example.projetoEurofarma.repository;

import com.example.projetoEurofarma.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    // Busca todas as inscrições de um usuário
    java.util.List<Inscricao> findByUsuarioId(Long usuarioId);
}