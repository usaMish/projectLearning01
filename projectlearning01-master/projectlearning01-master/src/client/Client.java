package client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;







public class Client extends JFrame{



	private final static int NEW_PLAYER = 0;			//プレイヤーの新規登録

	private final static int LOGIN = 1;					//ログイン

	private final static int RESULT = 2;				//戦績表示

	private final static int ONLINE = 3;				//online（オファー送受信可能状態）画面へ

	private final static int OFFER_FROM_OTHER = 4;		//相手からオファー

	private final static int REPLY_FROM_OTHER = 5;		//相手からオファーの返事

	private final static int WHERE_PUT= 6;				//相手がどこに手を置いたかの情報

	private final static int OPPONENT_DISCONNECT= 7;	//相手が切断した情報

	private final static int GAME_FINISH = 8;			//ゲームを終了しmenu画面へ





	private static final int x=1500;

	private static final int y=1000;



	private static int receiveHandler=0;//1:データ受信要求中



	PrintWriter out;

	private Receiver receiver;



	private Player my;

	private ArrayList<Player> getOfferPlayer = new ArrayList<Player>();//offer人数


	//driverを使うときはprivate修飾子を消す
	MainPanel mainPanel=new MainPanel(this);

	MenuPanel menuPanel;

	OthelloPanel othelloPanel;




	//driverを使うときはprivate修飾子を消す
	Client() {//コンストラクタ

		this.add(mainPanel);

		mainPanel.setLayout(null);

		mainPanel.setVisible(true);

		this.setSize(x,y);

	}





	public void connectServer(String ipAddress,int port) {

		Socket socket=null;

		try {

			socket=new Socket(ipAddress,port);



			mainPanel.mainScreen();



			//送信用object

			out = new PrintWriter(socket.getOutputStream(), true); //データ送信用オブジェクトの用意



			//受信用object

			receiver=new Receiver(socket);

			receiver.start();





		}catch(UnknownHostException e) {

			System.out.println("ホストのＩＰアドレスが判定できません: "+e);

			mainPanel.connectError();//アプリケーション画面にも表示(アプリケーションメッセージでは２つのエラーを区別しない。)

		}catch(IOException e) {

			System.out.println("サーバー接続時にエラーが発生しました: "+e);

			mainPanel.connectError();

		}

	}



	//データ送信用メソッド

	public void sendMessage(String msg){	// サーバに操作情報を送信

		out.println(msg);//送信データをバッファに書き出す

		out.flush();//送信データを送る

		System.out.println("サーバにメッセージ[" + msg+ "]を送信しました"); //テスト標準出力

	}



	public void receiveHandler(int i){//receiveHandler:データ受信をクライアントが欲している状態であるかどうかを判断する変数

		receiveHandler=i;

	}



	//データ受信用クラス

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



