package br.leandro.java21.recordpatterns;

public class Main {

    record Endereco(String cidade, String estado) {}

    record Cliente(String nome, int idade, Endereco endereco) {}

    public static void main(String[] args) {
        Cliente cliente = new Cliente(
                "Leandro",
                41,
                new Endereco("Ivoti", "RS")
        );

        imprimirDados(cliente);
    }

    static void imprimirDados(Object obj) {
        switch (obj) {
            case Cliente(String nome, int idade, Endereco(String cidade, String estado)) ->
                    System.out.println("""
                            Nome: %s
                            Idade: %d
                            Cidade: %s
                            Estado: %s
                            """.formatted(nome, idade, cidade, estado));

            default ->
                    System.out.println("Objeto desconhecido");
        }
    }
}