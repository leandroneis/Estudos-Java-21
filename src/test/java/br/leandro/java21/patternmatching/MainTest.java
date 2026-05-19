package br.leandro.java21.patternmatching;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @ParameterizedTest
    @MethodSource("cenariosDeTaxa")
    void deve_calcularTaxa_conformeTipoDePagamento(Pagamento pagamento, BigDecimal taxaEsperada) {
        BigDecimal taxaCalculada = Main.calcularTaxa(pagamento);

        assertEquals(taxaEsperada, taxaCalculada);
    }

    static Stream<Object[]> cenariosDeTaxa() {
        return Stream.of(
                new Object[]{new Pix(new BigDecimal("100.00")), new BigDecimal("0.00")},
                new Object[]{new Pix(new BigDecimal("1000.00")), new BigDecimal("5.00")},
                new Object[]{new Cartao(new BigDecimal("100.00"), 1), new BigDecimal("2.50")},
                new Object[]{new Cartao(new BigDecimal("100.00"), 3), new BigDecimal("3.50")},
                new Object[]{new Boleto(new BigDecimal("100.00"), false), new BigDecimal("3.50")},
                new Object[]{new Boleto(new BigDecimal("100.00"), true), new BigDecimal("2.00")}
        );
    }
}