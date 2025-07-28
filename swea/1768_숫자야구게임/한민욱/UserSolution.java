class UserSolution {
    public final static int N = 4;

    static class Node {
        int[] digits;

        public Node(int one, int two, int three, int four) {
            this.digits = new int[N];
            this.digits[0] = one;
            this.digits[1] = two;
            this.digits[2] = three;
            this.digits[3] = four;
        }
    }

    private Node[] list;
    private int listSize;
    private int[] visited;
    private Node[] nextList;
    
    public void doUserImplementation(int[] guess) {
        list = new Node[5040];
        visited = new int[10];
        listSize = 0;
        nextList = new Node[5040];

        dfs(0, new int[N]);

        while (true) {
            Node currentNode = list[0]; 
            
            for (int i = 0; i < N; i++) {
                guess[i] = currentNode.digits[i];
            }

            Solution.Result actualResult = Solution.query(guess);
            
            if (actualResult.strike == N) {
                break;
            }

            int nextSize = 0; 
            for (int i = 0; i < listSize; i++) {
                Node candidateNode = list[i];
                Solution.Result simulatedResult = compare(currentNode, candidateNode);
                
                if (actualResult.strike == simulatedResult.strike && actualResult.ball == simulatedResult.ball) {
                    nextList[nextSize++] = candidateNode;
                }
            }
            
            for (int i = 0; i < nextSize; i++) {
                list[i] = nextList[i];
            }
            listSize = nextSize;
        }
    }

    public static Solution.Result compare(Node guessNode, Node candidateNode) {
        int strike = 0;
        int ball = 0;

        int[] candidateCounts = new int[10];
        for (int digit : candidateNode.digits) {
            candidateCounts[digit]++;
        }

        for (int i = 0; i < N; i++) {
            if (guessNode.digits[i] == candidateNode.digits[i]) {
                strike++;
            } else {
                if (candidateCounts[guessNode.digits[i]] > 0) {
                    ball++;
                }
            }
        }

        Solution.Result result = new Solution.Result();
        result.strike = strike;
        result.ball = ball;
        return result;
    }

    public  void dfs(int count, int[] value) {
        if (count == N) {
            list[listSize++] = new Node(value[0], value[1], value[2], value[3]);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if (visited[i] == 1) continue;
            
            visited[i] = 1; 
            value[count] = i; 
            dfs(count + 1, value); 
            visited[i] = 0; 
        }
    }
}