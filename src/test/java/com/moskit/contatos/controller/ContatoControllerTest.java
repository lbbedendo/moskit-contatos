package com.moskit.contatos.controller;

import com.moskit.contatos.repository.ContatoRepository;
import com.moskit.contatos.service.ContatoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class ContatoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ContatoService contatoService;
    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private ContatoController contatoController;

    @Test
    public void save_isOk_quandoSalvarUmContatoValido() throws Exception {
        String contato = "{\n" +
                "\t\"nome\": \"João da Silva\",\n" +
                "\t\"telefoneComercial\": \"4399861932\",\n" +
                "\t\"emailPessoal\": \"joaosilva@gmail.com\",\n" +
                "\t\"dataNascimento\": \"1989-05-25\",\n" +
                "\t\"favorito\": true\n" +
                "}";
        mockMvc.perform(post("/contato")
                .content(contato)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}