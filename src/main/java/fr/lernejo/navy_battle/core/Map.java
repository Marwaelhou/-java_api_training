package fr.lernejo.navy_battle.core;

import java.util.ArrayList;
import java.util.List;

public class Map extends TheGameMap {
        private final List<xyz> hitPositions = new ArrayList<>();
        private final List<xyz> testPositions = new ArrayList<>();

        public Map(boolean fill) {
            super(fill);
            for (int i = 0; i < getWidth(); i++) {
                for (int j = i % 2; j < getHeight(); j += 2) {
                    hitPositions.add(new xyz(i, j));
                }
            }
        }

        public void setDetails(xyz coordination, Details newStatus) {
            super.setDetails(coordination, newStatus);

            if (newStatus == Details.SUCCESSFUL_FIRE) {
                testPositions.addAll(List.of(
                    coordination.plus(-1, 0),
                    coordination.plus(0, -1),
                    coordination.plus(1, 0),
                    coordination.plus(0, 1)
                ));
            }
        }


        private xyz hitOnThisPosition() {
            while (hitPositions.size() > 0) {
                var c = hitPositions.remove(0);
                if (getDetails(c) == Details.EMPTY) return c;
            }
            return null;
        }
        private xyz fireOnSuccessfulHit() {
            while (testPositions.size() > 0) {
                var c = testPositions.remove(0);
                if (getDetails(c) == Details.EMPTY) return c;
            }
            return null;
        }

        public xyz getNextPointforHit() {
            xyz coordination = null;
            if (!testPositions.isEmpty())
                coordination = fireOnSuccessfulHit();

            if (coordination == null && !hitPositions.isEmpty())
                coordination = hitOnThisPosition();

            if (coordination == null)
                coordination = bruteHit();

            return coordination;
        }

        public FireResult hitOnThisPosition(xyz coordination) {
            if (getDetails(coordination) != Details.BOAT)
                return FireResult.MISS;

            var first = getListBoats().stream().filter(s -> s.contains(coordination)).findFirst();
            assert (first.isPresent());
            first.get().remove(coordination);

            setDetails(coordination, Details.SUCCESSFUL_FIRE);

            return first.get().isEmpty() ? FireResult.SUNK : FireResult.HIT;
        }
        private xyz bruteHit() {
            // System.err.println("Brute force required!");
            for (int i = 0; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {
                    if (getDetails(i, j) == Details.EMPTY)
                        return new xyz(i, j);
                }
            }
            throw new RuntimeException("OOHH THIS IS A CHEATER");
        }

    }
