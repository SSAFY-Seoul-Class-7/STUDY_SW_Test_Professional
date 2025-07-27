class UserSolution {
    public final static int N = 4;

    public void doUserImplementation(int guess[]) {
        int[][] candidates = new int[5040][N];
        for (int count = 0; count < 5040; count++) {
            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 10; ++j) {
                    if (j != i) {
                        for (int k = 0; k < 10; ++k) {
                            if (k != i && k != j) {
                                for (int l = 0; l < 10; ++l) {
                                    if (l != i && l != j && l != k) {
                                        candidates[count][0] = i;
                                        candidates[count][1] = j;
                                        candidates[count][2] = k;
                                        candidates[count][3] = l;
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int remain = 5040; // 남은 후보 수
        int[] check = new int[N];
        while (true) {
            check = candidates[0];

            Solution.Result result = Solution.query(check);

            if (result.strike == 4) {
                for (int i = 0; i < N; i++) {
                    guess[i] = check[i];
                }
                return;
            }
            int idx = 0;
            for (int i = 1; i < remain; i++) {
                if (match(check, candidates[i], result.strike, result.ball)) {
                    candidates[idx++] = candidates[i];
                }
            }
            remain = idx;
        }
    }


    private boolean match(int[] check, int[] candidates, int s, int b) {
        int strike = 0, ball = 0;
        for (int i = 0; i < N; ++i) {
            if (check[i] == candidates[i]) {
                strike++;
            } else {
                for (int j = 0; j < N; ++j) {
                    if (i != j && check[i] == candidates[j]) {
                        ball++;
                    }
                }
            }
        }
        return strike == s && ball == b;
    }
}
