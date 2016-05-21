package fr.kissy.ae.evolution.utils;

import com.google.common.base.Objects;

public class MutableInt {
    int value;

    public MutableInt() {
        this(0);
    }

    public MutableInt(int value) {
        this.value = value;
    }

    public void increment() {
        ++value;
    }

    public int get() {
        return value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .toString();
    }
}