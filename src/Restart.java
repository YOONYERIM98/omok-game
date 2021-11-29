
public class Restart implements Runnable{

	// 게임이 끝나면 다시 게임을 재시작하는 함수
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Main.restart();
	}
}