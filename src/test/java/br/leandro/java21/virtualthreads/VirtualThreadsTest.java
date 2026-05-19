package br.leandro.java21.virtualthreads;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class VirtualThreadsTest {

    @Test
    void deve_completar_1000_tarefas_em_tempo_proximo_ao_sleep_quando_usar_virtual_threads()
            throws InterruptedException {

        int totalTarefas = 1_000;
        long sleepMs = 100;
        CountDownLatch latch = new CountDownLatch(totalTarefas);
        long inicio = System.currentTimeMillis();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < totalTarefas; i++) {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofMillis(sleepMs));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();
        long duracao = System.currentTimeMillis() - inicio;

        // Virtual threads devem terminar próximo do tempo de sleep, não de 1000 * sleep
        // Margem generosa para ambientes de CI com variação
        assertThat(duracao)
                .as("Virtual threads devem completar em menos de 2s (não 100s)")
                .isLessThan(2_000);
    }

    @Test
    void deve_executar_todas_as_tarefas_quando_usar_virtual_threads()
            throws InterruptedException {

        int totalTarefas = 500;
        AtomicInteger contador = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(totalTarefas);

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < totalTarefas; i++) {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofMillis(10));
                        contador.incrementAndGet();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();

        assertThat(contador.get())
                .as("Todas as tarefas devem ter sido executadas")
                .isEqualTo(totalTarefas);
    }

    @Test
    void deve_ser_virtual_thread_quando_criada_com_Thread_ofVirtual() {
        Thread vt = Thread.ofVirtual().unstarted(() -> {});

        assertThat(vt.isVirtual())
                .as("Thread criada com ofVirtual() deve ser virtual")
                .isTrue();
    }

    @Test
    void deve_ser_thread_de_plataforma_quando_criada_com_Thread_ofPlatform() {
        Thread pt = Thread.ofPlatform().unstarted(() -> {});

        assertThat(pt.isVirtual())
                .as("Thread criada com ofPlatform() não deve ser virtual")
                .isFalse();
    }

    @Test
    void deve_ser_mais_rapido_com_virtual_threads_do_que_com_fixed_pool()
            throws InterruptedException {

        long tempoFixed = Main.medirComFixedPool();
        long tempoVirtual = Main.medirComVirtualThreads();

        assertThat(tempoVirtual)
                .as("Virtual threads devem ser mais rápidas que fixed pool de 10 threads")
                .isLessThan(tempoFixed);
    }
}
