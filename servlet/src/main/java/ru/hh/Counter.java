package ru.hh;
public class Counter {
    int data = 0;

    public int get() {
        return data;
    }

    public void inc() {
        data += 1;
    }

    public void dec(int decrease) {
        data -= decrease;
    }

    public void clear() {
        data = 0;
    }
}
