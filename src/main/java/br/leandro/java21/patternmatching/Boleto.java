package br.leandro.java21.patternmatching;

import java.math.BigDecimal;

public record Boleto(BigDecimal valor, boolean atrasado) implements Pagamento {
}
