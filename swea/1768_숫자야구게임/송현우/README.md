## 요약
- swea B형 제출 환경 연습을 위한 문제
- sample_input.txt 입출력 테스트
- main.cpp 분석 및 solution.cpp 구현
- 문제 유형 : 구현, 수학

## 풀이 과정
1. 4중 for 루프를 사용하여, 중복되지 않는 4자리 숫자로 구성된 모든 순열(총 5040개)을 생성하고 candidates 배열에 저장
2. query가 반환한 Result(strike, ball 값)를 기준으로 candidates 배열 전체를 순회
3. 각 후보에 대해 check(current_guess, candidate) 함수를 실행하여 가상의 Result 값을 계산하고, 이 값이 query의 실제 반환 값과 일치하는 후보만 next_candidates 배열에 복사
4. 정답을 찾으면 (S:4) 종료

### 사고 과정
최악의 경우 = S:0, B:0 -> 6P4 -> 360개 <br/>
즉, 90%가량 후보에서 지울 수 있다 <br/>
남은 후보에서 과정을 반복하면 빠르게 정답을 도출할 수 있다 <br/> <br/>
다만, 최적화할 수 있는 방법이 두 가지 더 남아있다 <br/>
(1) 과거에 후보를 도출했던 과정을 활용하기 <br/>
(2) strike & ball에 따른 최적화(ex. S:4 이면 4!안에 정답을 반드시 도출할 수 있다) <br/>
