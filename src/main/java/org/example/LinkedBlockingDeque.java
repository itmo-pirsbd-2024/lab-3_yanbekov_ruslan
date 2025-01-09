package org.example;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class LinkedBlockingDeque<E> {
    private final LinkedList<E> list;
    private final Lock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    private final int capacity;

    public LinkedBlockingDeque(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Элементов должно быть больше 0");
        }
        this.capacity = capacity;
        this.list = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    public void addFirst(E e) throws InterruptedException {
        lock.lock();
        try {
            while (list.size() == capacity) {
                notFull.await(); // если очередь полна
            }
            list.addFirst(e);
            notEmpty.signal(); // очередь не пуста
        } finally {
            lock.unlock();
        }
    }

    public void addLast(E e) throws InterruptedException {
        lock.lock();
        try {
            while (list.size() == capacity) {
                notFull.await();
            }
            list.addLast(e);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E removeFirst() throws InterruptedException {
        lock.lock();
        try {
            while (list.isEmpty()) {
                notEmpty.await();
            }
            E e = list.removeFirst();
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }

    public E removeLast() throws InterruptedException {
        lock.lock();
        try {
            while (list.isEmpty()) {
                notEmpty.await();
            }
            E e = list.removeLast();
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }

    public E peekFirst() {
        lock.lock();
        try {
            if (list.isEmpty()) {
                return null;
            }
            return list.getFirst();
        } finally {
            lock.unlock();
        }
    }

    public E peekLast() {
        lock.lock();
        try {
            if (list.isEmpty()) {
                return null;
            }
            return list.getLast();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return list.size();
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return list.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public boolean isFull() {
        lock.lock();
        try {
            return list.size() == capacity;
        } finally {
            lock.unlock();
        }
    }
}



