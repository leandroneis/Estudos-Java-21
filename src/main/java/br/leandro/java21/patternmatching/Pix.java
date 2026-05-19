package br.leandro.java21.patternmatching;

import java.math.BigDecimal;

public record Pix(BigDecimal valor) implements Pagamento {
}
