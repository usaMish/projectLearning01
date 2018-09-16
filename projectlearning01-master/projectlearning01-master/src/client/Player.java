package client;

public class Player {
	private String playerID; //プレイヤID
	private String win;
	private String lose;
	private String draw;
	private String giveUp;


	Player(String str){
		String[] playerData=str.split("/");
		playerID=playerData[0];
		win=playerData[1];
		draw=playerData[2];
		lose=playerData[3];
		giveUp=playerData[4];

		//System.out.println("player 情報を取得しました。");
		//System.out.println(playerID+win+draw+lose+giveUp);

	}

	public String getID(){ //プレイヤIDの取得
		return playerID;
	}

	public String getWin(){//対戦相手の勝利数取得（string）
		return win;
	}

	public String getLose(){
		return lose;
	}

	public String getDraw(){
		return draw;
	}

	public String getGiveUp(){
		return giveUp;
	}


}
