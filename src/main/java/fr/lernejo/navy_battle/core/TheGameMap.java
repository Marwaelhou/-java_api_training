package fr.lernejo.navy_battle.core;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TheGameMap extends GameMapList {
    public TheGameMap(boolean fill) {
        super(fill);

        if (fill)
            printMap();
    }

    public boolean hasShipLeft() {
        for (var row : getGameDetailss()) {
            if (Arrays.stream(row).anyMatch(s -> s == Details.BOAT))
                return true;
        }
        return false;
    }

    public void setDetails(xyz coordination, Details newStatus) {
        getGameDetailss()[coordination.getX()][coordination.getY()] = newStatus;
    }

    public void printMap() {
        System.out.println(" .... ");
        for (Details[] row : getGameDetailss()) {
            System.out.println(Arrays.stream(row).map(Details::getLet).collect(Collectors.joining(" ")));
        }
        System.out.println(" .... ");
    }
}
