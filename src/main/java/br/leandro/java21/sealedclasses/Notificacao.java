package br.leandro.java21.sealedclasses;

sealed interface Notificacao permits Email, SMS, Push {
    String mensagem();
}
