package mid;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class LMW20221200_mid2 {

    Scanner sc = new Scanner(System.in);
    Random random = new Random();
    String[] rps = {"가위", "바위", "보"}; // 가위 0, 바위 1, 보 2
    String[] mjb = {"묵", "찌", "빠"}; // 묵 0, 찌 1, 빠 2
    boolean Turn;
    int userScore = 0;
    int computerScore = 0;
    int round = 1;
    int userChoice;
    int computerChoice;

    public int userInput() {

        while (true) {
            String user = sc.nextLine();

            userChoice = -1;

            switch (user) {
                case "가위":
                case "묵":
                case "0":
                    userChoice = 0;
                    break;

                case "바위":
                case "찌":
                case "1":
                    userChoice = 1;
                    break;

                case "보":
                case "빠":
                case "2":
                    userChoice = 2;
                    break;

                default:
                    System.out.println("-".repeat(20) + "\n0 ~ 2 / 가위 ~ 보 / 묵 ~ 빠 입력.");
                    continue;
            }

            break;
        }
        return userChoice;
    }

    public void playGame() {

        System.out.println("가위바위보를 시작합니다.");
        while (true) {
            try {
                int computer = random.nextInt(3);
//                System.out.println(computer);
                System.out.print(">>> 0 ~ 2 또는 가위 ~ 보 입력 : ");
                int user = userInput();

                System.out.println(">>> 나 : " + rps[user] + " | 컴퓨터 : " + rps[computer]);
                if (user == computer) {
                    System.out.println("비겼습니다.\n");
                    continue;
                } else if ((user == 0 && computer == 1) || (user == 1 && computer == 2) || (user == 2 && computer == 0)) {
                    //     user : 가위 || computer : 바위    user : 바위 || computer : 보      user : 보 || computer : 가위
                    System.out.println("졌습니다.\n");
                    Turn = false;
                } else {
                    System.out.println("이겼습니다.\n");
                    Turn = true;
                }

            } catch (InputMismatchException e) { // 다른 타입을 입력했을 때 예외 처리
                System.out.println(">>> 숫자를 입력해 주세요\n" + "-".repeat(20));
            } catch (IndexOutOfBoundsException e) {
                System.out.println(">>> 0 ~ 2 사이의 값을 입력해주세요\n" + "-".repeat(20));
            }
            break;
        }
        gameStart();
    }

    public void gameStart() {

        while (true) {
            System.out.println("===== ROUND " + round + " =====");

            switchTurn(Turn);

            if (Turn) {
                computerChoice = (int) (Math.random() * 3);
//                System.out.println(computerChoice);
                System.out.print("묵(0), 찌(1), 빠(2) 중 하나를 선택하세요: ");
                userChoice = userInput();
                System.out.println(">>> 나 : " + mjb[userChoice] + " | 컴퓨터 : " + mjb[computerChoice]);
            } else {
                computerChoice = (int) (Math.random() * 3);
//                System.out.println(computerChoice);
                System.out.println("컴퓨터가 입력을 기다립니다.");
                userChoice = userInput();
                System.out.println(">>> 나 : " + mjb[userChoice] + " | 컴퓨터 : " + mjb[computerChoice]);
            }

            boolean userWin = checkWin(Turn, userChoice, computerChoice);

            if (userWin) {
                if (Turn && userChoice == computerChoice) {
                    System.out.println("사용자가 이겼습니다!");
                    userScore++;
                }
            } else {
                if (!Turn && computerChoice == userChoice) {
                    System.out.println("컴퓨터가 이겼습니다!");
                    computerScore++;
                }
            }

            System.out.println("현재 스코어 - 사용자: " + userScore + ", 컴퓨터: " + computerScore);
            System.out.println();

            Turn = !Turn;

            if ((userScore == 3) || (computerScore == 3)) {
                if (userScore > computerScore) {
                    System.out.println("최종 스코어 - 사용자: " + userScore + ", 컴퓨터: " + computerScore);
                    System.out.println("사용자가 최종 승리했습니다!");
                } else if (userScore < computerScore) {
                    System.out.println("최종 스코어 - 사용자: " + userScore + ", 컴퓨터: " + computerScore);
                    System.out.println("컴퓨터가 최종 승리했습니다!");
                }
                break;
            }
            round++;
        }
    }

    public static void switchTurn(boolean Turn) {
        if (Turn) {
            System.out.println("=== 사용자 턴 ===");
        } else {
            System.out.println("=== 컴퓨터 턴 ===");
        }
    }

    public boolean checkWin(boolean Turn, int userChoice, int computerChoice) {

        if (Turn) {
            if (userChoice == computerChoice) {
                return true;
            }
        } else {
            if (computerChoice == userChoice) {
                return false;
            }
        }
        return Turn;
    }

    public static void main(String[] args) {

        LMW20221200_mid2 lmw20221200Mid = new LMW20221200_mid2();
        lmw20221200Mid.playGame();

    }
}
