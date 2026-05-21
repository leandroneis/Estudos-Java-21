package br.leandro.java21.sealedclasses;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @Test
    void deve_formatar_com_prefixo_email_quando_notificacao_e_email() {
        Notificacao notificacao = new Email("Bem-vindo ao Java 21!");

        assertThat(Main.enviar(notificacao)).isEqualTo("[EMAIL] Bem-vindo ao Java 21!");
    }

    @Test
    void deve_formatar_com_prefixo_sms_quando_notificacao_e_sms() {
        Notificacao notificacao = new SMS("Seu código de verificação é 42.");

        assertThat(Main.enviar(notificacao)).isEqualTo("[SMS] Seu código de verificação é 42.");
    }

    @Test
    void deve_formatar_com_prefixo_push_quando_notificacao_e_push() {
        Notificacao notificacao = new Push("Você tem uma nova mensagem.");

        assertThat(Main.enviar(notificacao)).isEqualTo("[PUSH] Você tem uma nova mensagem.");
    }
}
