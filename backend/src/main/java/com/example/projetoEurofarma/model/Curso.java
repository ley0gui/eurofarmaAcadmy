package com.example.projetoEurofarma.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cursos")
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "pontos_premio", nullable = false)
    private int pontosPremio;

    @Column(name = "link_conteudo")
    private String linkConteudo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPontosPremio() {
        return pontosPremio;
    }

    public void setPontosPremio(int pontosPremio) {
        this.pontosPremio = pontosPremio;
    }

    public String getLinkConteudo() {
        return linkConteudo;
    }

    public void setLinkConteudo(String linkConteudo) {
        this.linkConteudo = linkConteudo;
    }
}