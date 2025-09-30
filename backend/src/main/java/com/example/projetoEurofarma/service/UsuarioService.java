package com.example.projetoEurofarma.service;

import com.example.projetoEurofarma.dto.UsuarioCadastroDTO;
import com.example.projetoEurofarma.model.Usuario;
import com.example.projetoEurofarma.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(UsuarioCadastroDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmail(dto.getEmail());

        // CRIPTOGRAFIA: Criptografa a senha antes de salvar
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(novoUsuario);
    }

    public List<Usuario> getRanking() {
        return usuarioRepository.findAllByOrderByPontosDesc();
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}