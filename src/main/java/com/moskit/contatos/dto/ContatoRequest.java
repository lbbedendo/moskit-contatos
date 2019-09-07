package com.moskit.contatos.dto;

import com.moskit.contatos.exception.ContatoException;
import com.moskit.contatos.model.Contato;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ContatoRequest {
    @NotEmpty(message = "O campo nome é obrigatório")
    private String nome;
    private String telefoneComercial;
    private String telefoneResidencial;
    private String telefoneCelular;
    private String emailComercial;
    private String emailPessoal;
    @NotNull(message = "O campo data de nascimento é obrigatório")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;
    @NotNull(message = "O campo favorito é obrigatório")
    private Boolean favorito;

    public Contato toContato() {
        Contato contato = new Contato();
        BeanUtils.copyProperties(this, contato);
        return contato;
    }

    public void validate() {
        if (ObjectUtils.isEmpty(telefoneComercial) && ObjectUtils.isEmpty(telefoneResidencial) && ObjectUtils.isEmpty(telefoneCelular)) {
            throw new ContatoException("Pelo menos 1 telefone é obrigatório");
        }

        if (ObjectUtils.isEmpty(emailComercial) && ObjectUtils.isEmpty(emailPessoal)) {
            throw new ContatoException("Pelo menos 1 e-mail é obrigatório");
        }
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
