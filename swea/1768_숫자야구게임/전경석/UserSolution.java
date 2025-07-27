class UserSolution {
    public final static int N = 4;

    public void doUserImplementation(int guess[]) {
        boolean[][][][] visited = new boolean[10][10][10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        // 중복되는 숫자 존재 여부 체크
                        if (i == j || i == k || i == l || j == k || j ==l || k == l) visited[i][j][k][l] = true;
                        if (visited[i][j][k][l]) continue;

                        System.out.println(3);

                        Solution.Result result = Solution.query(new int[] {i, j, k, l});
                        if (result.strike == 4) {
                            guess[0] = i;
                            guess[1] = j;
                            guess[2] = k;
                            guess[3] = l;
                            return;
                        }

                        for (int i2 = 0; i2 < 10; i2++) {
                            for (int j2 = 0; j2 < 10; j2++) {
                                for (int k2 = 0; k2 < 10; k2++) {
                                    for (int l2 = 0; l2 < 10; l2++) {
                                        int strike = 0;
                                        int ball = 0;
                                        if (i ==i2) strike++;
                                        if (j == j2) strike++;
                                        if (k == k2) strike++;
                                        if (l == l2) strike++;

                                        if (i == j2 || i == k2 || i == l2) ball++;
                                        if (j == i2 || j ==k2 || j == l2) ball++;
                                        if (k == i2 || k == j2 || k == l2) ball++;
                                        if (l == i2 || l == j2 || l == k2) ball++;

                                        if (strike != result.strike || ball != result.ball) visited[i2][j2][k2][l2] = true;

                                    }
                                }
                            }
                        }


                    }
                }
            }
        }

    }
}
