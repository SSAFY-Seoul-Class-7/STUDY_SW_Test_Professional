
// 10 :05 + 15분 

// 네트워크에 루트노드 3개로 외부와 통신하는 네트워크가 있다. 

// 전송시간을 알아보기 위해서 루트 노드간의 최단전송시간을 측정하려고 한다. 
// 루트노드 3개와 300개의 소규모 그룹으로 이루어져있다. 

// 대표노드 3개와 말단노드 27개로 이루어져 있고, 대표노드 3개는 루트노드나 다른 소규모 그룹의 대표노드와 연결이 가능하다. 
// 각각의 라인에는 전송시간이 있다. 
// 그 전송시간의 합이 가장 작은시간. 

// 크게 3개의 대표노드가있고. 300개의 소규모 그룹이 있음.

// 각각 소규모에는 3개의 대표노드 27개의 일반노드가 있음. 
// 각각의 노드는 노드번호를 가지고 
// 루트노드의 노드번호는 1, 2, 3
// 소규모 그룹은 101, 102, 103
// 소규모 그룹의 대표노드는 ?01, ?02, ?03

// 소규모 그룹마다 최대 한번만 연결된다.
// A와 B의 연결은 최대 한번 연결된다.
// 그 그룹마다 접근할 수 있는 길은 1개인가봄. 이걸 잘 설정해야 최단거리가 나올듯?
// 새로운 라인이 추가되거나 기존에 있던 라인이 없어지면서 전송시간이 달라질 수 있다. 

// 네트워크 초기화 이후 노드의 추가나 제거는 없고, 라인은 추가될 수 있음. 
// 라인의 전송시간이 주어질 때, 루트 노드간의 최단 전송 시간을 알아내라. 
// 라인이 없을 경우 아무것도 하지 않는다. 
// 200번. 200번. 보통 리스트로 넣긴하는데. 리스트가 끊어지는경우. 새롭게 초기화?
// 추가되고, 제거되면 나머지 최단경로도 바뀌고, 리스트도 바뀌나? 안바뀌지않나? 
// 그냥 추가만 하면? list 에 추가, 그리고 그거에 연결되어있는 리스트 추가하면 되나? 
// 포함되어있는지 확인하면서 추가해야할듯. 그 index 는 한번 돌아야함. 
// 제거되면 ? 그냥 끊기만하면 되네.
// 추가되면? 한번 돌아야함. 
// 뭐야 그냥 추가하면 되나? 700번을 해야하는건가? 
// 만약 add, remove 가 일어나지 않았다면 그대로 사용. 일어났다면 한번 호출. 

// 찾으면 바로 return 

// 시간복잡도 

// 소규모 그룹 갯수 300개.
// 그럼 총 30 * 300 + 3 = 90003 개의 노드가 있음.
// (90000 + 30000) * 약 17 = 12만 * 20 => 넉넉하게 40만 * 700 = 2억1천
// 4초니까 가능할듯? 
// 3만개가 있고 라인이 1000까지 있을 수 있으니 300_000_00 int로 가능
// 노드의 개수는 90003개. 노드의 개수 3만개. 

// 그러면 다익스트라 1번 도는데 걸리는 시간은 9만 + 3만  log 9만 
// 12만 * 17 약 30만이라 치고 이걸 700번 하면 2억 1천만
// 제한시간 4초니까 가능할 것 같은데.


import java.util.*;

class UserSolution {
	
	public class Node{
		int index;
		int dist;
		public Node(int index, int dist) {
			this.index = index;
			this.dist = dist;
		}
	}
	
	public class Edge{
		int index;
		int weight;
		public Edge(int index, int weight) {
			this.index = index;
			this.weight = weight;
		}
	}
	public ArrayList<Edge>[] list;
	public int[] dist;
	public int NodeCount =0;
	
	public void initDijkstra() {
		dist = new int[NodeCount * 100 + 31];	
		Arrays.fill(dist, Integer.MAX_VALUE);
	}
	
	
	public void init(int N, int K, int mNodeA[], int mNodeB[], int mTime[])
	{
		NodeCount = N;
		
		initDijkstra();
		// 그래프 초기화 
		list = new ArrayList[N * 100 + 31];
		for(int i=1;i<N * 100 + 31;i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0;i<K;i++) {
			int from = mNodeA[i];
			int to = mNodeB[i];
			int weight = mTime[i];
			
			list[from].add(new Edge(to, weight));
			list[to].add(new Edge(from, weight));
		}
	}

	public void addLine(int mNodeA, int mNodeB, int mTime)
	{
		// 하나만 추가
		list[mNodeA].add(new Edge(mNodeB, mTime));
		list[mNodeB].add(new Edge(mNodeA, mTime));
	}

	public void removeLine(int mNodeA, int mNodeB)
	{	// 하나만 제거
	
		list[mNodeA].removeIf(e -> e.index == mNodeB);
		list[mNodeB].removeIf(e -> e.index == mNodeA);
		
	}

	public int checkTime(int mNodeA, int mNodeB)
	{
		
		initDijkstra();
		PriorityQueue<Node> queue = new PriorityQueue<>(
				(o1, o2) -> Integer.compare(o1.dist, o2.dist)
		);
		
		queue.add(new Node(mNodeA, 0));
		dist[mNodeA] = 0;
		
		while(!queue.isEmpty()) {
			
			Node node = queue.poll();
			
			if(dist[node.index] != node.dist) continue;
			if(node.index == mNodeB) {
				return node.dist;
			}
			
			for(Edge e : list[node.index]) {
				
				if(dist[e.index] > dist[node.index] + e.weight) {
					dist[e.index] = dist[node.index] + e.weight;
					queue.add(new Node(e.index, dist[e.index]));
				}
			}
		}
		
		return dist[mNodeB];
	}
}
