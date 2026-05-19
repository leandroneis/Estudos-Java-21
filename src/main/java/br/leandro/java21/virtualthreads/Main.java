package br.leandro.java21.virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Virtual Threads (Project Loom) — Java 21 vs Java 17
 *
 * Java 17: threads de plataforma são caras (cada uma consome ~1MB de stack +
 * overhead de SO). Com pool fixo de 10 threads, 1000 tarefas de 100ms levam ~10s
 * porque só 10 rodam por vez.
 *
 * Java 21: virtual threads são gerenciadas pela JVM, não pelo SO. São baratas
 * o suficiente para criar uma por tarefa. Bloqueio (sleep, I/O) libera o carrier
 * thread para outra virtual thread — as 1000 tarefas completam em ~100ms.
 */
public class Main {

    private static final int TOTAL_TAREFAS = 1_000;
    private static final long SLEEP_MS = 100;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Virtual Threads: Java 21 vs Java 17 ===\n");

        long tempoFixedPool = medirComFixedPool();
        long tempoVirtualThreads = medirComVirtualThreads();

        System.out.printf("%n--- Resultado ---%n");
        System.out.printf("Fixed pool (10 threads): %d ms%n", tempoFixedPool);
        System.out.printf("Virtual threads:         %d ms%n", tempoVirtualThreads);
        System.out.printf("Speedup:                 %.1fx mais rápido%n",
                (double) tempoFixedPool / tempoVirtualThreads);
    }

    /**
     * Jeito Java 17: pool fixo de threads de plataforma.
     * Gargalo: só 10 threads rodam ao mesmo tempo; as demais ficam na fila.
     */
    public static long medirComFixedPool() throws InterruptedException {
        System.out.println("Iniciando: Fixed thread pool (10 threads)...");
        CountDownLatch latch = new CountDownLatch(TOTAL_TAREFAS);
        Instant inicio = Instant.now();

        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            for (int i = 0; i < TOTAL_TAREFAS; i++) {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofMillis(SLEEP_MS));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();
        long duracao = Duration.between(inicio, Instant.now()).toMillis();
        System.out.printf("Fixed pool concluído em %d ms%n", duracao);
        return duracao;
    }

    /**
     * Jeito Java 21: virtual threads via newVirtualThreadPerTaskExecutor().
     * Cada tarefa ganha sua própria virtual thread — sem fila, sem espera.
     * O bloqueio no sleep() libera o carrier thread para outra virtual thread.
     */
    public static long medirComVirtualThreads() throws InterruptedException {
        System.out.println("\nIniciando: Virtual threads (uma por tarefa)...");
        CountDownLatch latch = new CountDownLatch(TOTAL_TAREFAS);
        Instant inicio = Instant.now();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < TOTAL_TAREFAS; i++) {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofMillis(SLEEP_MS));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();
        long duracao = Duration.between(inicio, Instant.now()).toMillis();
        System.out.printf("Virtual threads concluído em %d ms%n", duracao);
        return duracao;
    }
}
