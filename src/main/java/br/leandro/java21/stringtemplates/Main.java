package br.leandro.java21.stringtemplates;

import static java.lang.StringTemplate.STR;

public class Main {

    public static void main(String[] args) {
        String usuario = "Leandro";
        String acao = "LOGIN";
        int status = 200;

        String log = STR."[INFO] usuário=\{usuario}, ação=\{acao}, status=\{status}";

        System.out.println(log);
    }
}