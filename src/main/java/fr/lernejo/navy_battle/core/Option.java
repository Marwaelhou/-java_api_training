package fr.lernejo.navy_battle.core;


import java.util.ArrayList;
import java.util.List;

public class Option<T> {
    private final List<T> list = new ArrayList<>();

    public Option() { }
    public void set(T obj) {
        list.clear();
        list.add(obj);
    }
    public Option(T obj) {
        set(obj);
    }

    public T get() {
        if(list.isEmpty())
            throw new RuntimeException("Option is empty!");

        return list.get(0);
    }
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return  list.isEmpty();
    }

    public void unset() {
        list.clear();
    }


}
