import java.awt.Graphics;
import java.util.Random;
import java.util.Scanner;

public class Game implements Runnable{

	// 하나의 게임에서 사용할 변수들을 설정
	Board board = new Board();
	private static int BLACK = 1;
	private static int WHITE = 2;
	public static Player attacker;
	Player playerOne = new Player("홍길동", "홍길동입니다.");
	Player playerTwo = new Player("인공지능", "인공지능입니다.");
	public boolean ready = true;
	boolean counting = false;

	// 게임 객체가 생성되면 바로 실행되는 생성자
	public Game()
	{
		// 어떤 플레이어가 먼저 시작할지 랜덤하게 결정
		playerSetting(playerOne, playerTwo);
		board.playerOne = playerOne;
		board.playerTwo = playerTwo;
		// BLACK 돌을 부여받은 플레이어가 선공
		if(playerOne.getPlayerColor() == BLACK)
		{
			attacker = playerOne;
		}
		else
		{
			attacker = playerTwo;	
		}
		// 컴퓨터가 선공인 경우 counting 변수가 사용됨
		counting = true;
	}
	
	// 사용자가 바둑판 이미지를 클릭해 공격을 시작하는 함수
	public boolean attack(int x, int y)
	{
		// 게임 진행 메시지 판에 입력 포커스를 맞춤
		Main.textArea.requestFocus();
		// 놓을 수 없는 자리인 경우
		if(!board.put(x, y, attacker))
		{
			Main.textArea.append("놓을 수 없는 자리입니다.\n");
			Main.textArea.append("둘 위치를 다시 선택하세요\n");
			return false;
		}
		// 돌을 놓은 뒤에 승리 판정이 난 경우
		if(board.win(x,  y, attacker))
		{
			Main.textArea.append("승리자는 " + board.getWinner().getPlayerName() + "입니다.\n");
			ready = false;
			return true;
		}
		// 승리하지 않은 경우
		else
		{
			// 공격자를 교체함
			if(playerOne == attacker)
				attacker = playerTwo;
			else
				attacker = playerOne;
			Main.textArea.append(attacker.getPlayerName() + " 플레이어가 둘 차례입니다.\n");
			
			// 컴퓨터가 AlphaBetaPruning 알고리즘을 이용하여 최적의 수를 찾아냄
			board.AlphaBetaPruning(0, -1000000, 1000000);
			
			// 컴퓨터는 최적의 수인 targetX, targetY에 착수함
			if(!board.put(board.targetX, board.targetY, attacker))
			{
				// 컴퓨터는가 찾은 최적의 수가 이미 돌이 놓여진 자리일 수는 없지만 혹시 모를 오류를 대비하여 삽입함
				Main.textArea.append("놓을 수 없는 자리입니다.\n");
				Main.textArea.append("둘 위치를 다시 선택하세요\n");
				return false;
			}
			
			// 컴퓨터가 둔 수의 평가 가치를 판별
			Main.textArea.append("현재 " + attacker.getPlayerName() + "의 평가 가치는 " + board.evaluate(attacker) + "입니다.\n");
			
			// 컴퓨터가 승리한 경우 승리 메시지 출력
			if(board.win(board.targetX,  board.targetY, attacker))
			{
				Main.textArea.append("승리자는 " + board.getWinner().getPlayerName() + "입니다.\n");
				ready = false;
				return true;
			}
			
			// 플레이어 공수 교체
			if(playerOne == attacker)
				attacker = playerTwo;
			else
				attacker = playerOne;
			Main.textArea.append(attacker.getPlayerName() + " 플레이어가 둘 차례입니다.\n");
		}
		return false;
	}
	
	// 반복적으로 하나의 바둑판 이미지를 그리게 됨
	public void draw(Graphics g)
	{
		board.draw(g);
	}
	
	// 플레이어 순서를 랜덤으로 결정
	public static void playerSetting(Player playerOne, Player playerTwo)
	{
		Random random = new Random();
		if(random.nextBoolean() == true)
		{
			playerOne.setPlayerColor(BLACK);
			playerTwo.setPlayerColor(WHITE);
		}
		else
		{
			playerOne.setPlayerColor(WHITE);
			playerTwo.setPlayerColor(BLACK);
		}
	}
	
	// 게임이 시작되었고 컴퓨터가 선공인 경우 counting 변수에 의해서 AlphaBetaPruning 알고리즘을 바로 적용해 첫번째 돌을 둔다.
	@Override
	public void run() {
			if(counting == true && attacker == playerTwo){
				// AlphaBetaPruning 알고리즘을 통해 최적의 수를 판별
				board.AlphaBetaPruning(0, -1000000, 1000000);
				// 컴퓨터는 최적의 수인 targetX, targetY에 착수함
				board.put(board.targetX, board.targetY, attacker);
				Main.textArea.append("현재 " + attacker.getPlayerName() + "의 평가 가치는 " + board.evaluate(attacker) + "입니다.\n");
				// 선수 교체
				if(playerOne == attacker)
					attacker = playerTwo;
				else
					attacker = playerOne;
				Main.textArea.append(attacker.getPlayerName() + " 플레이어가 둘 차례입니다.\n");
				// 이제 첫 번째 수가 끝났으므로 counting 함수는 false 값을 가지고 더이상 사용되지 않음
				counting = false;
			}
			Main.main.repaint();
	}

}
