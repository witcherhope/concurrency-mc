package com.hackzone.concurrencymc.example.threadLocal;

public class RequestHolder {
    private final static ThreadLocal<Integer> requestHolder = new ThreadLocal<>();

    public static void set(Integer id) {
        requestHolder.set(id);
    }

    public static Integer get() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }
}
