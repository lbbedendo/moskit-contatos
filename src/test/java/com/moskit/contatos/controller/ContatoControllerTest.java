package com.moskit.contatos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moskit.contatos.dto.ContatoFavoritoPatchRequest;
import com.moskit.contatos.dto.ContatoRequest;
import com.moskit.contatos.repository.ContatoRepository;
import com.moskit.contatos.service.ContatoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "classpath:scripts/tests_contato_controller.sql")
public class ContatoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ContatoService contatoService;
    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private ContatoController contatoController;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void save_isOk_quandoSalvarUmContatoValido() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Tony Stark");
        request.setTelefoneComercial("4399861932");
        request.setEmailComercial("tony@stark.com");
        request.setDataNascimento(LocalDate.of(1972, 5, 25));
        request.setFavorito(true);
        mockMvc.perform(post("/contato")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    public void save_isBadRequest_quandoTentarSalvarUmContatoSemNenhumTelefone() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Tony Stark");
        request.setEmailComercial("tony@stark.com");
        request.setDataNascimento(LocalDate.of(1972, 5, 25));
        request.setFavorito(true);
        mockMvc.perform(post("/contato")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Pelo menos 1 telefone é obrigatório")));
    }

    @Test
    public void save_isBadRequest_quandoTentarSalvarUmContatoSemNenhumEmail() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Tony Stark");
        request.setTelefoneComercial("4399861932");
        request.setDataNascimento(LocalDate.of(1972, 5, 25));
        request.setFavorito(true);
        mockMvc.perform(post("/contato")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Pelo menos 1 e-mail é obrigatório")));

    }

    @Test
    public void save_isBadRequest_quandoTentarSalvarUmContatoSemDataNascimento() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Tony Stark");
        request.setTelefoneComercial("4399861932");
        request.setEmailComercial("tony@stark.com");
        request.setFavorito(true);
        mockMvc.perform(post("/contato")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("O campo data de nascimento é obrigatório")));
    }

    @Test
    public void save_isBadRequest_quandoTentarSalvarUmContatoSemCampoFavorito() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Tony Stark");
        request.setTelefoneComercial("4399861932");
        request.setEmailComercial("tony@stark.com");
        request.setDataNascimento(LocalDate.of(1972, 5, 25));
        mockMvc.perform(post("/contato")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("O campo favorito é obrigatório")));
    }

    @Test
    public void findById_isOk_quandoEncontrarContatoPorId() throws Exception {
        mockMvc.perform(get("/contato/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Thor Odinson")))
                .andExpect(jsonPath("$.telefoneComercial", is("43987661123")))
                .andExpect(jsonPath("$.telefoneResidencial", nullValue()))
                .andExpect(jsonPath("$.telefoneCelular", nullValue()))
                .andExpect(jsonPath("$.emailPessoal", is("thor@gmail.com")))
                .andExpect(jsonPath("$.emailComercial", nullValue()))
                .andExpect(jsonPath("$.dataNascimento", is("1989-04-09")))
                .andExpect(jsonPath("$.favorito", is(true)));
    }

    @Test
    public void findById_isNotFound_quandoNaoEncontrarContatoPorId() throws Exception {
        mockMvc.perform(get("/contato/9999")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAll_isOk_quandoRequisitarListagemDeContatos() throws Exception {
        mockMvc.perform(get("/contato")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Thor Odinson")))
                .andExpect(jsonPath("$[0].telefoneComercial", is("43987661123")))
                .andExpect(jsonPath("$[0].telefoneResidencial", nullValue()))
                .andExpect(jsonPath("$[0].telefoneCelular", nullValue()))
                .andExpect(jsonPath("$[0].emailPessoal", is("thor@gmail.com")))
                .andExpect(jsonPath("$[0].emailComercial", nullValue()))
                .andExpect(jsonPath("$[0].dataNascimento", is("1989-04-09")))
                .andExpect(jsonPath("$[0].favorito", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Thanos")))
                .andExpect(jsonPath("$[1].telefoneComercial", is("4399871243")))
                .andExpect(jsonPath("$[1].telefoneResidencial", nullValue()))
                .andExpect(jsonPath("$[1].telefoneCelular", nullValue()))
                .andExpect(jsonPath("$[1].emailPessoal", is("thanos@gmail.com")))
                .andExpect(jsonPath("$[1].emailComercial", nullValue()))
                .andExpect(jsonPath("$[1].dataNascimento", is("1939-03-12")))
                .andExpect(jsonPath("$[1].favorito", is(false)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].nome", is("Robert C. Martin")))
                .andExpect(jsonPath("$[2].telefoneComercial", is("5599855843")))
                .andExpect(jsonPath("$[2].telefoneResidencial", nullValue()))
                .andExpect(jsonPath("$[2].telefoneCelular", is("5599177893")))
                .andExpect(jsonPath("$[2].emailPessoal", is("bobmartin@gmail.com")))
                .andExpect(jsonPath("$[2].emailComercial", is("bobmartin@cleancoders.com")))
                .andExpect(jsonPath("$[2].dataNascimento", is("1959-03-12")))
                .andExpect(jsonPath("$[2].favorito", is(false)));
    }

    @Test
    public void deleteById_isOK_quandoRemoverUmContatoPorId() throws Exception {
        mockMvc.perform(delete("/contato/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void update_isOk_quandoAtualizarUmContatoExistente() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Thor Son of Odin");
        request.setTelefoneComercial("43987661123");
        request.setEmailComercial("thor@asgard.com");
        request.setEmailPessoal("thor@gmail.com");
        request.setDataNascimento(LocalDate.of(1912, 8, 28));
        request.setFavorito(true);
        mockMvc.perform(put("/contato/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void update_isBadRequest_quandoTentarAtualizarUmContatoSemEnviarPeloMenosUmTelefone() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Thor Son of Odin");
        request.setEmailComercial("thor@asgard.com");
        request.setEmailPessoal("thor@gmail.com");
        request.setDataNascimento(LocalDate.of(1912, 8, 28));
        request.setFavorito(true);
        mockMvc.perform(put("/contato/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Pelo menos 1 telefone é obrigatório")));
    }

    @Test
    public void update_isBadRequest_quandoTentarAtualizarUmContatoSemEnviarPeloMenosUmEmail() throws Exception {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Thor Son of Odin");
        request.setTelefoneComercial("43987661123");
        request.setDataNascimento(LocalDate.of(1912, 8, 28));
        request.setFavorito(true);
        mockMvc.perform(put("/contato/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Pelo menos 1 e-mail é obrigatório")));
    }

    @Test
    public void updateFieldFavorito_isOk_quandoAtualizarSomenteOCampoFavoritoDoContato() throws Exception {
        ContatoFavoritoPatchRequest request = new ContatoFavoritoPatchRequest(true);
        mockMvc.perform(patch("/contato/3")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFieldFavorito_isBadRequest_quandoNaoEnviarOCampoFavorito() throws Exception {
        mockMvc.perform(patch("/contato/3")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("O campo favorito é obrigatório")));
    }
}