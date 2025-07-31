#include <vector>
#include <queue>
#include <climits>
#include <algorithm>

using namespace std;
#define pii pair<int,int>

struct S {
	int w;
	int node;

	S(int w, int node) : w(w), node(node) {}

	bool operator<(const S& other) const {
		return w > other.w;
	}
};

vector<pii> edges[5'001];
vector<int> dist;
vector<int> brand_hotel[50];
int hotel_brand[5'001];
int n;

S dijkstra(int mStart, int target, int avoid) {
	dist.assign(n, INT_MAX);
	priority_queue<S> pq;

	dist[mStart] = 0;
	pq.push({ 0, mStart });

	while (!pq.empty()) {
		int w = pq.top().w;
		int now = pq.top().node;
		pq.pop();
		if (dist[now] < w) continue;
		if (now != mStart && now != avoid && hotel_brand[now] == target) return S(w, now);
		for (auto& edge : edges[now]) {
			int nw = w + edge.second;
			int next = edge.first;
			if (dist[next] > nw) {
				dist[next] = nw;
				pq.push({ nw,next });
			}
		}
	}
	return S(-1, -1);
}

void init(int N, int mBrands[])
{
	n = N;
	for (int i = 0; i < N; i++) {
		edges[i].clear();
	}
	for (int i = 0; i < 50; i++) {
		brand_hotel[i].clear();
	}

	for (int i = 0; i < N; i++) {
		brand_hotel[mBrands[i]].push_back(i);
		hotel_brand[i] = mBrands[i];
	}
}

void connect(int mHotelA, int mHotelB, int mDistance)
{
	edges[mHotelA].push_back({ mHotelB, mDistance });
	edges[mHotelB].push_back({ mHotelA, mDistance });
}

int merge(int mHotelA, int mHotelB)
{
	int mBrandA = hotel_brand[mHotelA];
	int mBrandB = hotel_brand[mHotelB];

	if (mBrandA == mBrandB) {
		return brand_hotel[mBrandA].size();
	}

	for (int hotel : brand_hotel[mBrandB]) {
		hotel_brand[hotel] = mBrandA;
		brand_hotel[mBrandA].push_back(hotel);
	}
	brand_hotel[mBrandB].clear();
	//printf("%d\n", brand_hotel[mBrandA].size());
	return brand_hotel[mBrandA].size();
}

int move(int mStart, int mBrandA, int mBrandB)
{
	S s1 = dijkstra(mStart, mBrandA, -1);
	S s2 = dijkstra(mStart, mBrandB, s1.node);
	//printf("%d %d\n", s1.w, s2.w);
	return s1.w + s2.w;
}
