package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeBenchmarkBasic {

    private static final int[] CAPACITIES = {1000, 10000, 100000, 1000000, 10000000};
    private static final int[] THREADS = {1, 2, 4, 8};

    public static void main(String[] args) throws InterruptedException {
        for (int capacity : CAPACITIES) {
            for (int threads : THREADS) {
                System.out.println("Benchmarking with capacity: " + capacity + ", threads: " + threads);
                benchmarkAddFirst(capacity, threads);
                benchmarkAddLast(capacity, threads);
                benchmarkRemoveFirst(capacity, threads);
                benchmarkRemoveLast(capacity, threads);
                benchmarkPeekFirst(capacity, threads);
                benchmarkPeekLast(capacity, threads);
                System.out.println();
            }
        }
    }

    private static void benchmarkAddFirst(int capacity, int threads) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.addFirst(j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("addFirst: " + duration + " ms");
    }

    private static void benchmarkAddLast(int capacity, int threads) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.addLast(j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("addLast: " + duration + " ms");
    }

    private static void benchmarkRemoveFirst(int capacity, int threads) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.addLast(j);
                    }
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.removeFirst();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("removeFirst: " + duration + " ms");
    }

    private static void benchmarkRemoveLast(int capacity, int threads) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.addLast(j);
                    }
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.removeLast();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("removeLast: " + duration + " ms");
    }

    private static void benchmarkPeekFirst(int capacity, int threads) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.addLast(j);
                    }
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.peekFirst();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("peekFirst: " + duration + " ms");
    }

    private static void benchmarkPeekLast(int capacity, int threads) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.addLast(j);
                    }
                    for (int j = 0; j < capacity / threads; j++) {
                        deque.peekLast();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("peekLast: " + duration + " ms");
    }
}
