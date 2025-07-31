문제 유형 : 다익스트라, Union-Find

## 풀이 과정

---

다익스트라의 호출 횟수를 줄이기 위해 다방면으로 노력했습니다

오래 걸리는 test case의 경우 다익스트라를 300회 정도 호출하는 걸 확인할 수 있었습니다

### 시작점 별 캐싱

unordered_map<int, vector<int>> dist_cache;

dist[]를 저장하는 캐시를 활용해 시작점을 캐시했습니다.

하지만 시작점이 5,000개 가량이라 캐시 히트가 매우 저조했고 큰 성능개선을 이룰 수 없었습니다.

### 다이얼 알고리즘

가중치가 작을 때 사용할 수 있는 특수한 알고리즘입니다 (0-1 BFS의 확장판)

```cpp
void dijkstra(int mStart) {
	dist[mStart] = 0;
	q[0].push(mStart);
	int cnt = 1;

	while (cnt) {
		while (q[p].empty()) ++p %= 11;

		int now = q[p].front();
		q[p].pop();
		cnt--;
		if (visited[now]) continue;
		visited[now] = true;

		for (auto& edge : edges[now]) {
			int nd = dist[now] + edge.second;
			int next = edge.first;
			if (dist[next] > nd) {
				dist[next] = nd;
				q[(p + edge.second) % 11].push(next);
				cnt++;
			}
		}
	}
}
```

위와 같은 코드로 성능이 꽤나 개선되었으나 (8개 테스트 통과 → 13개 테스트 통과)

여전이 다익스트라 호출 횟수가 많아 통과하지 못했습니다

## 해결 방법

---

결국, 다익스트라 호출 횟수를 줄일 수 없다면 다익스트라 자체를 최적화해야합니다.

문제에서 결국 방문할 브랜드의 가장 가까운 점을 탐색하면 되기 때문에, 방문한 호텔이 원하는 브랜드라면 탐색을 종료하면 됩니다.

제 풀이에서는 Union-Find를 적용하지 않았지만, merge에서 Union-Find를 적용하면 합병 과정을 더 최적화할 수 있습니다!
