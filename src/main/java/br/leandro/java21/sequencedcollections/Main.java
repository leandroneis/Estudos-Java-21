package br.leandro.java21.sequencedcollections;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        HistoricoNavegacao historico = new HistoricoNavegacao();

        historico.acessar("google.com");
        historico.acessar("youtube.com");
        historico.acessar("github.com");

        System.out.println("Primeira página: " + historico.primeiraPagina());
        System.out.println("Última página: " + historico.paginaAtual());
        System.out.println("Histórico reverso: " + historico.historicoReverso());
    }

    static class HistoricoNavegacao {

        private static final int LIMITE = 5;

        private final LinkedList<String> paginas = new LinkedList<>();

        public void acessar(String pagina) {
            paginas.addLast(pagina);

            if (paginas.size() > LIMITE) {
                paginas.removeFirst();
            }
        }

        public String primeiraPagina() {
            return paginas.getFirst();
        }

        public String paginaAtual() {
            return paginas.getLast();
        }

        public String voltar() {
            paginas.removeLast();
            return paginas.getLast();
        }

        public LinkedList<String> historico() {
            return new LinkedList<>(paginas);
        }

        public LinkedList<String> historicoReverso() {
            return new LinkedList<>(paginas.reversed());
        }
    }
}