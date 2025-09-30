package com.example.projetoEurofarma.controller;

import com.example.projetoEurofarma.model.Curso;
import com.example.projetoEurofarma.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // LISTAR TODOS: GET /api/cursos
    @GetMapping
    public ResponseEntity<List<Curso>> listarTodos() {
        List<Curso> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    // CADASTRAR/ATUALIZAR: POST /api/cursos
    @PostMapping
    public ResponseEntity<Curso> salvarCurso(@RequestBody Curso curso) {
        // Idealmente, esta rota seria restrita a administradores (futura implementação de roles)
        Curso novoCurso = cursoService.salvar(curso);
        return new ResponseEntity<>(novoCurso, HttpStatus.CREATED);
    }

    // BUSCAR POR ID: GET /api/cursos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}