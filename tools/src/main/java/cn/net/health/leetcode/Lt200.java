package cn.net.health.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class Lt200 {
    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int total = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    queue.add(new int[]{i, j});
                    total++;
                    bfs(grid, m, n, queue);

                }
            }
        }

        return total;

    }

    private void bfs(char[][] grid, int m, int n, Queue<int[]> queue) {
        while (queue != null && queue.size() > 0) {
            int[] one = queue.poll();
            int x = one[0];
            int y = one[1];
            //对于每个从上下左右4个方向找1,
            int xyArr[][] = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int i = 0; i < 4; i++) {
                //新的坐标
                int newX = x + xyArr[i][0];
                //新的坐标
                int newY = y + xyArr[i][1];
                //满足条件，然后沉岛
                if (newX >= 0 && newY >= 0 && newX < m && newY < n && grid[newX][newY] == '1') {
                    //把满足条件的再次放入队列中
                    queue.add(new int[]{newX, newY});
                    //然后把这个岛沉了
                    grid[newX][newY] = '0';
                }
            }
        }
    }


    public int numIslands2(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int total = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    total++;
                    dfs(grid, i, j, m, n);

                }
            }
        }

        return total;

    }

    private void dfs(char[][] grid, int newX, int newY, int m, int n) {
        if (newX >= 0 && newY >= 0 && newX < m && newY < n && grid[newX][newY] == '1') {
            grid[newX][newY] = 0;
            dfs(grid, newX - 1, newY, m, n);
            dfs(grid, newX + 1, newY, m, n);
            dfs(grid, newX, newY - 1, m, n);
            dfs(grid, newX, newY + 1, m, n);
        }
    }

    public static void main(String[] args) {

    }
}
