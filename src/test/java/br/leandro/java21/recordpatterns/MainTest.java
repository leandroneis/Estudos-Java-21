package br.leandro.java21.recordpatterns;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void redirecionarSaida() {
        originalOut = System.out;
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restaurarSaida() {
        System.setOut(originalOut);
    }

    @Test
    void deveImprimirDadosDoCliente() {
        Main.Cliente cliente = new Main.Cliente(
                "Leandro",
                35,
                new Main.Endereco("Ivoti", "RS")
        );

        Main.imprimirDados(cliente);

        String resultado = output.toString();
        assertTrue(resultado.contains("Nome: Leandro"));
        assertTrue(resultado.contains("Idade: 35"));
        assertTrue(resultado.contains("Cidade: Ivoti"));
        assertTrue(resultado.contains("Estado: RS"));
    }

    @Test
    void deveImprimirObjetoDesconhecido() {
        Main.imprimirDados("qualquer coisa");

        assertTrue(output.toString().contains("Objeto desconhecido"));
    }

    @Test
    void deveLancarNullPointerException_quandoObjetoForNulo() {
        // switch com pattern matching em Java 21 não roteia null para o default — lança NPE
        assertThrows(NullPointerException.class, () -> Main.imprimirDados(null));
    }
}