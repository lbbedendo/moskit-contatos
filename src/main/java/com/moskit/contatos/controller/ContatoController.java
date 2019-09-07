package com.moskit.contatos.controller;

import com.moskit.contatos.dto.ContatoFavoritoPatchRequest;
import com.moskit.contatos.dto.ContatoRequest;
import com.moskit.contatos.model.Contato;
import com.moskit.contatos.service.ContatoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("contato")
public class ContatoController {
    private ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @PostMapping
    public HttpEntity<Contato> save(@RequestBody @Valid ContatoRequest contatoRequest) {
        return new ResponseEntity<>(contatoService.save(contatoRequest), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public HttpEntity<Contato> update(@PathVariable Integer id,
                                      @RequestBody @Valid ContatoRequest contatoRequest) {
        return ResponseEntity.ok(contatoService.save(id, contatoRequest));
    }

    @PatchMapping("{id}")
    public HttpEntity<Contato> updateFieldFavorito(@PathVariable Integer id,
                                                   @RequestBody @Valid ContatoFavoritoPatchRequest contatoFavoritoPatchRequest) {
        contatoService.updateFieldFavorito(id, contatoFavoritoPatchRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public HttpEntity<List<Contato>> findAll() {
        return ResponseEntity.ok(contatoService.findAll());
    }

    @GetMapping("{id}")
    public HttpEntity<Contato> findById(@PathVariable Integer id) {
        return contatoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        contatoService.deleteById(id);
    }
}
