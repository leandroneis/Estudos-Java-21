package br.leandro.java21.patternmatching;

import java.math.BigDecimal;

public record Cartao(BigDecimal valor, int parcelas) implements Pagamento {
}
