package com.example.projetoEurofarma.controller;

import com.example.projetoEurofarma.model.Inscricao;
import com.example.projetoEurofarma.service.InscricaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    // 1. INSCREVER-SE EM UM CURSO: POST /api/inscricoes/{usuarioId}/{cursoId}
    @PostMapping("/{usuarioId}/{cursoId}")
    public ResponseEntity<Inscricao> inscreverUsuario(@PathVariable Long usuarioId, @PathVariable Long cursoId) {
        try {
            Inscricao novaInscricao = inscricaoService.inscreverUsuario(usuarioId, cursoId);
            return new ResponseEntity<>(novaInscricao, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2. CONCLUIR CURSO E GANHAR PONTOS: PUT /api/inscricoes/concluir/{inscricaoId}
    @PutMapping("/concluir/{inscricaoId}")
    public ResponseEntity<Inscricao> concluirCurso(@PathVariable Long inscricaoId) {
        try {
            // Este método, no Service, atualiza o status E adiciona os pontos ao Usuario!
            Inscricao inscricaoConcluida = inscricaoService.concluirCurso(inscricaoId);
            return ResponseEntity.ok(inscricaoConcluida);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 3. LISTAR INSCRIÇÕES DO USUÁRIO: GET /api/inscricoes/usuario/{usuarioId}
    //[cite_start]// Permite que o colaborador visualize seu progresso nos cursos[cite: 22].
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Inscricao>> listarInscricoesPorUsuario(@PathVariable Long usuarioId) {
        List<Inscricao> inscricoes = inscricaoService.listarInscricoesPorUsuario(usuarioId);
        return ResponseEntity.ok(inscricoes);
    }
}
