package com.moskit.contatos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "contato", schema = "mskt")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "NOME")
    private String nome;

    @Column(name = "TELEFONE_COMERCIAL")
    private String telefoneComercial;

    @Column(name = "TELEFONE_RESIDENCIAL")
    private String telefoneResidencial;

    @Column(name = "TELEFONE_CELULAR")
    private String telefoneCelular;

    @Column(name = "EMAIL_COMERCIAL")
    private String emailComercial;

    @Column(name = "EMAIL_PESSOAL")
    private String emailPessoal;

    @Column(name = "DATA_NASCIMENTO")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNascimento;

    @Column(name = "FAVORITO")
    private Boolean favorito;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getEmailComercial() {
        return emailComercial;
    }

    public void setEmailComercial(String emailComercial) {
        this.emailComercial = emailComercial;
    }

    public String getEmailPessoal() {
        return emailPessoal;
    }

    public void setEmailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }
}
