package com.moskit.contatos.service;

import com.moskit.contatos.dto.ContatoFavoritoPatchRequest;
import com.moskit.contatos.dto.ContatoRequest;
import com.moskit.contatos.exception.ContatoException;
import com.moskit.contatos.model.Contato;
import com.moskit.contatos.repository.ContatoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "classpath:scripts/tests_contato_service.sql")
public class ContatoServiceTest {
    @Autowired
    private ContatoService contatoService;
    @Autowired
    private ContatoRepository contatoRepository;

    @Test
    public void save_deveRetornarContatoSalvo_quandoSalvarUmNovoContato() {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Clint Barton");
        request.setTelefoneComercial("4399861932");
        request.setEmailComercial("barton@avengers.com");
        request.setDataNascimento(LocalDate.of(1988, 1, 28));
        request.setFavorito(false);
        Contato contato = contatoService.save(request);
        assertThat(contato.getId()).isNotNull();
        assertThat(contato.getNome()).isEqualTo("Clint Barton");
        assertThat(contato.getTelefoneCelular()).isNull();
        assertThat(contato.getTelefoneComercial()).isEqualTo("4399861932");
        assertThat(contato.getTelefoneResidencial()).isNull();
        assertThat(contato.getDataNascimento()).isEqualTo(LocalDate.of(1988, 1, 28));
        assertThat(contato.getEmailComercial()).isEqualTo("barton@avengers.com");
        assertThat(contato.getEmailPessoal()).isNull();
        assertThat(contato.getFavorito()).isFalse();
    }

    @Test
    public void save_deveRetornarContatoAtualizado_quandoAtualizarUmContatoExistente() {
        Integer id = 3;
        ContatoRequest request = new ContatoRequest();
        request.setNome("Magneto");
        request.setTelefoneCelular("4399861932");
        request.setTelefoneComercial("4399319080");
        request.setEmailPessoal("eriklehnsherr@gmail.com");
        request.setEmailComercial("magneto@marvel.com");
        request.setDataNascimento(LocalDate.of(1960, 3, 14));
        request.setFavorito(false);
        Contato contato = contatoService.save(id, request);
        assertThat(contato.getId()).isEqualTo(3);
        assertThat(contato.getNome()).isEqualTo("Magneto");
        assertThat(contato.getTelefoneCelular()).isEqualTo("4399861932");
        assertThat(contato.getTelefoneComercial()).isEqualTo("4399319080");
        assertThat(contato.getTelefoneResidencial()).isNull();
        assertThat(contato.getDataNascimento()).isEqualTo(LocalDate.of(1960, 3, 14));
        assertThat(contato.getEmailComercial()).isEqualTo("magneto@marvel.com");
        assertThat(contato.getEmailPessoal()).isEqualTo("eriklehnsherr@gmail.com");
        assertThat(contato.getFavorito()).isFalse();
    }

    @Test
    public void save_ContatoException_quandoTentarSalvarUmContatoSemNenhumTelefone() {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Clint Barton");
        request.setEmailComercial("barton@avengers.com");
        request.setDataNascimento(LocalDate.of(1988, 1, 28));
        request.setFavorito(false);
        assertThatExceptionOfType(ContatoException.class)
                .isThrownBy(() -> contatoService.save(request))
                .withMessage("Pelo menos 1 telefone é obrigatório");
    }

    @Test
    public void save_ContatoException_quandoTentarSalvarUmContatoSemNenhumEmail() {
        ContatoRequest request = new ContatoRequest();
        request.setNome("Clint Barton");
        request.setTelefoneComercial("4399861932");
        request.setDataNascimento(LocalDate.of(1988, 1, 28));
        request.setFavorito(false);
        assertThatExceptionOfType(ContatoException.class)
                .isThrownBy(() -> contatoService.save(request))
                .withMessage("Pelo menos 1 e-mail é obrigatório");
    }

    @Test
    public void updateFieldFavorito_campoFavoritoAtualizado_quandoContatoExistir() {
        ContatoFavoritoPatchRequest patchRequest = new ContatoFavoritoPatchRequest(true);
        Integer id = 1;
        contatoService.updateFieldFavorito(id, patchRequest);
        assertThat(contatoService.findById(id))
                .hasValueSatisfying(contato -> {
                    assertThat(contato.getId()).isEqualTo(1);
                    assertThat(contato.getNome()).isEqualTo("Wanda Maximoff");
                    assertThat(contato.getTelefoneCelular()).isNull();
                    assertThat(contato.getTelefoneComercial()).isEqualTo("4399558877");
                    assertThat(contato.getTelefoneResidencial()).isNull();
                    assertThat(contato.getDataNascimento()).isEqualTo(LocalDate.of(1990, 4, 9));
                    assertThat(contato.getEmailComercial()).isEqualTo("wanda@avengers.com");
                    assertThat(contato.getEmailPessoal()).isNull();
                    assertThat(contato.getFavorito()).isTrue();
                });
    }

    @Test
    public void deleteById_contatoRemovido_quandoContatoExistir() {
        Integer id = 2;
        contatoService.deleteById(id);
        assertThat(contatoService.findById(id)).isEmpty();
    }

    @Test
    public void findById_contato_quandoContatoExistir() {
        assertThat(contatoService.findById(3))
                .hasValueSatisfying(contato -> {
                    assertThat(contato.getId()).isEqualTo(3);
                    assertThat(contato.getNome()).isEqualTo("Erik Lehnsherr");
                    assertThat(contato.getTelefoneCelular()).isEqualTo("4399121405");
                    assertThat(contato.getTelefoneComercial()).isNull();
                    assertThat(contato.getTelefoneResidencial()).isNull();
                    assertThat(contato.getDataNascimento()).isEqualTo(LocalDate.of(1960, 3, 14));
                    assertThat(contato.getEmailPessoal()).isEqualTo("eriklehnsherr@gmail.com");
                    assertThat(contato.getEmailComercial()).isNull();
                    assertThat(contato.getFavorito()).isFalse();
                });
    }

    @Test
    public void findAll_contatos_quandoConsultarTodosOsContatos() {
        assertThat(contatoService.findAll())
                .hasSize(3);
    }
}