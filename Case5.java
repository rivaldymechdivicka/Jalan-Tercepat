import java.util.*;

public class Case5 {
    private static class Point {
        int x, y, dist;
        List<String> path;

        Point(int x, int y, int dist, List<String> path) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.path = new ArrayList<>(path);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> map = new ArrayList<>();
        String line;
        while (!(line = scanner.nextLine()).equals("OK")) {
            map.add(line);
        }

        char[][] grid = new char[map.size()][map.get(0).length()];
        Point start = null, end = null;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                grid[i][j] = map.get(i).charAt(j);
                if (grid[i][j] == '^') {
                    start = new Point(i, j, 0, new ArrayList<>());
                } else if (grid[i][j] == '*') {
                    end = new Point(i, j, 0, new ArrayList<>());
                }
            }
        }

        int[] dX = {0, 0, 1, -1};
        int[] dY = {1, -1, 0, 0};
        String[] directions = {"kanan", "kiri", "bawah", "atas"};

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.x == end.x && current.y == end.y) {
                String prevDirection = "";
                int count = 0;
                int totalSteps = 0;
                
                for (String step : current.path) {
                    String[] parts = step.split(" ");
                    int stepCount = Integer.parseInt(parts[0]);
                    String direction = parts[1];

                    if (!direction.equals(prevDirection)) {
                        if (!prevDirection.equals("")) {
                            System.out.println(count + " " + prevDirection);
                        }
                        prevDirection = direction;
                        count = stepCount;
                    } else {
                        count += stepCount;
                    }

                    totalSteps += stepCount;
                }
                
                System.out.println(count + " " + prevDirection);
                System.out.println(totalSteps + " Langkah");
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dX[i];
                int newY = current.y + dY[i];
                if (isValidMove(grid, newX, newY, visited)) {
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(1 + " " + directions[i]);
                    queue.add(new Point(newX, newY, current.dist + 1, newPath));
                    visited[newX][newY] = true;
                }
            }
        }

        System.out.println("tidak ada jalan");
    }

    private static boolean isValidMove(char[][] grid, int x, int y, boolean[][] visited) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] != '#' && !visited[x][y];
    }
}
