import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main extends JFrame{

	// 메인 함수 진행에 필요한 기본적인 변수 선언
	private final int BLACK = 1, WHITE = 2;
	private int boardXSize = 15, boardYSize = 15;
	private final int WIDTH = 480, HEIGHT = 510;
	private static Game game;
	public static Main main;
	
	// 더블 버퍼링 기술에 필요한 변수
	Image dbImage;
	Graphics dbG;
	
	// 게임 진행 내용을 보여주는 JTextArea를 JScrollPane에 달아 실시간으로 GUI로 출력
	public static JTextArea textArea = new JTextArea(10, 20);
	public static JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	// 메인 함수의 생성자는 게임 실행과 동시에 구동
	public Main() {
		// 전체 GUI 크기 설정과 기본 설정
		setSize(WIDTH + 500, HEIGHT);
		setResizable(false);
		setVisible(true);
		setTitle("오목 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(new MouseHandler());
		setLocationRelativeTo(null);
		setLayout(null); 
		
		// 게임 진행 메시지 스크롤 팬을 메인 프레임에 부착
		scrollPane.setBounds(WIDTH + 20, 25, 455, 430);
		add(scrollPane);
		scrollPane.setVisible(true);
	}
	
	// 반복적으로 이미지를 갱신하는 paint() 함수 정의
	public void paint(Graphics g) {
		// 게임 진행 메시지를 출력하기 위해 필요한 부분
		super.paint(g);
		// 더블 버퍼링으로 오목 바둑판 화면 이미지를 지속적으로 갱신
		dbImage = createImage(WIDTH, HEIGHT);
		dbG = dbImage.getGraphics();
		draw(dbG);
		g.drawImage(dbImage, 0, 0, null);
	}
	
	// 하나의 게임 객체의 draw() 함수를 불러와 버퍼에 쓰게 됨
	public void draw(Graphics g) {
		game.draw(g);
	}

	// 메인 함수 영역
	public static void main(String[] args) {
		game = new Game();
		main = new Main();
		textArea.append(game.attacker.getPlayerName() + " 플레이어가 둘 차례입니다.\n");
		Thread gameThread = new Thread(game);
		gameThread.run();
		main.repaint();
	}

	// 하나의 게임이 끝난 경우 게임이 재시작되는 함수
	public static void restart() {
		game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.run();
		textArea.append("게임이 재시작되었습니다.\n");
		textArea.append(game.attacker.getPlayerName() + " 플레이어가 둘 차례입니다.\n");
		main.repaint();
	}
	
	// 마우스 클릭 이벤트 핸들러 부분 정의
	public class MouseHandler extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			
			// 마우스 클릭이 일어나면 현재 게임판 이미지 갱신
			repaint();
			
			// 마우스 이벤트가 발생한 모니터의 x, y 위치를 판별
			int mx = e.getX();
			int my = e.getY();
			
			// 현재 게임이 준비된 상태인 경우
			if(game.ready)
			{
				// 현재 선택한 부분에 해당하는 바둑판의 좌표값을 찾아냄
				for(int i = 0; i < boardXSize; i++)
				{
					for(int j = 0; j < boardYSize; j++)
					{
						if(mx > 30 * i + 15 && mx < 30 * i + 45 && my > 30 * j + 45 && my < 30 * j + 75)
						{
							// 해당 위치에 돌을 착수 시도함
							if(game.attack(i, j))
							{
								// 이긴 경우 게임을 재시작함
								textArea.append("3초 후에 게임이 재시작됩니다.\n");
								Restart restart = new Restart();
								Thread restartThread = new Thread(restart);
								restartThread.start();
							}
						}
					}
				}
			}
		}
	}
	
}