					if(inputLine!=null&&receiveHandler==1) {

						classifyMsg(inputLine,br);

					}

				}

			}catch(IOException e) {

				e.printStackTrace();

			}

		}

	}





	public void classifyMsg(String msg,BufferedReader br) {//ここで受信データの種類判別

		System.out.println("サーバからメッセージ[" + msg + "]を受信しました"); //テスト用標準出力

		int type;



		type = Integer.parseInt("" +msg.charAt(0));



		switch(type){



		case NEW_PLAYER:{



			receiveHandler=0;



			if(msg.equals("00")) {//新規作成失敗

				mainPanel.authenticationMsg(0);

			}else if(msg.equals("01")) {//成功

				mainPanel.authenticationMsg(1);

			}



			receiveHandler=0;



			break;

		}



		case LOGIN:{

			receiveHandler=0;



			if(msg.equals("10")) {//login失敗

				mainPanel.authenticationMsg(2);

			}else if((msg.substring(0,2)).equals("11")) {//成功

				my=new Player(msg.substring(2));



				menuPanel=new MenuPanel(this,my);



				changePanel(0,1);//menuPanelに遷移

				menuPanel.ruleScreen();

			}

			break;

		}



		case RESULT:{

			receiveHandler=0;



			String str=msg.substring(1);



			ArrayList<String> results = new ArrayList<String>();

			Player player=null;



			if(!(str.equals(""))) {

				player=new Player(str);

			}



			try {

				while(br.ready()) {

					str=br.readLine();

					results.add(str);

				}

			} catch (IOException e) {

				e.printStackTrace();

			}



			String[]  s= results.toArray(new String[results.size()]);



			if(player==null) {

				menuPanel.searchError();

			}else {

				menuPanel.results(player,s);

			}



			break;

		}



		case ONLINE:{

			receiveHandler=0;



			String str=msg.substring(1);



			ArrayList<Player> onlinePlayers = new ArrayList<Player>();

			Player player=null;





			if(!(str.equals(""))) {

				player=new Player(str);

				onlinePlayers.add(player);

			}



			try {

				while(br.ready()) {

					str=br.readLine();

					player=new Player(str);

					onlinePlayers.add(player);

				}

			} catch (IOException e) {

				e.printStackTrace();

			}



			Player[]  onlines= onlinePlayers.toArray(new Player[onlinePlayers.size()]);



			Player[]  offers= getOfferPlayer.toArray(new Player[getOfferPlayer.size()]);





			menuPanel.playMain(onlines,offers);





			break;

		}



		case OFFER_FROM_OTHER:{//41+player: オファー受付,40+player: オファーキャンセル受付,42: オファー配列クリア



			String str=msg.substring(2);



			Player player=null;





			if(msg.charAt(1)=='1') {//オファー受付

				player=new Player(str);

				getOfferPlayer.add(player);

				System.out.println("オファー受付 from "+player.getID());

			}

			else if(msg.charAt(1)=='0') {//オファーキャンセル受付

				player=new Player(str);



				for(int i=0;i<getOfferPlayer.size();i++) {

					if(player.getID().equals(getOfferPlayer.get(i).getID())) {

						getOfferPlayer.remove(i);

						System.out.println("オファーキャンセル受付 from "+player.getID());

						break;

					}

				}



			}



			else if(msg.charAt(1)=='2') {

				getOfferPlayer.clear();

			}





			if(menuPanel.getScreenIsPlayMain()==1) {

				sendMessage("31");//更新ついでにnow playerも更新

			}



			break;

		}



		case REPLY_FROM_OTHER:{//500+playerキャンセル,511or512+player:マッチング成立 520+player:受理したが相手が切断





			String str=msg.substring(3);



			Player player=new Player(str);



			if(msg.substring(0,3).equals("500")) {

				//オファーキャンセルなどのエラー、onlinePlayerに戻る

				menuPanel.opponentCancel(0,player.getID());

			}

			else if(msg.substring(0,3).equals("511")) {//811:黒

				//マッチング成立、オセロパネルに遷移



				othelloPanel=new OthelloPanel(1,this,my,player);

				changePanel(1,2);

			}

			else if(msg.substring(0,3).equals("512")) {//812:白

				//マッチング成立、オセロパネルに遷移



				othelloPanel=new OthelloPanel(2,this,my,player);

				changePanel(1,2);

			}

			else if(msg.substring(0,3).equals("520")) {

				menuPanel.opponentCancel(1,player.getID());

			}



			break;

		}


		case WHERE_PUT:{

			//receiveHandler=0;

	        if(othelloPanel.turn==1 && othelloPanel.oB.bw==2) {

	        	othelloPanel.turn=2;

	        }else {

	        	//othelloPanel.turn=1;

	        }





	        if(othelloPanel.turn==1) {

				System.out.println("白が"+msg+"万を受信");

	        }else {

				System.out.println("黒が"+msg+"万を受信");

	        }



	        char[] rivalInfo=msg.toCharArray();

	        //			        System.out.println(rivalInfo[0]+"万");//turn

	        //			        System.out.println(rivalInfo[1]+"万");//i

	        //			        System.out.println(rivalInfo[2]+"万");//j

	        int[] InfoNum=new int[3];

	     //   InfoNum[1]=Character.getNumericValue(rivalInfo[1]);

	        InfoNum[1]=Character.getNumericValue(rivalInfo[1]);

	        InfoNum[2]=Character.getNumericValue(rivalInfo[2]);

	   //     System.out.println(InfoNum[1]);//turn

	        //			        System.out.println(InfoNum[1]);//i

	        //			        System.out.println(InfoNum[2]);//j

	        //			        System.out.println(othelloPanel.oB.bw+"万");

	 //       othelloPanel.turn=InfoNum[1];



	        othelloPanel.rivalI=InfoNum[1];

	        othelloPanel.rivalJ=InfoNum[2];

	        othelloPanel.waiting();




			break;

		}





		case OPPONENT_DISCONNECT:{

			if(othelloPanel.oB.bw==1) {

				othelloPanel.oB.giveUp(2);

			}else {

				othelloPanel.oB.giveUp(1);

			}

            othelloPanel.jend.setEnabled(true);//対局終了時にtrueに変更
            othelloPanel.giveUp.setEnabled(false);//対局終了時にtrueに変更
            othelloPanel.pass.setEnabled(false);//対局終了時にtrueに変更
            othelloPanel.whichTurn.setText("相手が切断しました。あなたの勝ちです");


			break;

		}


		case GAME_FINISH:{

			receiveHandler=0;



			my=new Player(msg.substring(1));



			menuPanel=new MenuPanel(this,my);



			changePanel(2,1);



			menuPanel.menuScreen();

		}



		}

	}



	public void changePanel(int i,int j) {//引数: from,to

		if(i==0) {
			mainPanel.removeAll();
			this.remove(mainPanel);

		}else if(i==1) {
			menuPanel.removeAll();
			this.remove(menuPanel);

		}else if(i==2) {
			othelloPanel.removeAll();
			this.remove(othelloPanel);

		}





		if(j==0) {

			this.add(mainPanel,0);

			mainPanel.setLayout(null);

			mainPanel.setVisible(true);

			mainPanel.mainScreen();

			this.revalidate();

		}else if(j==1) {

			this.add(menuPanel,0);

			menuPanel.setLayout(null);

			menuPanel.setVisible(true);

			this.revalidate();



		}else if(j==2) {

			this.add(othelloPanel,0);

			othelloPanel.setVisible(true);

			othelloPanel.setLayout(null);

			this.revalidate();

		}





	}



	public void removeOfferPlayer(String id) {

		for(int i=0;i<getOfferPlayer.size();i++) {

			if(getOfferPlayer.get(i).getID().equals(id)) {

				getOfferPlayer.remove(i);

				break;

			}

		}

	}







	public static void main(String[] args) {

		Client client=new Client();

		client.setDefaultCloseOperation(EXIT_ON_CLOSE);

		client.setVisible(true);

		client.setResizable(false);



		BufferedImage iconImage=null;



		try {

			iconImage=ImageIO.read(new File("./icon.png"));

		}catch(Exception e) {

			e.printStackTrace();

			iconImage=null;

		}



		client.setIconImage(iconImage);



		String ipAddress=args[0];

		int port=Integer.parseInt(args[1]);



		client.connectServer(ipAddress,port);



	}



}