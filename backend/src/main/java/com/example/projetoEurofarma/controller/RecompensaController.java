package com.example.projetoEurofarma.controller;

import com.example.projetoEurofarma.model.Recompensa;
import com.example.projetoEurofarma.model.Resgate;
import com.example.projetoEurofarma.service.RecompensaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recompensas")
public class RecompensaController {

    private final RecompensaService recompensaService;

    public RecompensaController(RecompensaService recompensaService) {
        this.recompensaService = recompensaService;
    }

    // 1. LISTAR RECOMPENSAS: GET /api/recompensas
    // Exibe o catálogo de prêmios.
    @GetMapping
    public ResponseEntity<List<Recompensa>> listarTodas() {
        return ResponseEntity.ok(recompensaService.listarTodas());
    }

    // 2. LISTAR HISTÓRICO DE RESGATES DO USUÁRIO: GET /api/recompensas/resgates/usuario/{usuarioId}
    // Permite ao colaborador visualizar seu histórico de trocas.
    @GetMapping("/resgates/usuario/{usuarioId}")
    public ResponseEntity<List<Resgate>> listarResgatesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(recompensaService.listarResgatesPorUsuario(usuarioId));
    }

    // 3. RESGATAR RECOMPENSA (AÇÃO PRINCIPAL): POST /api/recompensas/resgatar/{usuarioId}/{recompensaId}
    // Rota que debita os pontos do usuário.
    @PostMapping("/resgatar/{usuarioId}/{recompensaId}")
    public ResponseEntity<?> resgatar(
            @PathVariable Long usuarioId,
            @PathVariable Long recompensaId) {
        try {
            // Chama a lógica de negócio que verifica saldo, debita pontos e registra o resgate.
            Resgate resgate = recompensaService.resgatar(usuarioId, recompensaId);
            return new ResponseEntity<>(resgate, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Captura erros de saldo ou estoque esgotado, retornando BAD_REQUEST (400)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 4. (Opcional - Rota Admin) CADASTRAR NOVA RECOMPENSA: POST /api/recompensas/admin
    // Rota para a Equipe de Inovação adicionar novos prêmios (exigiria um Role 'ADMIN' para ser segura).
    @PostMapping("/admin")
    public ResponseEntity<Recompensa> cadastrarRecompensa(@RequestBody Recompensa recompensa) {
        // Esta rota é um placeholder. Para que funcione, você precisaria adicionar um método
        // 'salvar' simples ao seu RecompensaService que chame o RecompensaRepository.save(recompensa).
        return new ResponseEntity("Necessário implementar método salvar no service para esta rota Admin.", HttpStatus.NOT_IMPLEMENTED);
    }
}