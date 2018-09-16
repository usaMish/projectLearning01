package client;

import javax.swing.JFrame;

public class MenuPanelDriver extends JFrame{

	public static void main(String[] args) {
		System.out.println("Clientクラスのインスタンス作成");

		Client client=new Client();
		client.setDefaultCloseOperation(EXIT_ON_CLOSE);
		client.setVisible(true);
		client.setResizable(false);

		client.connectServer("localhost",10000);


		Player my=new Player("aiueo/1/2/3/4");
		client.menuPanel=new MenuPanel(client,my);
		client.add(client.menuPanel,0);
		client.menuPanel.setLayout(null);
		client.menuPanel.setVisible(true);

		client.changePanel(0, 1);

		System.out.println("rule画面表示(現在onlineのplayerはtestとしてyamada)");

		client.menuPanel.ruleScreen();



	}

}
