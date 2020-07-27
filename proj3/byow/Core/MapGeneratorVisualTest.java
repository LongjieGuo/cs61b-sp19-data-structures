package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class MapGeneratorVisualTest {
    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator();
        TERenderer ter = new TERenderer();
        TETile[][] t = mg.generate(66754);
        ter.initialize(mg.getWidth(), mg.getHeight());
        ter.renderFrame(t);
    }
}
