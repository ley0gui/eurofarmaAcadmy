package com.example.projetoEurofarma.controller;

import com.example.projetoEurofarma.dto.LoginRequest;
import com.example.projetoEurofarma.dto.LoginResponse;
import com.example.projetoEurofarma.dto.UsuarioCadastroDTO;
import com.example.projetoEurofarma.model.Usuario;
import com.example.projetoEurofarma.service.JwtService;
import com.example.projetoEurofarma.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UsuarioController(UsuarioService usuarioService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // Endpoint de Cadastro: POST /api/usuarios/cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@RequestBody UsuarioCadastroDTO dto) {
        try {
            Usuario novoUsuario = usuarioService.cadastrarUsuario(dto);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint de Login: POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Autentica o usuário e verifica a senha (Spring Security)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(request.getEmail());
                Usuario usuario = usuarioService.buscarPorEmail(request.getEmail());

                LoginResponse response = new LoginResponse();
                response.setToken(token);
                response.setEmail(usuario.getEmail());
                response.setId(usuario.getId());
                response.setNome(usuario.getNome());
                response.setPontos(usuario.getPontos());

                return ResponseEntity.ok(response);
            } else {
                throw new UsernameNotFoundException("Credenciais inválidas.");
            }
        } catch (Exception e) {
            return new ResponseEntity("Email ou senha incorretos.", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint de Ranking: GET /api/usuarios/ranking
    @GetMapping("/ranking")
    public ResponseEntity<List<Usuario>> getRanking() {
        List<Usuario> ranking = usuarioService.getRanking();
        return ResponseEntity.ok(ranking);
    }
}