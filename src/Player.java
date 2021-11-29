
public class Player {

	// 플레이어 기본 변수 선언
	private String playerName;
	private String playerInfo;
	private int playerColor;
	
	// 플레이어 생성자 정의
	public Player(String playerName, String playerInfo) {
		this.playerName = playerName;
		this.playerInfo = playerInfo;
	}
	
	// 플레이어의 돌 색깔을 반환
	int getPlayerColor()
	{
		return playerColor;
	}
	
	// 플레이어의 돌 색깔을 설정
	void setPlayerColor(int playerColor)
	{
		this.playerColor = playerColor;
	}

	// 플레이어 이름을 반환
	public String getPlayerName() {
		return playerName;
	}

	// 플레이어 이름을 설정
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	// 플레이어 정보를 반환
	public String getPlayerInfo() {
		return playerInfo;
	}

	// 플레이어 정보를 설정
	public void setPlayerInfo(String playerInfo) {
		this.playerInfo = playerInfo;
	}
}