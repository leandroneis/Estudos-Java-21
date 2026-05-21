package br.leandro.java21.sealedclasses;

import static java.lang.StringTemplate.STR;

public class Main {

    // Switch sem 'default': o compilador exige que todos os subtipos de Notificacao sejam tratados.
    // Se um 4º subtipo for adicionado em Notificacao sem atualizar este switch, ocorre erro de compilação.
    static String enviar(Notificacao notificacao) {
        return switch (notificacao) {
            case Email e -> STR."[EMAIL] \{e.mensagem()}";
            case SMS s   -> STR."[SMS] \{s.mensagem()}";
            case Push p  -> STR."[PUSH] \{p.mensagem()}";
        };
    }

    public static void main(String[] args) {
        Notificacao email = new Email("Bem-vindo ao Java 21!");
        Notificacao sms   = new SMS("Seu código de verificação é 42.");
        Notificacao push  = new Push("Você tem uma nova mensagem.");

        System.out.println(enviar(email));
        System.out.println(enviar(sms));
        System.out.println(enviar(push));
    }
}