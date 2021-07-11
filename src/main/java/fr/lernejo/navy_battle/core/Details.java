package fr.lernejo.navy_battle.core;

public enum Details {
    SUCCESSFUL_FIRE("X"),
    BOAT("B"),
    MISSED_FIRE("-"),
    EMPTY(".");

    private final String let;

    Details(String let) {
        this.let = let;
    }

    public String getLet() {
        return let;
    }
}
