package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

public class MainPanelDriver extends JFrame{

	public static void main(String[] args) {
		System.out.println("Clientクラスのインスタンス作成");

		Client client=new Client();

		client.setDefaultCloseOperation(EXIT_ON_CLOSE);

		client.setVisible(true);

		client.setResizable(false);

		//client.connectServer("localhost", 10000);

		Socket socket;
		try {
			socket = new Socket("localhost", 10000);
			client.out = new PrintWriter(socket.getOutputStream(), true);

		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		System.out.println("main画面表示");

		client.mainPanel.mainScreen();

	}

}
