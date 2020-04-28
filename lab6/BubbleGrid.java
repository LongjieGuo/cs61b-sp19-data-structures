public class BubbleGrid {
    private int[][] grid;
    private int[][] grid_map;
    private int sizeRow;
    private int sizeColumn;
    private int[] bubbleFallCount;
    private UnionFind u;

    public BubbleGrid(int[][] g) {
        grid = g;
        sizeRow = g.length;
        sizeColumn = g[0].length;
        grid_map = new int[sizeRow][sizeColumn];
        u = new UnionFind(sizeRow * sizeColumn);
        int index = 0;
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeColumn; j++) {
                grid_map[i][j] = index;
                index++;
            }
        }

        
    }

    public int[] popBubbles(int[][] darts) {
        bubbleFallCount = new int[darts.length];

        return bubbleFallCount;
    }

}
