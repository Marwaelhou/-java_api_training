package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.core.*;

public class GamePlay {
    private final Map localMap;
    private final Map remoteMap;
    private final Option<Status> status = new Option<>(Status.ONGOING);

    public GamePlay() {
        remoteMap = new Map(false);
        localMap = new Map(true);
    }

    public void wonGame() {
        status.set(Status.WON);

        System.out.println("WIN WIN WIN");
        System.out.println("Finitoo");
        System.out.println("Oposit-map:");
        remoteMap.printMap();

        System.out.println(" My-map:");
        localMap.printMap();
    }

    public xyz getNextPlaceToHit() {
        return remoteMap.getNextPointforHit();
    }

    public FireResult hit(xyz coordination) {
        return localMap.hitOnThisPosition(coordination);
    }

    public void setFireResult(xyz coordination, FireResult result) {
        if (result == FireResult.MISS)
            remoteMap.setDetails(coordination, Details.MISSED_FIRE);
        else
            remoteMap.setDetails(coordination, Details.SUCCESSFUL_FIRE);
    }

    public Status getStatus() {
        if (!localMap.hasShipLeft())
            status.set(Status.LOST);
        return status.get();
    }
    public boolean localMapShipLeft() {
        return localMap.hasShipLeft();
    }

}
