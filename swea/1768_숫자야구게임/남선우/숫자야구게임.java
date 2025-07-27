import java.util.Scanner;

class Solution {
    private final static int N              = 4;  
    private final static int MAX_QUERYCOUNT = 1000000;

    private static int digits[] = new int[N]; 
    private static int digits_c[] = new int[10];

    private static int T;                          

	private static int querycount;
	
    // the value of limit_query will be changed in evaluation
	private final static int limit_query = 2520;

    static class Result {
        public int strike;                                
        public int ball;
	}
	
	private static boolean isValid(int guess[]) {
		int guess_c[] = new int[10];
		
		for (int count = 0; count < 10; ++count) guess_c[count] = 0;
		for (int idx = 0; idx < N; ++idx) {
			if (guess[idx] < 0 || guess[idx] >= 10 || guess_c[guess[idx]] > 0) return false;
			guess_c[guess[idx]]++;
		}
		return true;
	}
	
	// API : return a result for comparison with digits[] and guess[]
    public static Result query(int guess[]) {
		Result result = new Result();
		
		if (querycount >= MAX_QUERYCOUNT) {
			result.strike = -1;
			result.ball = -1;
			return result;
		}
		
		querycount++;
		
		if (!isValid(guess)) {
			result.strike = -1;
			result.ball = -1;
			return result;
		}
		
		result.strike = 0;
		result.ball = 0;

		for (int idx = 0; idx < N; ++idx)
			if (guess[idx] == digits[idx])
				result.strike++;
			else if (digits_c[guess[idx]] > 0)
				result.ball++;
		
		return result;
	}

	private static void initialize(Scanner sc) {
		for (int count = 0; count < 10; ++count) digits_c[count] = 0;
		
		String input;
		do input = sc.next(); while(input.charAt(0) < '0' || input.charAt(0) > '9');

		for (int idx = 0; idx < N; ++idx) {
			digits[idx] = input.charAt(idx) - '0';
			digits_c[digits[idx]]++;
		}
		
		querycount = 0;
	}

	private static boolean check(int guess[]) {
		for (int idx = 0; idx < N; ++idx)
			if (guess[idx] != digits[idx]) return false;
		return true;
	}
	
	public static void main(String[] args) throws Exception
	{
		
		int total_score = 0;
		int total_querycount = 0;
		
		//System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		Scanner sc = new Scanner(System.in);
		T = sc.nextInt();

		숫자야구게임 usersolution = new 숫자야구게임();
		for (int testcase = 1; testcase <= T; ++testcase) {
			initialize(sc);

			int guess[] = new int[N];
            usersolution.doUserImplementation(guess);

			if (!check(guess)) querycount = MAX_QUERYCOUNT;
			if (querycount <= limit_query) total_score++;
			System.out.printf("#%d %d\n", testcase, querycount);
			total_querycount += querycount;
		}
		if (total_querycount > MAX_QUERYCOUNT) total_querycount = MAX_QUERYCOUNT;
		System.out.printf("total score = %d\ntotal query = %d\n", total_score * 100 / T, total_querycount);
		sc.close();
	}
}

// 바꿔볼 것 안바꿔볼것으로 나눠진다.
// 바꿔본 부분이 원래보다 더 많이 맞으면 그 부분이 정답
// 원래보다 더 안좋아지면 안바꿔본 부분이 정답
// 재귀 혹은 반복적으로 풀어보기

// 처음 생각 : 맞춰가는 것. 비트마스킹 같은걸로
// 후 생긱 : 정답 판정으로 나온 숫자와 관련된 애들을 제거해주기

class 숫자야구게임 {
    public final static int N = 4;
    public static boolean[] arr;

    public void init(){
        arr = new boolean[9877];
        for(int i = 0; i<9877;i++){
            if(i < 123){
                arr[i] = false;
            }else{
                int first = i / 1000;
                int second = (i % 1000) / 100;
                int third = (i % 100) / 10;
                int fourth = i % 10;

                if(first!=second && first != third && first != fourth && second != third && second != fourth && third != fourth){
                    arr[i] = true;
                }
                else{
                    arr[i] = false;
                }
            }
        }
    }

    public void doUserImplementation(int guess[]) {
        init();
        while (true) { 
            int i = 123;
            for(; i<9877; i++){
                if(arr[i]){
                    break;
                }
            }
            guess[0] =  i / 1000;
            guess[1] = (i % 1000) / 100;
            guess[2] = (i % 100) / 10;
            guess[3] = i % 10;
            
            Solution.Result result = Solution.query(guess);
            if(result.strike == 4){
                break;
            }

            for(int j = 123; j<9877; j++){
                if(arr[j]){
                    int[] guess_digits = new int[N];
                    int guess_digits_c[] = new int[10];

                    guess_digits[0] = j / 1000;
                    guess_digits[1] = (j % 1000) / 100;
                    guess_digits[2] = (j % 100) / 10;
                    guess_digits[3] = j % 10;

                    for (int idx = 0; idx < N; ++idx) {
                        guess_digits_c[guess_digits[idx]]++;
                    }

                    int strike = 0;
                    int ball = 0;

                    for (int idx = 0; idx < N; ++idx){
                        if (guess[idx] == guess_digits[idx]){
                            strike++;
                        }else if (guess_digits_c[guess[idx]] > 0){
                            ball++;
                        }
                            
                    }
                    if(strike == result.strike && ball == result.ball){
                        continue;
                    }else{
                        arr[j] = false;
                    }
                }
            }

        }
    }
}
