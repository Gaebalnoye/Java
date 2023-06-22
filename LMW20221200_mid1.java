package mid;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class LMW20221200_mid1 {

    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    String[] choices = {"가위", "바위", "보"};
    private boolean isUserBetting;
    private int userBettingAmount = 0;
    private int computerBettingAmount = 0;
    private int userScore;
    private int computerScore;
    int round = 1;

    public void playGame() {

        createFile();
        setInput();

        while (true) {

            System.out.println("===== ROUND " + round + " =====");
            System.out.println(">>> 0 ~ 2 / 가위 ~ 보 입력");

            int computerChoice = random.nextInt(3);
//            System.out.println(computerChoice); 테스트 시에 컴퓨터가 낸 값을 출력
            String userChoice = scanner.next();

            int userSelectedChoice;

            switch (userChoice) {
                case "가위":
                case "0":
                    userSelectedChoice = 0;
                    break;

                case "바위":
                case "1":
                    userSelectedChoice = 1;
                    break;

                case "보":
                case "2":
                    userSelectedChoice = 2;
                    break;

                default:
                    System.out.println(">>> 0 ~ 2 또는 가위 ~ 보 입력\n" + "-".repeat(20));
                    continue;
            }

            System.out.println(">>> 나 : " + choices[userSelectedChoice] + " | 컴퓨터 : " + choices[computerChoice]);
            if (userSelectedChoice == computerChoice) {
                System.out.println("비겼습니다.\n");
            } else if ((userSelectedChoice == 0 && computerChoice == 1) || (userSelectedChoice == 1 && computerChoice == 2) || (userSelectedChoice == 2 && computerChoice == 0)) {
                computerScore++;
                System.out.println("졌습니다.\n");
            } else {
                System.out.println("이겼습니다.\n");
                userScore++;
            }

            if ((userScore == 5) || (computerScore == 5)) {
                if (userScore > computerScore) {
                    System.out.println("최종 스코어 - 사용자: " + userScore + ", 컴퓨터: " + computerScore);
                    System.out.println("사용자가 최종 승리했습니다!");
                } else {
                    System.out.println("최종 스코어 - 사용자: " + userScore + ", 컴퓨터: " + computerScore);
                    System.out.println("컴퓨터가 최종 승리했습니다!");
                }
                break;
            }
            System.out.println("현재 스코어 - 사용자: " + userScore + ", 컴퓨터: " + computerScore);
            round++;
        }
        calculatePoints(isUserBetting, userBettingAmount, computerBettingAmount);
    }

    public void calculatePoints(boolean isUserBetting, int userBettingAmount, int computerBettingAmount) {
        int point;

        int currentPoint = readPoints();

        if (isUserBetting) {
            if (userScore > computerScore) {
                point = currentPoint + ((computerBettingAmount / userBettingAmount) * (userBettingAmount + userBettingAmount));
                System.out.println((computerBettingAmount / userBettingAmount) * (userBettingAmount + userBettingAmount) + "포인트를 얻었습니다.");
            } else {
                point = currentPoint - userBettingAmount;
                System.out.println(userBettingAmount + "포인트를 잃었습니다.");
            }
        } else {
            if (userScore > computerScore) {
                point = currentPoint + 1000;
                System.out.println("1000포인트를 얻었습니다.");
            } else {
                point = currentPoint;
            }
        }
        writePoints(point);
    }

    public void setInput() {

        int currentPoint = readPoints(); // 현재 보유 포인트 확인
        while (true) {
            System.out.print("베팅을 하시겠습니까? y / n : ");
            String input = scanner.next();

            if (input.equals("y")) {
                if (currentPoint == 0) {
                    System.out.println("보유 포인트가 없습니다.\n");
                    isUserBetting = false;

                } else {
                    System.out.println("보유중인 포인트: " + currentPoint + "p");
                    System.out.print("베팅 금액을 입력하세요: ");
                    userBettingAmount = scanner.nextInt();

                    if (userBettingAmount > currentPoint) {
                        System.out.println("보유 포인트가 부족합니다. 베팅 여부를 다시 입력받겠습니다.\n");
                        continue;
                    }

                    computerBettingAmount = userBettingAmount + (int) (Math.random() * 10000);
                    System.out.println("사용자: " + userBettingAmount + "원, 컴퓨터: " + computerBettingAmount + "원을 걸었습니다.");
                    isUserBetting = true;
                }
                break;
            } else if (input.equals("n")) {
                isUserBetting = false;
                break;
            } else {
                System.out.println(">>> y 또는 n을 입력해주세요.\n" + "-".repeat(20));
            }
        }
    }

    File file = new File("Point.txt");

    // 파일 생성 메소드
    public void createFile() {
        if (!file.exists()) { // 파일이 존재하지 않을 때
            try {
                FileWriter fw = new FileWriter(file);
                file.createNewFile();
                fw.write(Integer.toString(10000));
                fw.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 파일 읽기 메소드
    public int readPoints() {
        int point = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            point = Integer.parseInt(br.readLine());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return point;
    }

    // 파일 쓰기 메소드
    public void writePoints(int points) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(Integer.toString(points));
            fw.flush();
            fw.close();
            System.out.println("남은 포인트: " + points);
        } catch (IOException e) { // 입출력 예외
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        LMW20221200_mid1 lmw20221200Mid = new LMW20221200_mid1();
        lmw20221200Mid.playGame();

    }
}