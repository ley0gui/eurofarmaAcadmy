package com.example.projetoEurofarma.service;

import com.example.projetoEurofarma.model.Curso;
import com.example.projetoEurofarma.model.Inscricao;
import com.example.projetoEurofarma.model.Usuario;
import com.example.projetoEurofarma.repository.InscricaoRepository;
import com.example.projetoEurofarma.repository.UsuarioRepository;
import com.example.projetoEurofarma.util.StatusInscricao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoService cursoService; // Reutiliza o serviço de Curso

    public InscricaoService(InscricaoRepository inscricaoRepository, UsuarioRepository usuarioRepository, CursoService cursoService) {
        this.inscricaoRepository = inscricaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoService = cursoService;
    }

    // 1. LÓGICA DE INSCRIÇÃO
    public Inscricao inscreverUsuario(Long usuarioId, Long cursoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Curso curso = cursoService.buscarPorId(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        // Regra de Negócio: Evitar reinscrição (simplificado)
        // Uma verificação mais robusta procuraria por um curso EM_PROGRESSO.

        Inscricao novaInscricao = new Inscricao();
        novaInscricao.setUsuario(usuario);
        novaInscricao.setCurso(curso);
        novaInscricao.setStatus(StatusInscricao.EM_PROGRESSO);

        return inscricaoRepository.save(novaInscricao);
    }

    // 2. LÓGICA DE CONCLUSÃO E PONTUAÇÃO
    public Inscricao concluirCurso(Long inscricaoId) {
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada."));

        // Verifica se já está concluído para evitar dupla pontuação
        if (inscricao.getStatus() == StatusInscricao.CONCLUIDO) {
            throw new RuntimeException("O curso já foi concluído e pontuado.");
        }

        // 1. Atualiza o status da inscrição
        inscricao.setStatus(StatusInscricao.CONCLUIDO);
        inscricaoRepository.save(inscricao);

        // 2. Atribui os pontos ao usuário (Gamificação)
        Usuario usuario = inscricao.getUsuario();
        int pontosGanhos = inscricao.getCurso().getPontosPremio();

        // Atualiza o saldo de pontos
        usuario.setPontos(usuario.getPontos() + pontosGanhos);
        usuarioRepository.save(usuario);

        return inscricao;
    }

    public List<Inscricao> listarInscricoesPorUsuario(Long usuarioId) {
        return inscricaoRepository.findByUsuarioId(usuarioId);
    }
}