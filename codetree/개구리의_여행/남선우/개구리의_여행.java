package codetree.개구리의_여행.남선우;
/**
9:15 ~ 11:21
- 호수 : N * N 
- 돌
    - 돌이 좌표로 주어짐
    - 안전한 돌, 미끄러운 돌, 천적이 사는 돌
    - . / S / #

- 시작 위치에서 도착 위치까지 가는 것이 목표
- 초기 점프력은 1

- 점프
    - 상하좌우 이동
    - 현재 점프력만큼 칸 이동
    - 돌이 없으면 이동 불가
    - 미끄러운 돌이 도착한 위치에 있으면 이동 불가
    - 도착한 위치 + 지나치는 경로에 천적이 사는 돌 있는 경우도 이동 불가
    - 1만큼의 시간이 소요
- 점프력 증가
    - 점프력 1 증가
    - 최대 증가는 5까지
    - 증가 후 점프력을 K라고 할 때 k^2 만큼의 시간 소요
- 점프력 감소
    - 기존 점프력이 k인 경우 1,2, --- k-1 중 하나의 점프력 갖도록 가능
    - 감소에는 1만큼의 시간이 소요

총 Q번의 여행 계획을 세움. 최대한 짧은 시간에 여행. 

출력 : 
최소 시간 출력
불가능한 계획일 경우 -1 출력

맵 최대 2500
Q 1000개

# 엣지 대충 15000개
# 3000개 * 5 = 15,000

# 노드 대충 12500개

# 
# 1.5 * 10^4 * 10^3 * 1.5 * 10 = 1.5 * 1.5 * 10^8
# 엣지와 노드가 많아진 다익스트라

*/
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static class TripInfo{
        int start_r;
        int start_c;
        int end_r;
        int end_c;
        TripInfo(int sr, int sc, int er, int ec){
            this.start_r = sr;
            this.start_c = sc;
            this.end_r = er;
            this.end_c = ec;
        }
    }

    public static class Location implements Comparable<Location>{
        int r;
        int c;
        int distance;
        int jump;
        Location(int r, int c, int distance, int now_jump){
            this.r = r;
            this.c = c;
            this.distance = distance;
            this.jump = now_jump;
        }

        public int compareTo(Location b){
            return this.distance - b.distance;
        }
    }

    public static boolean in_range(int r, int c, int N){
        return 0<=r && r<N && 0<=c && c < N;
    }

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());
        String[][] map = new String[N][N];

        for(int i=0;i<N;i++){
            String[] str = br.readLine().split("");
            for(int j=0;j<N;j++){
                map[i][j] = str[j];
            }
        }
        int Q = Integer.parseInt(br.readLine());
        TripInfo[] tripInfo = new TripInfo[Q];

        for(int i=0; i<Q;i++){
            String[] str = br.readLine().split(" ");
            tripInfo[i] = new TripInfo(Integer.parseInt(str[0])-1,Integer.parseInt(str[1])-1,Integer.parseInt(str[2])-1,Integer.parseInt(str[3])-1);
        }

        // 코드 구현

        int[] dr = {0,0,1,-1};
        int[] dc = {1,-1,0,0};
        int MAX = Integer.MAX_VALUE;
        
        // dx, dy 테크니션
        // 다익스트라로 특정 위치에서 특정 위치로 가기

        for(int q=0;q<Q;q++){
            int result = MAX;
            TripInfo now_info = tripInfo[q];
            
            PriorityQueue<Location> queue = new PriorityQueue<>();
        
            int[][][] distance = new int[N][N][6];
            for(int i = 0; i<N; i++){
                for(int j = 0; j<N;j++){
                    for(int k = 0; k<6; k++){
                        distance[i][j][k] = MAX;
                    }
                }
            }

            queue.offer(new Location(now_info.start_r, now_info.start_c, 0, 1));
            distance[now_info.start_r][now_info.start_c][1] = 0;
  
            while(!queue.isEmpty()){
                Location now_loc = queue.poll();
                if(now_loc.r == now_info.end_r && now_loc.c == now_info.end_c){
                    result = Math.min(result,now_loc.distance);
                }
                if(now_loc.distance > distance[now_loc.r][now_loc.c][now_loc.jump]){
                    continue;
                }
                for(int k=0; k<4; k++){
                    for(int ns = 1; ns<=5;ns++){
                        int delay = 0;
                        if(now_loc.jump == ns){
                            delay = 0;
                        }else if(now_loc.jump > ns){
                            delay = 1;
                        }else if(now_loc.jump < ns){
                            int temp_ns = ns;
                            while(temp_ns != now_loc.jump){
                                delay += temp_ns * temp_ns;
                                temp_ns -= 1;
                            }
                        }

                        int next_r = now_loc.r + dr[k]*ns;
                        int next_c = now_loc.c + dc[k]*ns;
                        
                        if(!in_range(next_r, next_c, N)){
                            continue;
                        }

                        if(map[next_r][next_c].equals("S")){
                            continue;
                        }

                        if(map[next_r][next_c].equals("#")){
                            break;
                        }
    
                        int next_cost = now_loc.distance + delay + 1;

                        if(next_cost < distance[next_r][next_c][ns]){
                            queue.offer(new Location(next_r, next_c, next_cost, ns));
                            distance[next_r][next_c][ns] = next_cost;
                        }
            
                    }
                }
            }

            if(result == MAX){
                sb.append(-1).append("\n");
            }else{
                sb.append(result).append("\n");
            }
        }
        System.out.print(sb);
    }
}

// 이동기
// 점프력을 증가 시키는데는 K제곱
// 점프력을 감소시키는데는 1
// 점프 1

// 다익스트라로 구현할 때
// 엣지가 많아진 다익스트라
// 크기를 늘리고, 줄여가는 경로 
//  but, 중간에 천적이 있으면 안됨.

// 이후로 못가는거를 어떻게 표시할 것이냐?
    // break로 표시

// 출력
// 최소 시간 출력
// 불가능한 계획일 경우 -1 출력

// 시간이 많이 쓰인 부분 
//    - 점프 적용 안함
//    - 시간 올려주는 거를 잘못 적용함.