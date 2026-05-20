package br.leandro.java21.recordpatterns;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void deveImprimirDadosDoCliente() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        Main.Cliente cliente = new Main.Cliente(
                "Leandro",
                35,
                new Main.Endereco("Ivoti", "RS")
        );

        Main.imprimirDados(cliente);

        System.setOut(originalOut);

        String resultado = output.toString();

        assertTrue(resultado.contains("Nome: Leandro"));
        assertTrue(resultado.contains("Idade: 35"));
        assertTrue(resultado.contains("Cidade: Ivoti"));
        assertTrue(resultado.contains("Estado: RS"));
    }
}