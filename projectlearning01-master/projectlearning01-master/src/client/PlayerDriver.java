package client;

public class PlayerDriver {

	public static void main(String[] args) {

		System.out.println("PlayerID:aiueo,Win:1,Draw:2,Lose:3,GiveUp:4でplayerクラスのインスタンスを作成します。");
		Player player = new Player("aiueo/1/2/3/4");
		System.out.println("player.getID():"+player.getID());
		System.out.println("player.getWin():"+player.getWin());
		System.out.println("player.getDraw():"+player.getDraw());
		System.out.println("player.getLose():"+player.getLose());
		System.out.println("player.getGiveUp():"+player.getGiveUp());

	}

}
