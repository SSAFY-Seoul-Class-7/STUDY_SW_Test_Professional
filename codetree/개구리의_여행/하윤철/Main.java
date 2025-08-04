import java.io.*;
import java.util.*;

public class Main {
	static int n;
	static char[][] lake;
	static int[][][] dist;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		lake = new char[n][n];

		for (int i = 0; i < n; i++) {
			String input = br.readLine();
			for (int j = 0; j < n; j++) {
				lake[i][j] = input.charAt(j);
			}
		}
		int q = Integer.parseInt(br.readLine());
		while (q-- > 0) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken()) - 1;
			int sy = Integer.parseInt(st.nextToken()) - 1;
			int ex = Integer.parseInt(st.nextToken()) - 1;
			int ey = Integer.parseInt(st.nextToken()) - 1;
			System.out.println(dijkstra(sx, sy, ex, ey));
		}

	}

	static int dijkstra(int sx, int sy, int ex, int ey) {
		PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x[3]));// 시간 오름차순
		dist = new int[n][n][6];// x,y,점프력
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 1; k < 6; k++) {
					dist[i][j][k] = Integer.MAX_VALUE;
				}
			}
		}
		dist[sx][sy][1] = 0;
		pq.offer(new int[] { sx, sy, 1, 0 });
		while (!pq.isEmpty()) {
			int[] now = pq.poll();
			int x = now[0];
			int y = now[1];
			int jump = now[2];
			int time = now[3];
			if (dist[x][y][jump] < time)
				continue;
			if (x == ex && y == ey)
				return time;// 최단거리로 도착하면 탈출
			// 점프력 증가
			if (jump < 5) {
				int j = jump + 1;
				int t = time + (j * j);
				if (t < dist[x][y][j]) {
					dist[x][y][j] = t;
					pq.offer(new int[] { x, y, j, t });
				}
			}
			// 점프력 감소
			for (int j = jump-1; j > 0; j--) {
				int t = time + 1;
				if (dist[x][y][j] > t) {
					dist[x][y][j] = t;
					pq.offer(new int[] { x, y, j, t });
				}
			}
			// 이동
			
			for(int k=0;k<4;k++) {
				int nx=x;
				int ny=y;
				boolean flag=true;
				for(int j=0;j<jump;j++) {
					nx+=dx[k];
					ny+=dy[k];
					if(nx<0||ny<0||nx>=n||ny>=n||lake[nx][ny]=='#') {
						flag=false;
						break;
					}
				}
				if(flag&&lake[nx][ny]=='.') {
					if(dist[nx][ny][jump]>time+1) {
						dist[nx][ny][jump]=time+1;
						pq.offer(new int[] {nx,ny,jump,time+1});
					}
				}
			}
			
		}

	
		
		
		return -1;
	}

}
