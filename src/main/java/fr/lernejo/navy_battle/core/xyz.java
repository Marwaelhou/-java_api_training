package fr.lernejo.navy_battle.core;

import java.util.Objects;

public class xyz {
    private final int x;
    private final int y;

    public xyz(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public xyz(String code) {
        if (code.length() < 2 || code.length() > 3 || !code.matches("^[A-J]([1-9]|10)$"))
            throw new RuntimeException("The code " + code + "is invalid!");

        y = Integer.parseInt(code.substring(1)) - 1;
        x = code.charAt(0) - 'A';
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public xyz plus(int x, int y) {
        y = y + this.y;
        x = x + this.x;

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x >= 10) x = 9;
        if (y >= 10) y = 9;

        return new xyz(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        xyz that = (xyz) o;
        return x == that.x && y == that.y;
    }

    @Override
    public String toString() {
        return Character.toString('A' + x) + (y + 1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
