package com.example.projetoEurofarma.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Resgates")
@Data
public class Resgate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Relacionamento Muitos-para-Um: Muitos resgates para Um Usuário
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne // Relacionamento Muitos-para-Um: Muitos resgates para Uma Recompensa
    @JoinColumn(name = "id_recompensa", nullable = false)
    private Recompensa recompensa;

    @Column(name = "pontos_debitados", nullable = false)
    private int pontosDebitados;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_resgate")
    private Date dataResgate = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }

    public int getPontosDebitados() {
        return pontosDebitados;
    }

    public void setPontosDebitados(int pontosDebitados) {
        this.pontosDebitados = pontosDebitados;
    }

    public Date getDataResgate() {
        return dataResgate;
    }

    public void setDataResgate(Date dataResgate) {
        this.dataResgate = dataResgate;
    }
}