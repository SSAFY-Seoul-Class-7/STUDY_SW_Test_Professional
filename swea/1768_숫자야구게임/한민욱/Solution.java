import java.util.Scanner;

class Solution {
    private final static int N = 4;
    private final static int MAX_QUERYCOUNT = 1000000;
    private final static int LIMIT_QUERY = 2520;

    private static int[] digits = new int[N];
    private static int[] digits_c = new int[10];
    private static int  querycount;

    static class Result {
        int strike, ball;
    }

    public static Result query(int[] guess) {
        Result r = new Result();
        if (querycount >= MAX_QUERYCOUNT) {
            r.strike = r.ball = -1;
            return r;
        }
        querycount++;
        int[] seen = new int[10];
        for (int g : guess) {
            if (g < 0 || g > 9 || seen[g]++ > 0) {
                r.strike = r.ball = -1;
                return r;
            }
        }
        r.strike = r.ball = 0;
        for (int i = 0; i < N; i++) {
            if (guess[i] == digits[i])          r.strike++;
            else if (digits_c[guess[i]] > 0)    r.ball++;
        }
        return r;
    }

    private static void initialize(Scanner sc) {
        for (int i = 0; i < 10; i++) digits_c[i] = 0;
        String s;
        do {
            s = sc.next();
        } while (s.length() == 0 || !Character.isDigit(s.charAt(0)));
        for (int i = 0; i < N; i++) {
            digits[i] = s.charAt(i) - '0';
            digits_c[digits[i]]++;
        }
        querycount = 0;
    }

    private static boolean check(int[] g) {
        for (int i = 0; i < N; i++)
            if (g[i] != digits[i]) return false;
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int totalScore = 0, totalQueries = 0;
        UserSolution user = new UserSolution();

        for (int tc = 1; tc <= T; tc++) {
            initialize(sc);
            int[] guess = new int[N];
            user.doUserImplementation(guess);
            if (!check(guess)) querycount = MAX_QUERYCOUNT;
            if (querycount <= LIMIT_QUERY) totalScore++;
            System.out.printf("#%d %d\n", tc, querycount);
            totalQueries += querycount;
        }
        if (totalQueries > MAX_QUERYCOUNT) totalQueries = MAX_QUERYCOUNT;
        System.out.printf("total score = %d\ntotal query = %d\n",
                          totalScore * 100 / T, totalQueries);
        sc.close();
    }
}
