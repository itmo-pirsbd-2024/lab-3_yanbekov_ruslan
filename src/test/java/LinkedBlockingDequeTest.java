import org.example.LinkedBlockingDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedBlockingDequeTest {

    private LinkedBlockingDeque<Integer> deque;

    @BeforeEach
    public void setUp() {
        deque = new LinkedBlockingDeque<>(5);
    }

    @Test
    public void testAddFirst() throws InterruptedException {
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(2), deque.peekFirst());
        assertEquals(Integer.valueOf(1), deque.peekLast());
    }

    @Test
    public void testAddLast() throws InterruptedException {
        deque.addLast(1);
        deque.addLast(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(1), deque.peekFirst());
        assertEquals(Integer.valueOf(2), deque.peekLast());
    }

    @Test
    public void testRemoveFirst() throws InterruptedException {
        deque.addLast(1);
        deque.addLast(2);
        assertEquals(Integer.valueOf(1), deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(2), deque.peekFirst());
    }

    @Test
    public void testRemoveLast() throws InterruptedException {
        deque.addLast(1);
        deque.addLast(2);
        assertEquals(Integer.valueOf(2), deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.peekLast());
    }

    @Test
    public void testIsEmpty() throws InterruptedException {
        assertTrue(deque.isEmpty());
        deque.addLast(1);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testIsFull() throws InterruptedException {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        assertTrue(deque.isFull());
        deque.removeFirst();
        assertFalse(deque.isFull());
    }

    @Test
    public void testPeekFirst() throws InterruptedException {
        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.peekFirst());
        deque.addFirst(2);
        assertEquals(Integer.valueOf(2), deque.peekFirst());
    }

    @Test
    public void testPeekLast() throws InterruptedException {
        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.peekLast());
        deque.addLast(2);
        assertEquals(Integer.valueOf(2), deque.peekLast());
    }

    @Test
    public void testSize() throws InterruptedException {
        assertEquals(0, deque.size());
        deque.addLast(1);
        assertEquals(1, deque.size());
        deque.addLast(2);
        assertEquals(2, deque.size());
        deque.removeFirst();
        assertEquals(1, deque.size());
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Runnable producerFirst = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    deque.addFirst(i);
                    Thread.sleep(100); // Имитация работы
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable producerLast = () -> {
            try {
                for (int i = 6; i <= 10; i++) {
                    deque.addLast(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumerFirst = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    deque.removeFirst();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumerLast = () -> {
            try {
                for (int i = 6; i <= 10; i++) {
                    deque.removeLast();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        executorService.submit(producerFirst);
        executorService.submit(producerLast);
        executorService.submit(consumerFirst);
        executorService.submit(consumerLast);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        assertTrue(deque.isEmpty());
    }
}
