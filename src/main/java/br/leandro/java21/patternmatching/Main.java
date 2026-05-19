package br.leandro.java21.patternmatching;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    public static void main(String[] args) {
        Pagamento pix = new Pix(new BigDecimal("100.00"));
        Pagamento cartaoCredito = new Cartao(new BigDecimal("100.00"), 3);
        Pagamento boleto = new Boleto(new BigDecimal("100.00"), false);

        System.out.println("Taxa Pix: " + calcularTaxa(pix));
        System.out.println("Taxa Cartão: " + calcularTaxa(cartaoCredito));
        System.out.println("Taxa Boleto: " + calcularTaxa(boleto));
    }

    public static BigDecimal calcularTaxa(Pagamento pagamento) {
        return (switch (pagamento) {

            case Pix p when p.valor().compareTo(new BigDecimal("500.00")) > 0 ->
                    p.valor().multiply(new BigDecimal("0.005"));

            case Pix p ->
                    BigDecimal.ZERO;

            case Cartao c when c.parcelas() > 1 ->
                    c.valor().multiply(new BigDecimal("0.035"));

            case Cartao c ->
                    c.valor().multiply(new BigDecimal("0.025"));

            case Boleto b when b.atrasado() ->
                    b.valor().multiply(new BigDecimal("0.02"));

            case Boleto b ->
                    new BigDecimal("3.50");

        }).setScale(2, RoundingMode.HALF_UP);
    }
}