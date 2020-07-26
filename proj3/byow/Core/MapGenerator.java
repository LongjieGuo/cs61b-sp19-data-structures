package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    private int width;
    private int height;

    public TETile[][] generate(int seed) {
        Random r = new Random(seed);

        width = RandomUtils.uniform(r, 40, 100);
        height = RandomUtils.uniform(r, 20, 50);

        TETile[][] teTiles = new TETile[width][height];
        ArrayList<Room> rooms;

        setAllToWall(teTiles, width, height);
        rooms = generateRooms(r, width, height);
        fillInAllRoomSpaces(teTiles, rooms);
        connectAllRooms(teTiles, r, rooms);
        removeRedundantWalls(teTiles);

        return teTiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static void setAllToWall(TETile[][] teTiles, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                teTiles[i][j] = Tileset.WALL;
            }
        }
    }

    public static Room generateOneRandomRoom(Random r, int width, int height) {
        int topLeftX = RandomUtils.uniform(r, 1, width - 2);
        int bottomRightX = RandomUtils.uniform(r, topLeftX + 1, width - 1);
        while (bottomRightX - topLeftX >= 8) {
            bottomRightX = RandomUtils.uniform(r, topLeftX + 1, width - 1);
        }
        int bottomRightY = RandomUtils.uniform(r, 1, height - 2);
        int topLeftY = RandomUtils.uniform(r, bottomRightY + 1, height - 1);
        while (topLeftY - bottomRightY >= 8) {
            topLeftY = RandomUtils.uniform(r, bottomRightY + 1, height - 1);
        }
        return new Room(new Position(topLeftX, topLeftY), new Position(bottomRightX, bottomRightY));
    }

    private static ArrayList<Room> generateRooms(Random r, int width, int height) {
        int numOfRooms = RandomUtils.uniform(r, 10, 20);
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < numOfRooms; i++) {
            Room newRoom = MapGenerator.generateOneRandomRoom(r, width, height);
            rooms.add(newRoom);
        }
        return rooms;
    }

    private static void fillInOneRoom(TETile[][] teTiles, Room room) {
        for (int i = room.getTopLeft().getX(); i <= room.getBottomRight().getX(); i++) {
            for (int j = room.getBottomRight().getY(); j <= room.getTopLeft().getY(); j++) {
                teTiles[i][j] = Tileset.FLOOR;
            }
        }
    }

    private static void fillInAllRoomSpaces(TETile[][] teTiles, List<Room> rooms) {
        for (Room room : rooms) {
            fillInOneRoom(teTiles, room);
        }
    }

    private static void connectTwoPointsUsingHallway(TETile[][] teTiles, Position p1, Position p2) {
        int upper = Math.max(p1.getY(), p2.getY());
        int lower = Math.min(p1.getY(), p2.getY());
        int left = Math.min(p1.getX(), p2.getX());
        int right = Math.max(p1.getX(), p2.getX());

        if (left == right) {
            for (int i = lower; i <= upper; i++) {
                teTiles[right][i] = Tileset.FLOOR;
            }
        } else if (lower == upper) {
            for (int i = left; i <= right; i++) {
                teTiles[i][upper] = Tileset.FLOOR;
            }
        } else {
            for (int i = left; i <= right; i++) {
                teTiles[i][upper] = Tileset.FLOOR;
            }
            Position end = new Position(right, upper);
            if (end.getY() == p2.getY() && end.getX() == p2.getX()) {
                connectTwoPointsUsingHallway(teTiles, p1, new Position(left, p2.getY()));
            } else if (end.getY() == p1.getY() && end.getX() == p1.getX()) {
                connectTwoPointsUsingHallway(teTiles, p2, new Position(left, p1.getY()));
            } else {
                if (upper == p1.getY()) {
                    connectTwoPointsUsingHallway(teTiles, p2, new Position(right, upper));
                } else {
                    connectTwoPointsUsingHallway(teTiles, p1, new Position(right, upper));
                }
            }
        }
    }

    private static void connectTwoRooms(TETile[][] teTiles, Random random, Room r1, Room r2) {
        Position p1 = new Position(RandomUtils.uniform(random, r1.getTopLeft().getX(), r1.getBottomRight().getX() + 1),
                                    RandomUtils.uniform(random, r1.getBottomRight().getY(), r1.getTopLeft().getY() + 1));
        Position p2 = new Position(RandomUtils.uniform(random, r2.getTopLeft().getX(), r2.getBottomRight().getX() + 1),
                RandomUtils.uniform(random, r2.getBottomRight().getY(), r2.getTopLeft().getY() + 1));
        connectTwoPointsUsingHallway(teTiles, p1, p2);
    }

    private static void connectAllRooms(TETile[][] teTiles, Random random, ArrayList<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            if (i > 0) {
                connectTwoRooms(teTiles, random, rooms.get(i - 1), rooms.get(i));
            }
        }
    }

    /**
     * @author: Zangsy
     */
    private static void removeRedundantWalls(TETile[][] world) {
        int[][] neighbours = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                if (world[x][y].equals(Tileset.WALL)) {
                    int floorNum = 0;
                    for (int[] nbr : neighbours) {
                        int adjX = x + nbr[0];
                        int adjY = y + nbr[1];
                        if (adjX >= 0 && adjX < world.length) {
                            if (adjY >= 0 && adjY < world[0].length) {
                                if (world[adjX][adjY].equals(Tileset.FLOOR)) {
                                    floorNum += 1;
                                }
                            }
                        }
                    }
                    // Check whether current wall has floor neighbours.
                    // If not, it is redundant, replace it with nothing.
                    if (floorNum == 0) {
                        world[x][y] = Tileset.NOTHING;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int N = 40;
        MapGenerator mg = new MapGenerator();
        TERenderer ter = new TERenderer();
        TETile[][] t = new TETile[N][N];
        ter.initialize(N, N);
        Random random = new Random(35566);

        Room r1 = MapGenerator.generateOneRandomRoom(random, 40, 40);
        Room r2 = MapGenerator.generateOneRandomRoom(random, 40, 40);
        mg.setAllToWall(t, 40, 40);
        fillInOneRoom(t, r1);
        fillInOneRoom(t, r2);

        MapGenerator.connectTwoRooms(t, random, r1, r2);
        MapGenerator.removeRedundantWalls(t);
        ter.renderFrame(t);
    }
}
