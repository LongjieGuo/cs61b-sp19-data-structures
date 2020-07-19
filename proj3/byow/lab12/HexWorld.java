package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        for (int i = 0; i < s; i++) {
            for (int j = s - i - 1; j < 2 * s + i - 1; j++) {
                if (p.x + j < WIDTH && p.y + i < HEIGHT) {
                    world[p.x + j][p.y + i] = t;
                }
                if (p.x + j < WIDTH && p.y + i + 2 * (s - i) - 1 < HEIGHT) {
                    world[p.x + j][p.y + i + 2 * (s - i) - 1] = t;
                }
            }
        }
    }

    public static void initializeWorld(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public static void drawRandomVerticalHexes(TETile[][] world, Position start, int length, int numOfHexes, TETile t) {
        for (int i = 0; i < numOfHexes; i++) {
            Position now = new Position(start.x, start.y + 6 * i);
            addHexagon(world, now, length, t);
        }
    }

    public static Position calcBottomRightStart(Position p, int length) {
        return new Position(p.x + 2 * length - 1, p.y - length);
    }

    public static Position calcTopRightStart(Position p, int length) {
        return new Position(p.x + 2 * length - 1, p.y + length);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] w = new TETile[WIDTH][HEIGHT];
        Position p = new Position(10 , 10);
        initializeWorld(w);
        int l = 3;
        drawRandomVerticalHexes(w, p, l, 3, Tileset.WALL);
        p = calcBottomRightStart(p, l);
        drawRandomVerticalHexes(w, p, l, 4, Tileset.FLOWER);
        p = calcBottomRightStart(p, l);
        drawRandomVerticalHexes(w, p, l, 5, Tileset.FLOOR);
        p = calcTopRightStart(p, l);
        drawRandomVerticalHexes(w, p, l, 4, Tileset.GRASS);
        p = calcTopRightStart(p, l);
        drawRandomVerticalHexes(w, p, l, 3, Tileset.AVATAR);
        ter.renderFrame(w);
    }
}
