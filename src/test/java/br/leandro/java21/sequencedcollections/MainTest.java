package br.leandro.java21.sequencedcollections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void deveAdicionarPaginasNoHistorico() {
        Main.HistoricoNavegacao historico = new Main.HistoricoNavegacao();

        historico.acessar("google.com");
        historico.acessar("youtube.com");

        assertEquals("google.com", historico.primeiraPagina());
        assertEquals("youtube.com", historico.paginaAtual());
    }

    @Test
    void deveManterNoMaximoCincoPaginas() {
        Main.HistoricoNavegacao historico = new Main.HistoricoNavegacao();

        historico.acessar("pagina1.com");
        historico.acessar("pagina2.com");
        historico.acessar("pagina3.com");
        historico.acessar("pagina4.com");
        historico.acessar("pagina5.com");
        historico.acessar("pagina6.com");

        assertEquals(5, historico.historico().size());
        assertEquals("pagina2.com", historico.primeiraPagina());
        assertEquals("pagina6.com", historico.paginaAtual());
    }

    @Test
    void deveVoltarParaPaginaAnterior() {
        Main.HistoricoNavegacao historico = new Main.HistoricoNavegacao();

        historico.acessar("google.com");
        historico.acessar("youtube.com");
        historico.acessar("github.com");

        String paginaAnterior = historico.voltar();

        assertEquals("youtube.com", paginaAnterior);
        assertEquals("youtube.com", historico.paginaAtual());
    }

    @Test
    void deveRetornarHistoricoReverso() {
        Main.HistoricoNavegacao historico = new Main.HistoricoNavegacao();

        historico.acessar("google.com");
        historico.acessar("youtube.com");
        historico.acessar("github.com");

        List<String> reverso = historico.historicoReverso();

        assertEquals(List.of(
                "github.com",
                "youtube.com",
                "google.com"
        ), reverso);
    }
}