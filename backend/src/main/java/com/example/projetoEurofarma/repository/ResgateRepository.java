package com.example.projetoEurofarma.repository;

import com.example.projetoEurofarma.model.Resgate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResgateRepository extends JpaRepository<Resgate, Long> {

    java.util.List<Resgate> findByUsuarioId(Long usuarioId);
}