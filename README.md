
## 🎯 기능 요구 사항

블랙잭 게임을 변형한 프로그램을 구현한다. 블랙잭 게임은 딜러와 플레이어 중 카드의 합이 21 또는 21에 가장 가까운 숫자를 가지는 쪽이 이기는 게임이다.


```java
- 카드의 숫자 계산은 카드 숫자를 기본으로 한다
  예외로 Ace는 1 또는 11로 계산할 수 있으며,
  King, Queen, Jack은 각각 10으로 계산한다.
- 게임을 시작하면 플레이어는 두 장의 카드를 지급 받으며, 
  두 장의 카드 숫자를 합쳐 21을 초과하지 않으면서 21에 가깝게 만들면 이긴다. 
  21을 넘지 않을 경우 원한다면 얼마든지 카드를 계속 뽑을 수 있다.
- 딜러는 처음에 받은 2장의 합계가 16이하이면 반드시 1장의 카드를 추가로 받아야 하고, 
  17점 이상이면 추가로 받을 수 없다.
- 게임을 완료한 후 각 플레이어별로 승패를 출력한다.
```

### 실행 결과 예시

```java
게임에 참여할 사람의 이름을 입력하세요.
pobi,jason

딜러와 pobi, jason에게 2장을 나누었습니다.
딜러: 3다이아몬드
pobi카드: 2하트, 8스페이드
jason카드: 7클로버, K스페이드

pobi는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
y
pobi카드: 2하트, 8스페이드, A클로버
pobi는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
n
jason은 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
n
jason카드: 7클로버, K스페이드

딜러는 16이하라 한장의 카드를 더 받았습니다.

딜러 카드: 3다이아몬드, 9클로버, 8다이아몬드 - 결과: 20
pobi카드: 2하트, 8스페이드, A클로버 - 결과: 21
jason카드: 7클로버, K스페이드 - 결과: 17

## 최종 승패
딜러: 1승 1패
pobi: 승
jason: 패
```
### 순서
1. 게이머 이름 입력
2. 딜러, 게이머 이름, 기본 패 출력 (딜러 및 게이머 정보 필요)
3. 첫번째 순서 게이머에게 질문 출력 (첫번째 순서 게이머 필요)
4. y 이면 첫번째 순서 게이머 패에 카드 추가 후 정보 출력 (해당 게이머 정보 필요)
5. n 이면 다음 순서 게이머로 넘어감
6. 다음 순서 게이머로 넘어가고 4,5 반복
7. 딜러 조건 판단 후 종료 
8. 모든 플레이어 정보와 결과 출력 (Map 형태 - Map<Player, Result>)
9. 승패 출력

## 필요한 객체의 상태와 속성

### 플레이어
- **딜러** : 카드 상태 - 카드 뽑기 가능 여부, 카드 뽑기, 카드 보여주기
- **게이머** : 카드 상태 - 카드 뽑기 가능 여부, 카드 뽑기, 카드 보여주기
- **게이머들** : 게이머 리스트, 현재 게이머 판단 - 현재 게이머 가져오기, 현재 게이머 카드 추가, 다음 게이머로 넘기기

### 카드
- **카드** : 숫자, 무늬
- **카드 덱** : 트럼프 카드 52 장 - 기본 카드 제공(2장), 추가 뽑기 카드 제공(1장)
- **카드 패** : 플레이어가 가진 패 - 카드패의 점수 합, 카드 추가, 카드 패 보여주기

### 카드 상태
- **Hit 상태** : 카드 패 - 카드 뽑기 가능여부가 true 인 상태(합이 21 미만), 카드 뽑기
- **Bust 상태** : 카드 패 - 카드 뽑기 가능 여부가 false 인 상태(합이 21 초과), 카드 뽑기
- **Stand 상태** : 카드 패 - 카드 뽑기를 멈춘 상태, 카드 뽑기
- **Essential 상태** : 카드 패 - 카드 뽑기가 필수인 상태 (합이 16이하), 카드 뽑기
- **Finish 상태** : 카드 패 - 카드 뽑기가 끝난 상태 (합이 16 초과), 카드 뽑기
- **BlackJack 상태** : 카드 패 - 카드 뽑기가 필요없는 상태 (합이 21)
