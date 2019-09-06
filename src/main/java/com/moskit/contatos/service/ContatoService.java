package com.moskit.contatos.service;

import com.moskit.contatos.dto.ContatoFavoritoPatchRequest;
import com.moskit.contatos.dto.ContatoRequest;
import com.moskit.contatos.model.Contato;
import com.moskit.contatos.repository.ContatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {
    private ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public Contato save(ContatoRequest contatoRequest) {
        return contatoRepository.save(contatoRequest.toContato());
    }

    public Contato save(Integer id, ContatoRequest contatoRequest) {
        Contato contato = contatoRequest.toContato();
        contato.setId(id);
        return contatoRepository.save(contato);
    }

    public void updateFieldFavorito(Integer id, ContatoFavoritoPatchRequest contatoFavoritoPatchRequest) {
        contatoRepository.findById(id)
            .ifPresent(contato -> {
                contato.setFavorito(contatoFavoritoPatchRequest.getFavorito());
                contatoRepository.save(contato);
            });
    }

    public List<Contato> findAll() {
        return contatoRepository.findAll();
    }

    public Optional<Contato> findById(Integer id) {
        return contatoRepository.findById(id);
    }

    public void deleteById(Integer id) {
        contatoRepository.deleteById(id);
    }
}
