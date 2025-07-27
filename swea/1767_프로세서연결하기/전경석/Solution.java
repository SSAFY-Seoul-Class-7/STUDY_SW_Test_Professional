import java.io.*;
import java.util.*;

class Solution {
    static int maxConnectedCoreCount, minLength, coreSize, arr[][], n;
    static List<Node> cores;
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

        for (int test_case = 1; test_case <= T; test_case++) {
            maxConnectedCoreCount = 0;
            minLength = 0;
            n = Integer.parseInt(br.readLine());
            arr = new int[n][n];
            cores = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                    if (arr[i][j] == 1) {
                        // 가장자리가 아닌 코어만 추가.
                        if (i != 0 && i != n-1 && j != 0 && j != n-1) {
                            cores.add(new Node(i, j));
                        }
                    }
                }
            }
            coreSize = cores.size();
            back(0, 0, 0);
            sb.append(String.format("#%d %d\n", test_case, minLength));
        }
        System.out.println(sb);
    }

    static class Node {
        int x, y;
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static void back(int depth, int totalLength, int connectedCoreCount) {
        if (depth == coreSize) {
            if (maxConnectedCoreCount < connectedCoreCount) {
                maxConnectedCoreCount = connectedCoreCount;
                minLength = totalLength;
            }
            else if(maxConnectedCoreCount == connectedCoreCount) {
                minLength = Math.min(minLength, totalLength);
            }
            return;
        }

        // 전선 연결 X
        back(depth + 1, totalLength, connectedCoreCount);

        // 전선 연결 O
        loop:
        for (int i = 0; i < 4; i++) {
            int weight = 1;
            List<Node> switchedNodes = new ArrayList<>();
            while (true) {
                int newI = cores.get(depth).x + dx[i] * weight;
                int newJ = cores.get(depth).y + dy[i] * weight;
                if (newI < 0 || newI >= n || newJ < 0 || newJ >= n) break;
                if (arr[newI][newJ] != 0) {
                    // 배열 원상태 복원
                    restore(switchedNodes);
                    continue loop;
                }
                arr[newI][newJ] = -1;
                switchedNodes.add(new Node(newI, newJ));
                weight++;
            }
            back(depth + 1, totalLength + switchedNodes.size(), connectedCoreCount + 1);
            restore(switchedNodes);
        }

    }
    static void restore(List<Node> switchedNodes) {
        for (Node node : switchedNodes) {
            arr[node.x][node.y] = 0;
        }
    }
}