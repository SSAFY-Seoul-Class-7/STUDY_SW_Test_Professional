#define N 4
 
struct Result {
    int strike;
    int ball;
};
 
extern Result query(int guess[]);
 
Result check(const int guess1[N], const int guess2[N]) {
    Result res = { 0, 0 };
    int guess1_digits[10] = { 0, };
 
    for (int i = 0; i < N; i++) {
        guess1_digits[guess1[i]]++;
    }
 
    for (int i = 0; i < N; i++) {
        if (guess1[i] == guess2[i]) {
            res.strike++;
        }
        else if (guess1_digits[guess2[i]] > 0) {
            res.ball++;
        }
    }
    return res;
}
 
void doUserImplementation(int guess[]) {
    // 가능한 모든 후보 생성 (0~9 중복 없는 4자리 수)
    // 재귀 백트래킹이 더 좋은 방법
    int candidates[5040][N];
    int candidate_count = 0;
 
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            if (i == j) continue;
            for (int k = 0; k < 10; k++) {
                if (k == i || k == j) continue;
                for (int l = 0; l < 10; l++) {
                    if (l == i || l == j || l == k) continue;
                    candidates[candidate_count][0] = i;
                    candidates[candidate_count][1] = j;
                    candidates[candidate_count][2] = k;
                    candidates[candidate_count][3] = l;
                    candidate_count++;
                }
            }
        }
    }
 
    while (true) {
        int current_guess[N];
        for (int i = 0; i < N; i++) {
            current_guess[i] = candidates[0][i];
        }
 
        Result result = query(current_guess);
 
        // 정답을 맞힌 경우
        if (result.strike == N) {
            for (int i = 0; i < N; i++) {
                guess[i] = current_guess[i];
            }
            return;
        }
 
        // 후보 필터링
        int next_candidate_count = 0;
        int next_candidates[5040][N];
 
        for (int i = 0; i < candidate_count; i++) {
            // 추측한 값과 후보를 비교
            Result temp_result = check(current_guess, candidates[i]);
 
            // 동일한 값을 가지는 후보만 필터링
            if (temp_result.strike == result.strike && temp_result.ball == result.ball) {
                for (int j = 0; j < N; j++) {
                    next_candidates[next_candidate_count][j] = candidates[i][j];
                }
                next_candidate_count++;
            }
        }
 
        // 필터링된 후보로 교체
        candidate_count = next_candidate_count;
        for (int i = 0; i < candidate_count; i++) {
            for (int j = 0; j < N; j++) {
                candidates[i][j] = next_candidates[i][j];
            }
        }
    }
}
