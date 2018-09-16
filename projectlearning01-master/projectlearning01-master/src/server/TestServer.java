package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class TestServer {
	PrintWriter out;

	private Receiver receiver;

	public void accept(String ip) {
		try {
            // ポートを取得
            int port = Integer.parseInt(ip);

            // サーバーソケットを作成
            ServerSocket ss = new ServerSocket(port);

            // 無限ループ
            while (true) {
                    // クライアントからの要求を受け取る
                    Socket s = ss.accept();


        			out =new PrintWriter(s.getOutputStream(), true); //データ送信用オブジェクトの用意


        			receiver=new Receiver(s);
        			receiver.start();

            }


    } catch (Exception e) {
            e.printStackTrace();
    }
	}

	class Receiver extends Thread{
		private InputStreamReader sisr;
		private BufferedReader br;

		Receiver(Socket socket){
			try {
				sisr=new InputStreamReader(socket.getInputStream());
				br=new BufferedReader(sisr);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				while(true) {
					String inputLine=br.readLine();
					if(inputLine!=null) {
						//out.println("[inputLine]");
						System.out.println("受信メッセージ: "+inputLine);
					}

					if(inputLine.equals("31")) {

						String str;
						str="3yamada/10/20/30/40/5";

						out.println(str);
						out.flush();
	        			System.out.println("サーバにメッセージを送信しました");
					}

				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}



	public static void main(String[] args) {

		TestServer server = new TestServer();
		String ipAddress=args[0];
		server.accept(ipAddress);

    }



}
