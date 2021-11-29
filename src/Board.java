import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Board{

	// 보드판 기본 변수 선언
	private final int WIDTH = 480, HEIGHT = 510;
	private final int BLACK = 1, WHITE = 2;
	private int boardXSize = 15, boardYSize = 15;
	private int[][] boardMap = new int[boardXSize][boardYSize];
	public Player playerOne, playerTwo;
	private Player Winner;
	private Image boardImage;
	private Image blackImage;
	private Image whiteImage;
	// AlphaBetaPruning 알고리즘의 최대 Depth를 의미하는 Limit
	public int limit = 2;
	// AlphaBetaPruning 알고리즘을 통해 찾은 최적의 수를 의미하는 targetX, targetY
	public int targetX, targetY;

	// 보드판 생성자 정의
	public Board()
	{
		// 각각의 바둑판, 돌 이미지를 불러와 이미지 객체로서 삽입
		boardImage = new ImageIcon(getClass().getResource("/image/board.png")).getImage();
		blackImage = new ImageIcon(getClass().getResource("/image/black.png")).getImage();
		whiteImage = new ImageIcon(getClass().getResource("/image/white.png")).getImage();
		for(int i = 0; i < boardXSize; i++)
		{
			for(int j = 0; j < boardYSize; j++)
			{
				// 보드판 행렬 초기화
				boardMap[i][j] = 0;	
			}
		}
	}
	
	// 보드판을 이미지를 새롭게 그리는 함수
	public void draw(Graphics g) {
		// 바둑판 이미지를 불러옴
		g.drawImage(boardImage, 0, 30, null);
		// 각각의 가로, 세로 선을 그음
		for(int i = 1; i <= boardXSize; i++)
			g.drawLine(30 * i, 60, 30 * i, 480);
		for(int i = 1; i <= boardYSize; i++)
			g.drawLine(30, 30 + 30 * i, 450, 30 + 30 * i);
		// 모든 바둑판 위치를 돌며 돌이 놓여진 위치에 돌 이미지를 삽입
		for(int i = 1; i <= boardXSize; i++)
		{
			for(int j = 1; j <= boardYSize; j++)
			{
				if(boardMap[i - 1][j - 1] != 0)
				{
					if(boardMap[i - 1][j - 1] == BLACK)
						g.drawImage(blackImage, 30 * i - 15, 15 + 30 * j, null);
					if(boardMap[i - 1][j - 1] == WHITE)
						g.drawImage(whiteImage, 30 * i - 15, 15 + 30 * j, null);
				}
			}
		}
	}
	
	// 보드판에 돌을 놓는 함수
	public boolean put(int x, int y, Player player)
	{
		// 그 위치에 돌이 있는지 판정
		if(boardMap[x][y] != 0)
		{
			Main.textArea.append("이미 그 위치에 돌이 존재합니다.\n");
			return false;
		}
		// 돌을 놓음
		boardMap[x][y] = player.getPlayerColor();
		return true;
	}
	
	// 현재 보드판을 출력
	public void show()
	{
		for(int i = 0; i < boardXSize; i++)
		{
			for(int j = 0; j < boardYSize; j++)
			{
				System.out.print(boardMap[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	// 승리자를 반환
	public Player getWinner()
	{
		return Winner;
	}
	
	/*------------- 승리 판정 부분 -------------*/
	
	// 플레이어 승리 판정
	public boolean win(int x, int y, Player player)
	{
		// 5목 이상을 만든 어떠한 경우라도 존재한다면
		if(make5mok1(x, y)||make5mok2(x, y)||make5mok3(x, y)||make5mok4(x, y)||make5mok5(x, y)||
				make5mok6(x, y)||make5mok7(x, y)||make5mok8(x, y)||make5mok9(x, y)||make5mok10(x, y)||
				make5mok11(x, y)||make5mok12(x, y)||make5mok13(x, y)||make5mok14(x, y)||make5mok15(x, y)||
				make5mok16(x, y)||make5mok17(x, y)||make5mok18(x, y)||make5mok19(x, y)||make5mok20(x, y))
		{
			Winner = player;
			return true;
		}
		return false;
	}
	
	/*------------- 5목이 만들어질 수 있는 모든 20가지 경우의 수  -------------*/
	
	public boolean make5mok1(int x, int y)
	{
		try
		{
			for(int i = y; i < y + 5; i++)
			{
				if(boardMap[x][i] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok2(int x, int y)
	{
		try
		{
			for(int i = x, j = y; i < x + 5; i++, j--)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok3(int x, int y)
	{
		try
		{
			for(int i = x; i < x + 5; i++)
			{
				if(boardMap[i][y] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok4(int x, int y)
	{
		try
		{
			for(int i = x, j = y; i < x + 5; i++, j++)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok5(int x, int y)
	{
		try
		{
			for(int i = y; i > y - 5; i--)
			{
				if(boardMap[x][i] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok6(int x, int y)
	{
		try
		{
			for(int i = x, j = y; i > x - 5; i--, j++)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok7(int x, int y)
	{
		try
		{
			for(int i = x; i > x - 5; i--)
			{
				if(boardMap[i][y] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok8(int x, int y)
	{
		try
		{
			for(int i = x, j = y; i > x - 5; i--, j--)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok9(int x, int y)
	{
		try
		{
			for(int i = y - 1; i < y + 4; i++)
			{
				if(boardMap[x][i] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok10(int x, int y)
	{
		try
		{
			for(int i = x - 1, j = y + 1; i < x + 4; i++, j--)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok11(int x, int y)
	{
		try
		{
			for(int i = x - 1; i < x + 4; i++)
			{
				if(boardMap[i][y] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok12(int x, int y)
	{
		try
		{
			for(int i = x - 1, j = y - 1; i < x + 4; i++, j++)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok13(int x, int y)
	{
		try
		{
			for(int i = y + 1; i > y - 4; i--)
			{
				if(boardMap[x][i] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok14(int x, int y)
	{
		try
		{
			for(int i = x + 1, j = y - 1; i > x - 4; i--, j++)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok15(int x, int y)
	{
		try
		{
			for(int i = x + 1; i > x - 4; i--)
			{
				if(boardMap[i][y] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok16(int x, int y)
	{
		try
		{
			for(int i = x + 1, j = y + 1; i > x - 4; i--, j--)
			{
				if(boardMap[i][j] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok17(int x, int y)
	{
		try
		{
			for(int i = y - 2; i < y + 3; i++)
			{
				if(boardMap[x][i] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok18(int x, int y)
	{
		try
		{
			for(int i = x - 2; i < x + 3; i++)
			{
				if(boardMap[i][y] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok19(int x, int y)
	{
		try
		{
			for(int i = y + 2; i > y - 3; i--)
			{
				if(boardMap[x][i] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public boolean make5mok20(int x, int y)
	{
		try
		{
			for(int i = x + 2; i > x - 3; i--)
			{
				if(boardMap[i][y] != boardMap[x][y])
				{
					return false;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	/*------------- 3목이 만들어질 수 있는 경우의 수 판별  -------------*/
	
	/*
	 * 기본적으로 각 방향으로 3목이 생성되면 count에 2를 설정합니다.
	 * 이후에 한 쪽이라도 막혀있으면 count를 1씩 뺍니다.
	 * 즉, 열린 3은 두 곳 모두 열려있으므로 2를 반환합니다.
	 * 하나 닫힌 3은 1을 반환하며 닫힌 3은 0을 반환하게 됩니다.
	 * 
	 */
	
	public int make3mok1(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x][y + 1] && boardMap[x][y] == boardMap[x][y + 2])
			{
				count = 2;
				// 4목인 경우
				if(y < boardYSize - 3 && boardMap[x][y] == boardMap[x][y + 3])
				{
					return -1;
				}
				if(y > 0 && boardMap[x][y] == boardMap[x][y - 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(y == boardYSize - 3 || boardMap[x][y + 3] != 0)
				{
					count--;
				}
				if(y == 0 || boardMap[x][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok2(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y + 1] == 0 && boardMap[x][y] == boardMap[x][y + 2] && boardMap[x][y] == boardMap[x][y + 3])
			{
				count = 2;
				// 4목인 경우
				if(y < boardYSize - 4 && boardMap[x][y] == boardMap[x][y + 4])
				{
					return -1;
				}
				if(y > 0 && boardMap[x][y] == boardMap[x][y - 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(y == boardYSize - 4 || boardMap[x][y + 4] != 0)
				{
					count--;
				}
				if(y == 0 || boardMap[x][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok3(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y + 2] == 0 && boardMap[x][y] == boardMap[x][y + 1] && boardMap[x][y] == boardMap[x][y + 3])
			{
				count = 2;
				// 4목인 경우
				if(y < boardYSize - 4 && boardMap[x][y] == boardMap[x][y + 4])
				{
					return -1;
				}
				if(y > 0 && boardMap[x][y] == boardMap[x][y - 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(y == boardYSize - 4 || boardMap[x][y + 4] != 0)
				{
					count--;
				}
				if(y == 0 || boardMap[x][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok4(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y + 1] && boardMap[x][y] == boardMap[x - 2][y + 2])
			{
				count = 2;
				// 4목인 경우
				if(x > 2 && y < boardYSize - 3 && boardMap[x][y] == boardMap[x - 3][y + 3])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y > 0 && boardMap[x][y] == boardMap[x + 1][y - 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == 2 || y == boardYSize - 3 || boardMap[x - 3][y + 3] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == 0 || boardMap[x + 1][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok5(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 1][y + 1] == 0 && boardMap[x][y] == boardMap[x - 2][y + 2] && boardMap[x][y] == boardMap[x - 3][y + 3])
			{
				count = 2;
				// 4목인 경우
				if(x > 3 && y < boardYSize - 4 && boardMap[x][y] == boardMap[x - 4][y + 4])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y > 0 && boardMap[x][y] == boardMap[x + 1][y - 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == 3 || y == boardYSize - 4 || boardMap[x - 4][y + 4] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == 0 || boardMap[x + 1][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok6(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 2][y + 2] == 0 && boardMap[x][y] == boardMap[x - 1][y + 1] && boardMap[x][y] == boardMap[x - 3][y + 3])
			{
				count = 2;
				// 4목인 경우
				if(x > 3 && y < boardYSize - 4 && boardMap[x][y] == boardMap[x - 4][y + 4])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y > 0 && boardMap[x][y] == boardMap[x + 1][y - 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == 3 || y == boardYSize - 4 || boardMap[x - 4][y + 4] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == 0 || boardMap[x + 1][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok7(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y] && boardMap[x - 2][y] == boardMap[x][y])
			{
				count = 2;
				// 4목인 경우
				if(x < boardXSize - 1 && boardMap[x][y] == boardMap[x + 1][y])
				{
					return -1;
				}
				if(x > 2 && boardMap[x][y] == boardMap[x - 3][y])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == boardXSize - 1 || boardMap[x + 1][y] != 0)
				{
					count--;
				}
				if(x == 2 || boardMap[x - 3][y] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok8(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 1][y] == 0 && boardMap[x][y] == boardMap[x - 2][y] && boardMap[x - 3][y] == boardMap[x][y])
			{
				count = 2;
				// 4목인 경우
				if(x < boardXSize - 1 && boardMap[x][y] == boardMap[x + 1][y])
				{
					return -1;
				}
				if(x > 3 && boardMap[x][y] == boardMap[x - 4][y])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == boardXSize - 1 || boardMap[x + 1][y] != 0)
				{
					count--;
				}
				if(x == 3 || boardMap[x - 4][y] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok9(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 2][y] == 0 && boardMap[x][y] == boardMap[x - 1][y] && boardMap[x - 3][y] == boardMap[x][y])
			{
				count = 2;
				// 4목인 경우
				if(x < boardXSize - 1 && boardMap[x][y] == boardMap[x + 1][y])
				{
					return -1;
				}
				if(x > 3 && boardMap[x][y] == boardMap[x - 4][y])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == boardXSize - 1 || boardMap[x + 1][y] != 0)
				{
					count--;
				}
				if(x == 3 || boardMap[x - 4][y] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok10(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y - 1] && boardMap[x][y] == boardMap[x - 2][y - 2])
			{
				count = 2;
				// 4목인 경우
				if(x > 2 && y > 2 && boardMap[x][y] == boardMap[x - 3][y - 3])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y < boardYSize - 1 && boardMap[x][y] == boardMap[x + 1][y + 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == 2 || y == 2 || boardMap[x - 3][y - 3] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == boardYSize - 1 || boardMap[x + 1][y + 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok11(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 1][y - 1] == 0 && boardMap[x][y] == boardMap[x - 2][y - 2] && boardMap[x][y] == boardMap[x - 3][y - 3])
			{
				count = 2;
				// 4목인 경우
				if(x > 3 && y > 3 && boardMap[x][y] == boardMap[x - 4][y - 4])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y < boardYSize - 1 && boardMap[x][y] == boardMap[x + 1][y + 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == 3 || y == 3 || boardMap[x - 4][y - 4] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == boardYSize - 1 || boardMap[x + 1][y + 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make3mok12(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 2][y - 2] == 0 && boardMap[x][y] == boardMap[x - 1][y - 1] && boardMap[x][y] == boardMap[x - 3][y - 3])
			{
				count = 2;
				// 4목인 경우
				if(x > 3 && y > 3 && boardMap[x][y] == boardMap[x - 4][y - 4])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y < boardYSize - 1 && boardMap[x][y] == boardMap[x + 1][y + 1])
				{
					return -1;
				}
				// 4목이 아닌 경우 닫혔는지 확인
				if(x == 3 || y == 3 || boardMap[x - 4][y - 4] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == boardYSize - 1 || boardMap[x + 1][y + 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	/*------------- 4목이 만들어질 수 있는 경우의 수 판별  -------------*/
	
	/*
	 * 기본적으로 각 방향으로 4목이 생성되면 count에 2를 설정합니다.
	 * 이후에 한 쪽이라도 막혀있으면 count를 1씩 뺍니다.
	 * 즉, 열린 4은 두 곳 모두 열려있으므로 2를 반환합니다.
	 * 하나 닫힌 4은 1을 반환하며 닫힌 4은 0을 반환하게 됩니다.
	 * 
	 */
	
	public int make4mok1(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x][y + 1] && boardMap[x][y] == boardMap[x][y + 2] && boardMap[x][y] == boardMap[x][y + 3])
			{
				count = 2;
				// 4목 주변으로 닫혔는지 확인
				if(y == boardYSize - 4 || boardMap[x][y + 4] != 0)
				{
					count--;
				}
				if(y == 0 || boardMap[x][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok2(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y + 1] == 0 && boardMap[x][y] == boardMap[x][y + 2] && boardMap[x][y] == boardMap[x][y + 3] && boardMap[x][y] == boardMap[x][y + 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok3(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y + 2] == 0 && boardMap[x][y] == boardMap[x][y + 1] && boardMap[x][y] == boardMap[x][y + 3] && boardMap[x][y] == boardMap[x][y + 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok4(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y + 3] == 0 && boardMap[x][y] == boardMap[x][y + 1] && boardMap[x][y] == boardMap[x][y + 2] && boardMap[x][y] == boardMap[x][y + 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok5(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y + 1] && boardMap[x][y] == boardMap[x - 2][y + 2] && boardMap[x][y] == boardMap[x - 3][y + 3])
			{
				count = 2;
				// 4목 주변으로 닫혔는지 확인
				if(x == boardXSize - 1 || y == 0 || boardMap[x + 1][y - 1] != 0)
				{
					count--;
				}
				if(x == 3 || y == boardYSize - 4 || boardMap[x - 4][y + 4] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok6(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 1][y + 1] == 0 && boardMap[x][y] == boardMap[x - 2][y + 2] && boardMap[x][y] == boardMap[x - 3][y + 3] && boardMap[x][y] == boardMap[x - 4][y + 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok7(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 2][y + 2] == 0 && boardMap[x][y] == boardMap[x - 1][y + 1] && boardMap[x][y] == boardMap[x - 3][y + 3] && boardMap[x][y] == boardMap[x - 4][y + 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok8(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 3][y + 3] == 0 && boardMap[x][y] == boardMap[x - 1][y + 1] && boardMap[x][y] == boardMap[x - 2][y + 2] && boardMap[x][y] == boardMap[x - 4][y + 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok9(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y] && boardMap[x][y] == boardMap[x - 2][y] && boardMap[x - 3][y] == boardMap[x][y])
			{
				count = 2;
				// 4목 주변으로 닫혔는지 확인
				if(x == boardXSize - 1 || boardMap[x + 1][y] != 0)
				{
					count--;
				}
				if(x == 3 || boardMap[x - 4][y] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok10(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 1][y] == 0 && boardMap[x][y] == boardMap[x - 2][y] && boardMap[x][y] == boardMap[x - 3][y] && boardMap[x - 4][y] == boardMap[x][y])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok11(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 2][y] == 0 && boardMap[x][y] == boardMap[x - 1][y] && boardMap[x][y] == boardMap[x - 3][y] && boardMap[x - 4][y] == boardMap[x][y])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok12(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 3][y] == 0 && boardMap[x][y] == boardMap[x - 2][y] && boardMap[x][y] == boardMap[x - 1][y] && boardMap[x - 4][y] == boardMap[x][y])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok13(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y - 1] && boardMap[x][y] == boardMap[x - 2][y - 2] && boardMap[x][y] == boardMap[x - 3][y - 3])
			{
				count = 2;
				// 4목 주변으로 닫혔는지 확인
				if(x == boardXSize - 1 || y == boardYSize - 1 || boardMap[x + 1][y + 1] != 0)
				{
					count--;
				}
				if(x == 3 || y == 3 || boardMap[x - 4][y - 4] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok14(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 1][y - 1] == 0 && boardMap[x][y] == boardMap[x - 2][y - 2] && boardMap[x][y] == boardMap[x - 3][y - 3] && boardMap[x][y] == boardMap[x - 4][y - 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok15(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 2][y - 2] == 0 && boardMap[x][y] == boardMap[x - 1][y - 1] && boardMap[x][y] == boardMap[x - 3][y - 3] && boardMap[x][y] == boardMap[x - 4][y - 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	public int make4mok16(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x - 3][y - 3] == 0 && boardMap[x][y] == boardMap[x - 1][y - 1] && boardMap[x][y] == boardMap[x - 2][y - 2] && boardMap[x][y] == boardMap[x - 4][y - 4])
			{
				count = 1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}
	
	/*------------- 2목이 만들어질 수 있는 경우의 수 판별  -------------*/
	
	/*
	 * 기본적으로 각 방향으로 2목이 생성되면 count에 2를 설정합니다.
	 * 이후에 한 쪽이라도 막혀있으면 count를 1씩 뺍니다.
	 * 즉, 열린 2은 두 곳 모두 열려있으므로 2를 반환합니다.
	 * 하나 닫힌 2은 1을 반환하며 닫힌 3은 0을 반환하게 됩니다.
	 * 
	 */
	
	public int make2mok1(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x][y + 1])
			{
				count = 2;
				// 3목인 경우
				if(y < boardYSize - 2 && boardMap[x][y] == boardMap[x][y + 2])
				{
					return -1;
				}
				if(y > 0 && boardMap[x][y] == boardMap[x][y - 1])
				{
					return -1;
				}
				// 3목이 아닌 경우 닫혔는지 확인
				if(y == boardYSize - 2 || boardMap[x][y + 2] != 0)
				{
					count--;
				}
				if(y == 0 || boardMap[x][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}	
	
	
	public int make2mok2(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y + 1])
			{
				count = 2;
				// 3목인 경우
				if(x > 1 && y < boardYSize - 2 && boardMap[x][y] == boardMap[x - 2][y + 2])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y > 0 && boardMap[x][y] == boardMap[x + 1][y - 1])
				{
					return -1;
				}
				// 3목이 아닌 경우 닫혔는지 확인
				if(x == 1 || y == boardYSize - 2 || boardMap[x - 2][y + 2] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == 0 || boardMap[x + 1][y - 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}	
	
	public int make2mok3(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y])
			{
				count = 2;
				// 3목인 경우
				if(x > 1 && boardMap[x][y] == boardMap[x - 2][y])
				{
					return -1;
				}
				if(x < boardXSize - 1 && boardMap[x][y] == boardMap[x + 1][y])
				{
					return -1;
				}
				// 3목이 아닌 경우 닫혔는지 확인
				if(x == 1 || boardMap[x - 2][y] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || boardMap[x + 1][y] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}	
	
	public int make2mok4(int x, int y)
	{
		int count = -1;
		try
		{
			if(boardMap[x][y] == boardMap[x - 1][y - 1])
			{
				count = 2;
				// 3목인 경우
				if(x > 1 && y > 1 && boardMap[x][y] == boardMap[x - 2][y - 2])
				{
					return -1;
				}
				if(x < boardXSize - 1 && y < boardYSize - 1 && boardMap[x][y] == boardMap[x + 1][y + 1])
				{
					return -1;
				}
				// 3목이 아닌 경우 닫혔는지 확인
				if(x == 1 || y == 1 || boardMap[x - 2][y - 2] != 0)
				{
					count--;
				}
				if(x == boardXSize - 1 || y == boardYSize - 1 || boardMap[x + 1][y + 1] != 0)
				{
					count--;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
		return count;
	}		
	
	/*------------- 현재 두려는 수의 가치를 판단하여 책정하는 부분 -------------*/
	
	/*
	 * < 가치 판단 표 >
	 * 
	 * 5목 : 게임에서 승리하므로 가치가 1000000입니다.
	 * 열린 4목 : 게임에서 승리하기 직전이므로 가치가 100000입니다.
	 * 열린 3목, 하나 닫힌4목 : 상대가 막아야 되는 수이므로 가치가 10000입니다.
	 * 열린 2목, 하나 닫힌3목, 닫힌 4목 : 공격의 시발점이 되는 수이므로 가치가 3000입니다.
	 * 하나 닫힌 2목, 닫힌 3목 : 어느 정도의 가치가 있는 수이므로 가치가 100입니다.
	 * 닫힌 2목 : 가치가 5입니다.
	 * 현재 두는 수가 중간에 가까울수록 점수를 가중합니다.
	 * -> 위치가 인덱스 7에 가장 가까울 때는 200, 1 멀어질수록 20씩 감소합니다.
	 * 
	 */
	
	public int evaluate(Player player)
	{
		// 각각의 돌이 놓여진 경우를 체크하는 변수
		int sum = 0;
		int open3 = 0;
		int close3 = 0;
		int half3 = 0;
		int open4 = 0;
		int close4 = 0;
		int half4 = 0;
		int open2 = 0;
		int close2 = 0;
		int half2 = 0;
		int count;
		
		// 모든 바둑판을 검사
		for(int i = 0; i < boardXSize; i++)
		{
			for(int j = 0; j < boardYSize; j++)
			{
				// 자신의 돌이 있는 경우
				if(boardMap[i][j] == player.getPlayerColor())
				{
					// 5목이 만들어진 경우
					if(make5mok1(i, j)||make5mok2(i, j)||make5mok3(i, j)||make5mok4(i, j)||make5mok5(i, j)||
							make5mok6(i, j)||make5mok7(i, j)||make5mok8(i, j)||make5mok9(i, j)||make5mok10(i, j)||
							make5mok11(i, j)||make5mok12(i, j)||make5mok13(i, j)||make5mok14(i, j)||make5mok15(i, j)||
							make5mok16(i, j)||make5mok17(i, j)||make5mok18(i, j)||make5mok19(i, j)||make5mok20(i, j))
					{
						return 1000000;
					}
					
					/********** 단순히 전체에서 4목이 만들어진 경우만 체크 **********/
					
					count = make4mok1(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok2(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok3(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok4(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok5(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok6(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok7(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok8(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok9(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok10(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok11(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok12(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok13(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok14(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok15(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					count = make4mok16(i, j);
					if(count == 2)
						open4++;
					else if(count == 1)
						half4++;
					else if(count == 0)
						close4++;
					
					/********** 단순히 전체에서 3목이 만들어진 경우만 체크 **********/
					
					count = make3mok1(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok2(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok3(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok4(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok5(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok6(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok7(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok8(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok9(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok10(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok11(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					count = make3mok12(i, j);
					if(count == 2)
						open3++;
					else if(count == 1)
						half3++;
					else if(count == 0)
						close3++;
					
					/********** 단순히 전체에서 2목이 만들어진 경우만 체크 **********/
					
					count = make2mok1(i, j);
					if(count == 2)
						open2++;
					else if(count == 1)
						half2++;
					else if(count == 0)
						close2++;
					count = make2mok2(i, j);
					if(count == 2)
						open2++;
					else if(count == 1)
						half2++;
					else if(count == 0)
						close2++;	
					count = make2mok3(i, j);
					if(count == 2)
						open2++;
					else if(count == 1)
						half2++;
					else if(count == 0)
						close2++;
					count = make2mok4(i, j);
					if(count == 2)
						open2++;
					else if(count == 1)
						half2++;
					else if(count == 0)
						close2++;				
					
					/********** 바둑돌이 중간에 가까울 수록 가중치를 계산 **********/
					if(i > 7)
					{
						sum += 200 - ((i - 7) * 20);
					}
					else
					{
						sum += 200 - (7 - i) * 20;
					}
					if(j > 7)
					{
						sum += 200 - ((j - 7) * 20);
					}
					else
					{
						sum += 200 - (7 - j) * 20;
					}					
				}
			}
		}
		
		// 가중치를 계산한 합을 sum에 더하여 반환
		sum += open4 * 100000;
		sum += half4 * 10000;
		sum += close4 * 3000;
		sum += open3 * 10000;
		sum += half3 * 3000;
		sum += close3 * 100;
		sum += open2 * 3000;
		sum += half2 * 100;
		sum += close2 * 5;
		return sum;
	}
	
	// AlphaBetaPruning 알고리즘 함수
	int AlphaBetaPruning(int level, int alpha, int beta)
	{
		// 만약에 현재 최대 깊이인 limit에 도달한 경우
		if(level == limit)
		{
			// 컴퓨터의 평가 가치에서 플레이어의 평가 가치를 뺀 수를 반환
			return evaluate(playerTwo) - evaluate(playerOne);
		}
		// MAX 부분에 해당하는 경우
		if(level % 2 == 0)
		{
			// 더이상 작아질 수 없는 수를 max로 설정
			int max = -1000000;
			// 탐색을 끝내는 경우 find를 1로 설정
			int find = 0;
			// 전체 바둑판을 모두 탐색
			for(int i = 0; i < boardXSize; i++)
			{
				for(int j = 0; j < boardYSize; j++)
				{
					// 현재 위치에 둘 수 있는 경우
					if(boardMap[i][j] == 0)
					{
						// 잠시 그 수를 둔 것으로 설정
						boardMap[i][j] = playerTwo.getPlayerColor();
						// 재귀적 호출
						int e =  AlphaBetaPruning(level + 1, alpha, beta);
						// 다시 그 수를 두지 않은 것으로 설정
						boardMap[i][j] = 0;
						// 만약에 더욱 효율적인 수를 찾은 경우
						if(max < e)
						{
							// 그 수로 타겟을 설정
							max = e;
							if(level == 0)
							{
								targetX = i;
								targetY = j;
							}
						}
						// alpha값 갱신
						if(alpha < max)
						{
							alpha = max;
							// 만약에 현재 알파값이 베타값보다 크다면 더이상 노드를 볼 필요가 없음
							if(alpha >= beta)
							{
								find = 1;
							}
						}
					}
					if(find == 1)
					{
						break;
					}
				}
				if(find == 1)
				{
					break;
				}
			}
			return max;
		}
		// MIN 부분에 해당하는 경우
		else
		{
			// 더이상 커질 수 없는 수를 min으로 설정
			int min = 1000000;
			// 탐색을 끝내는 경우 find를 1로 설정
			int find = 0;
			// 전체 바둑판을 모두 탐색
			for(int i = 0; i < boardXSize; i++)
			{
				for(int j = 0; j < boardYSize; j++)
				{
					// 현재 위치에 둘 수 있는 경우
					if(boardMap[i][j] == 0)
					{
						// 잠시 그 수를 둔 것으로 설정
						boardMap[i][j] = playerOne.getPlayerColor();
						// 재귀적 호출
						int e =  AlphaBetaPruning(level + 1, alpha, beta);
						// 다시 그 수를 두지 않은 것으로 설정
						boardMap[i][j] = 0;
						// 더욱 작은 수를 찾은 경우
						if(min > e)
						{
							min = e;
						}
						// beta값 갱신
						if(beta > min)
						{
							beta = min;
							// 만약에 현재 알파값이 베타값보다 크다면 더이상 노드를 볼 필요가 없음
							if(alpha >= beta)
							{
								find = 1;
							}
						}
					}
					if(find == 1)
					{
						break;
					}	
				}
				if(find == 1)
				{
					break;
				}				
			}
			return min;			
		}
	}
}