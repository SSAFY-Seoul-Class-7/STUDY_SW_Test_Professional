#include <iostream>
#include <queue>
#include <climits>
#define endl "\n"
#define pii pair<int, int>
typedef long long ll;

using namespace std;

int n, q, sx, ex, sy, ey;
char c;
int board[51][51];
int dist[51][51][6];

int dx[4] = { 0,0,1,-1 };
int dy[4] = { 1,-1,0,0 };

struct Node {
	int w;
	int k;
	pii p;

	bool operator <(const Node& other) const {
		return w > other.w;
	}
};

int dijkstra(pii p1, pii p2) {
	priority_queue<Node> pq;
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= n; j++) {
			for (int k = 1; k <= 5; k++) {
				dist[i][j][k] = INT_MAX;
			}
		}
	}

	dist[p1.first][p1.second][1] = 0;
	pq.push({ 0, 1, p1 });
	
	while (!pq.empty()) {
		Node nd = pq.top();
		pq.pop();

		int x = nd.p.first;
		int y = nd.p.second;
		int k = nd.k;
		int w = nd.w;

		if (dist[x][y][k] < w) continue;
		if (x == p2.first && y == p2.second)return w;

		for (int i = 1; i < k; i++) {
			int nw = w + 1;
			if (dist[x][y][i] > nw) {
				dist[x][y][i] = nw;
				pq.push({ nw,i,nd.p });
			}
		}

		for (int i = k + 1; i <= 5; i++) {
			int nw = w;
			for (int j = k + 1; j <= i; j++) {
				nw += j * j;
			}
			if (dist[x][y][i] > nw) {
				dist[x][y][i] = nw;
				pq.push({ nw,i,nd.p });
			}
		}

		for (int i = 0; i < 4; i++) {
			int nx = x + k * dx[i];
			int ny = y + k * dy[i];
			if (nx<1 || nx>n || ny<1 || ny>n)continue;
			if (board[nx][ny]) continue;
			bool check = false;
			for (int j = 1; j < k; j++) {
				int tmpx = x + j * dx[i];
				int tmpy = y + j * dy[i];
				if (board[tmpx][tmpy] == 2) {
					check = true;
					break;
				}
			}
			if (!check) {
				int nw = w + 1;
				if (dist[nx][ny][k] > nw) {
					dist[nx][ny][k] = nw;
					pq.push({ nw, k, make_pair(nx,ny) });
				}
			}
		}
	}
	return -1;
}

int main()
{
	ios::sync_with_stdio(0); cin.tie(0); cout.tie(0);
	cin >> n;
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= n; j++) {
			cin >> c;
			if (c == 'S')board[i][j] = 1;
			else if (c == '#')board[i][j] = 2;
		}
	}
	cin >> q;
	while (q--) {
		cin >> sx >> sy >> ex >> ey;
		cout << dijkstra(make_pair( sx,sy ), make_pair( ex,ey )) << endl;
	}
}
