package fr.lernejo.navy_battle.core;

import java.util.*;

public class GameMapList {
        private final Integer[] BOATS = {5, 4, 3, 3, 2};
        private final List<List<xyz>> listBoats = new ArrayList<>();
        private final Details[][] Detailss = new Details[10][10];

        public GameMapList(boolean fill) {
            for (Details[] Detailss : this.Detailss) {
                Arrays.fill(Detailss, Details.EMPTY);
            }

            if (fill) {
                buildMap();
            }
        }

        public int getHeight() {
            return Detailss[0].length;
        }

        private void buildMap() {
            var random = new Random();
            var boats = new ArrayList<>(Arrays.asList(BOATS));
            Collections.shuffle(boats);
            while (!boats.isEmpty()) {
                int boat = boats.get(0);
                int y = Math.abs(random.nextInt()) % (getHeight() - 2);
                int x = Math.abs(random.nextInt()) % (getWidth() - 2);
                var orientation = random.nextBoolean() ? BoatOrientationOnMap.HORIZONTAL : BoatOrientationOnMap.VERTICAL;
                if (!canFit(boat, x, y, orientation))
                    continue;
                addBoat(boat, x, y, orientation);
                boats.remove(0);
            }
        }

        private boolean canFit(int length, int x, int y, BoatOrientationOnMap orientation) {
            if (y >= getHeight() || x >= getWidth() || getDetails(x, y) != Details.EMPTY)
                return false;

            if (length == 0)
                return true;

            return switch (orientation) {
                case VERTICAL -> canFit(length - 1, x, y + 1, orientation);
                case HORIZONTAL -> canFit(length - 1, x + 1, y, orientation);
            };
        }
        public int getWidth() {
            return Detailss.length;
        }

        public void addBoat(int length, int x, int y, BoatOrientationOnMap orientation) {
            var coordinates = new ArrayList<xyz>();

            while (length > 0) {
                Detailss[x][y] = Details.BOAT;
                length--;
                coordinates.add(new xyz(x, y));

                switch (orientation) {
                    case HORIZONTAL -> x++;
                    case VERTICAL -> y++;
                }
            }
            listBoats.add(coordinates);
        }

        public Details getDetails(xyz coordination) {
            return getDetails(coordination.getX(), coordination.getY());
        }

        protected Details[][] getGameDetailss() {
            return Detailss;
        }

        public Details getDetails(int x, int y) {
            if (x < 0 || y < 0 || x >= 10 || y >= 10)
                throw new RuntimeException("Invalidate coordinates!");

            return Detailss[x][y];
        }

        public List<List<xyz>> getListBoats() {
            return listBoats;
        }
    }


