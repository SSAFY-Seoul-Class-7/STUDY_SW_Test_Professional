import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        char[][] arr = new char[n][n];
        for (int i = 0; i < n; i++) {
            arr[i] = br.readLine().toCharArray();
        }

        StringBuilder sb = new StringBuilder();
        int q = Integer.parseInt(br.readLine());
        Map<String, Long> resultMap = new HashMap<>();

        mainLoop:
        for (int query = 0; query < q; query++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken()) -1;
            int c1 = Integer.parseInt(st.nextToken()) -1;
            int r2 = Integer.parseInt(st.nextToken()) -1;
            int c2 = Integer.parseInt(st.nextToken()) -1;
            long res = resultMap.getOrDefault(String.format("%d %d %d %d", r1, c1, r2, c2), 0L);

            if (res != 0L) {
                sb.append(res).append('\n');
                continue;
            }

            // x, y, jumpHeight
            boolean[][][] visited = new boolean[n][n][6];

            int[] dx = {1, -1, 0, 0};
            int[] dy = {0, 0, 1, -1};

            Queue<Node> queue = new LinkedList<>();
            queue.add(new Node(r1, c1, 1, 0));
            visited[r1][c1][1] = true;

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if (node.x == r2 && node.y == c2) {
                    sb.append(node.resCount).append('\n');
                    resultMap.put(String.format("%d %d %d %d", r1, c1, r2, c2), node.resCount);
                    resultMap.put(String.format("%d %d %d %d", r2, c2, r1, c1), node.resCount);
                    continue mainLoop;
                }

                // move
                loop:
                for (int i = 0; i < 4; i++) {
                    int newI = node.x + dx[i] * node.jumpHeight;
                    int newJ = node.y + dy[i] * node.jumpHeight;

                    if (newI < 0 || newI >= n || newJ < 0 || newJ >= n || visited[newI][newJ][node.jumpHeight] || arr[newI][newJ] != '.') continue;
                    for (int j = 1; j <= node.jumpHeight; j++) {
                        int tmpI = node.x + dx[i] * j;
                        int tmpJ = node.y + dy[i] * j;
                        if (tmpI < 0 || tmpI >= n || tmpJ < 0 || tmpJ >= n || arr[tmpI][tmpJ] == '#') continue loop;
                    }
                    queue.add(new Node(newI, newJ, node.jumpHeight, node.resCount + 1));
                    visited[newI][newJ][node.jumpHeight] = true;
                }

                // jumpHeight + 1
                if (node.jumpHeight + 1 <= 5 && !visited[node.x][node.y][node.jumpHeight+1]) {
                    queue.add(new Node(node.x, node.y, node.jumpHeight + 1, node.resCount + (node.jumpHeight+1) * (node.jumpHeight+1)));
                    visited[node.x][node.y][node.jumpHeight+1] = true;
                }

                // jumpHeight - 1
                for (int jumpHeight = 1; jumpHeight < node.jumpHeight; jumpHeight++) {
                    if (!visited[node.x][node.y][jumpHeight]) {
                        queue.add(new Node(node.x, node.y, jumpHeight, node.resCount + 1));
                        visited[node.x][node.y][jumpHeight] = true;
                    }
                }
            }
            sb.append(-1).append('\n');
        }
        System.out.println(sb);
    }

    static class Node {
        int jumpHeight, x, y;
        long resCount;
        Node (int x, int y, int jumpHeight, long resCount) {
            this.jumpHeight = jumpHeight;
            this.resCount = resCount;
            this.x = x;
            this.y = y;
        }
    }
}