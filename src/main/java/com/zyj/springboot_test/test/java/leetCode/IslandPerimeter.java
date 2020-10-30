package com.zyj.springboot_test.test.java.leetCode;

public class IslandPerimeter {

    /**
     * 给定一个包含 0 和 1 的二维网格地图，其中 1 表示陆地 0 表示水域。
     * <p>
     * 网格中的格子水平和垂直方向相连（对角线方向不相连）。
     * 整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
     * <p>
     * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。
     * 格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
     * <p>
     *  
     */
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    public static void main(String[] args) {
        int[][] grid = {{0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0}};

        System.out.println(islandPerimeter(grid));

    }
    public static int islandPerimeter(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int crosswise = grid.length;//横向长度
        int lengthways = grid[0].length;//纵向长度

        int perimeter = 0;
        for (int i = 0; i < crosswise; i++) {
            for (int j = 0; j < lengthways; j++) {
                if (grid[i][j] == 1) {
                    //解法1：这个解法更耗内存和时间，槽


//                    for (int k = 0; k < 4; k++) {
//                        int x = i + dx[k];
//                        int y = j + dy[k];
//                        if (x < 0 || x > crosswise-1 || y < 0 || y > lengthways-1 || grid[x][y] != 1) {
//                            perimeter++;
//                        }
//                    }

//                    解法2（自己想的，性能反而中等，算了，这题不纠结了，对于性能提升不报希望了）

//                    //左边
//                    if ((i - 1 < 0 || grid[i - 1][j] != 1)) {
//                        perimeter++;
//                    }
//                    //右边
//                    if (i + 1 >= crosswise || grid[i + 1][j] != 1) {
//                        perimeter++;
//                    }
//                    //上边
//                    if (j - 1 < 0 || grid[i ][j- 1] != 1) {
//                        perimeter++;
//                    }
//                    //下边
//                    if (j + 1 >= lengthways || grid[i][j + 1] != 1) {
//                        perimeter++;
//                    }
                }
            }
        }
        return perimeter;

    }
}
