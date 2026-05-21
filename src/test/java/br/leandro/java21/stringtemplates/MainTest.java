package br.leandro.java21.stringtemplates;

import org.junit.jupiter.api.Test;

import static java.lang.StringTemplate.STR;
import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @Test
    void deve_montar_mensagem_de_log_com_dados_dinamicos_quando_variaveis_sao_interpoladas() {
        String usuario = "Leandro";
        String acao = "LOGIN";
        int status = 200;

        String log = STR."[INFO] usuário=\{usuario}, ação=\{acao}, status=\{status}";

        assertThat(log).isEqualTo("[INFO] usuário=Leandro, ação=LOGIN, status=200");
    }
}