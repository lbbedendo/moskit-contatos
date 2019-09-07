package com.moskit.contatos.dto;

import javax.validation.constraints.NotNull;

public class ContatoFavoritoPatchRequest {
    @NotNull(message = "O campo favorito é obrigatório")
    private Boolean favorito;

    public ContatoFavoritoPatchRequest(Boolean favorito) {
        this.favorito = favorito;
    }

    public ContatoFavoritoPatchRequest() {}

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }
}
