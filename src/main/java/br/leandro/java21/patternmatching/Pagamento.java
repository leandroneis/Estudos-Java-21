package br.leandro.java21.patternmatching;

import java.math.BigDecimal;

sealed interface Pagamento permits Pix, Cartao, Boleto {
    BigDecimal valor();
}
