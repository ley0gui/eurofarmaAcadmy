package com.example.projetoEurofarma.service;

import com.example.projetoEurofarma.model.Recompensa;
import com.example.projetoEurofarma.model.Resgate;
import com.example.projetoEurofarma.model.Usuario;
import com.example.projetoEurofarma.repository.RecompensaRepository;
import com.example.projetoEurofarma.repository.ResgateRepository;
import com.example.projetoEurofarma.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecompensaService {

    private final RecompensaRepository recompensaRepository;
    private final ResgateRepository resgateRepository;
    private final UsuarioRepository usuarioRepository;

    public RecompensaService(RecompensaRepository recompensaRepository, ResgateRepository resgateRepository, UsuarioRepository usuarioRepository) {
        this.recompensaRepository = recompensaRepository;
        this.resgateRepository = resgateRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Método que implementa a lógica de resgate e débito de pontos
    @Transactional // Garante que todas as operações (saldo, estoque, registro) sejam atômicas.
    public Resgate resgatar(Long usuarioId, Long recompensaId) {
        // 1. Encontrar o Usuário
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // 2. Encontrar a Recompensa
        Recompensa recompensa = recompensaRepository.findById(recompensaId)
                .orElseThrow(() -> new RuntimeException("Recompensa não encontrada."));

        int custo = recompensa.getCustoPontos();

        // 3. Validação de Saldo
        if (usuario.getPontos() < custo) {
            throw new RuntimeException("Saldo de pontos insuficiente. Necessário: " + custo + ", Saldo atual: " + usuario.getPontos());
        }

        // 4. Validação de Estoque
        if (recompensa.getEstoque() <= 0) {
            throw new RuntimeException("Estoque da recompensa esgotado.");
        }

        // --- Operações Transacionais ---

        // A. Dedução de Pontos no Saldo do Usuário
        usuario.setPontos(usuario.getPontos() - custo);
        usuarioRepository.save(usuario);

        // B. Decremento do Estoque da Recompensa
        recompensa.setEstoque(recompensa.getEstoque() - 1);
        recompensaRepository.save(recompensa);

        // C. Registro do Resgate (Histórico)
        Resgate novoResgate = new Resgate();
        novoResgate.setUsuario(usuario);
        novoResgate.setRecompensa(recompensa);
        novoResgate.setPontosDebitados(custo);

        return resgateRepository.save(novoResgate);
    }

    // Método para listar todas as recompensas disponíveis (Catálogo)
    public List<Recompensa> listarTodas() {
        return recompensaRepository.findAll();
    }

    // Método para listar o histórico de resgates de um usuário
    public List<Resgate> listarResgatesPorUsuario(Long usuarioId) {
        return resgateRepository.findByUsuarioId(usuarioId);
    }
}