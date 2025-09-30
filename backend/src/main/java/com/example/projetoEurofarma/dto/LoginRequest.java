package com.example.projetoEurofarma.dto;

import lombok.Data;

@Data // Gera Getters/Setters
public class LoginRequest {
    private String email; // O email é o "username" no Spring Security
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
