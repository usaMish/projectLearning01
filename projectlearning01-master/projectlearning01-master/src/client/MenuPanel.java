package client;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class MenuPanel extends JPanel{
	private Client client;
	private Player my;

	private Font f=new Font("Arial",Font.PLAIN,50);

	private int screenIsPlayMain=0;//0:false,1:true


	MenuPanel(Client cl,Player my){
		client=cl;
		//this.setName(name);
		this.my=my;

	}

	private void background() {//簡単のために背景表示のみメソッドを分割


		JLabel bg = new JLabel();

		bg.setOpaque(true);
		bg.setBounds(0,0,1500,1000);
		bg.setBackground(new Color(34,139,34));

		add(bg,0);

	}

	private void backToMenu(int i) {//0: resultから,1:onlineから
		JButton backToMain=new JButton("Back to Menu");

		backToMain.setBounds(50,900,400,50);
		backToMain.setFont(f);
		backToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//login2_button_click_event
				menuScreen();
				//repaint();

				if(i==1) {
					client.sendMessage("30");//オンラインからメニュー画面にもどったことを送信
				}

				client.receiveHandler(0);//データ要求キャンセル

			}
		});

		add(backToMain,0);
	}

	private void backToPlayMain() {
		JButton backToPlayMain=new JButton("Back");

		backToPlayMain.setBounds(50,900,400,50);
		backToPlayMain.setFont(f);
		backToPlayMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//login2_button_click_event

				reloadPlayMain();
			}
		});

		add(backToPlayMain,0);
	}

	private void cancelOffer(int i,String id) {//0:自分がキャンセル, 1:相手にキャンセルされる
		JButton cancelOffer=new JButton("Back");

		cancelOffer.setBounds(50,900,400,50);
		cancelOffer.setFont(f);
		cancelOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//login2_button_click_event

				if(i==0) {
					client.sendMessage("40"+id);
				}

				reloadPlayMain();
			}
		});

		add(cancelOffer,0);

	}


	public void ruleScreen() {
		removeAll();
		background();


		JLabel ruleBg = new JLabel();

		ruleBg.setOpaque(true);
		ruleBg.setBounds(0,125,1500,750);
		ruleBg.setBackground(Color.BLACK);

		JTextPane rule=new JTextPane();


		rule.setText("～ルール説明～\nここで行うゲームは基本的なオセロのルールと何も変わりません。"
					+ "\nただし、以下の点には注意してください。"
					+ "\n・対戦する際、先に接続した方が黒となり先手となります。"
					+ "\n・ここでは1手打つ際に制限時間があります。制限時間を過ぎると、"
					+ "\n打つことが可能な場所にランダムに打たれます。"
					+ "\n・“パス”ボタンを押すと制限時間を過ぎたとき同様、ランダムに打たれます。"
					+ "\n・“投了”は負け扱いとなりますが、投了数としても戦績に残されます。");

		rule.setOpaque(false);
		rule.setBounds(150,250,1300,500);
		rule.setFont(new Font("MS Gothic",Font.PLAIN,35));
		rule.setForeground(Color.YELLOW);


		JButton close=new JButton("close");

		close.setBounds(50,900,400,50);
		close.setFont(f);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				menuScreen();
			}
		});

		add(ruleBg,0);
		add(rule,0);
		add(close,0);

		repaint();
	}

	public void menuScreen() {//メニュ－画面
		screenIsPlayMain=0;


		JButton play=new JButton("対局する");
		JButton search=new JButton("player検索");
		JButton signout=new JButton("Sign Out");
		JLabel playerID=new JLabel("ID: "+my.getID()+" さん");
		JLabel playerResult=new JLabel("勝: "+my.getWin()+" 負: "+my.getLose()+" 分: "+my.getDraw()+" 投了: "+my.getGiveUp());

		removeAll();

		background();

		playerID.setHorizontalAlignment(JLabel.CENTER);
		playerID.setBounds(0,50,1500,50);
		playerID.setFont(new Font("MS Gothic",Font.PLAIN,50));
		playerID.setForeground(Color.YELLOW);

		playerResult.setHorizontalAlignment(JLabel.CENTER);
		playerResult.setBounds(0,100,1500,50);
		playerResult.setFont(new Font("MS Gothic",Font.PLAIN,50));
		playerResult.setForeground(Color.YELLOW);

		play.setBounds(550,300,400,100);
		play.setFont(new Font("MS Gothic",Font.PLAIN,50));
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				client.sendMessage("31");//サーバにメッセージ送信

				client.receiveHandler(1);
			}
		});

		search.setBounds(550,500,400,100);
		search.setFont(new Font("MS Gothic",Font.PLAIN,50));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				search();
			}
		});

		signout.setBounds(50,900,400,50);
		signout.setFont(new Font("MS Gothic",Font.PLAIN,50));
		signout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				client.sendMessage("10");
				client.changePanel(1,0);
			}
		});

		add(playerID,0);
		add(playerResult,0);
		add(play,0);
		add(search,0);
		add(signout,0);

		repaint();
	}

	private void search() {//探したいプレイヤーの入力画面

		JLabel menuBg = new JLabel();
		JLabel msg=new JLabel("検索したいplayerIDを入力してください。");
		JTextField searchID=new JTextField(16);
		JButton search=new JButton("検索");
		JLabel errorMsg=new JLabel("");

		removeAll();

		background();
		backToMenu(0);

		menuBg.setOpaque(true);
		menuBg.setBounds(0,125,1500,750);
		menuBg.setBackground(Color.BLACK);

		msg.setHorizontalAlignment(JLabel.CENTER);
		msg.setBounds(500,200,500,50);
		msg.setFont(new Font("MS Gothic",Font.PLAIN,25));
		msg.setForeground(Color.WHITE);

		searchID.setBounds(550,300,400,50);
		searchID.setFont(f);

		search.setBounds(550,400,400,50);
		search.setFont(new Font("MS Gothic",Font.PLAIN,50));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				String input_id;

				if(searchID.getText().equals("")) {
					errorMsg.setText("idを入力してください。");
				}else{
					errorMsg.setText("");
					input_id=searchID.getText();

					client.sendMessage("2"+input_id);//サーバにメッセージ送信

					errorMsg.setText("サーバと通信中・・・");

					client.receiveHandler(1);//データ要求

					remove(search);
					repaint();
				}
			}
		});

		errorMsg.setHorizontalAlignment(JLabel.CENTER);
		errorMsg.setBounds(500,700,500,50);
		errorMsg.setFont(new Font("MS Gothic",Font.PLAIN,25));
		errorMsg.setForeground(Color.RED);

		add(menuBg,0);
		add(msg,0);
		add(searchID,0);
		add(search,0);
		add(errorMsg,0);

		repaint();
	}

	public void results(Player player,String[] s) {//戦績表示　引数にplayer型の配列

		JLabel menuBg = new JLabel();

		menuBg.setOpaque(true);
		menuBg.setBounds(0,125,1500,750);
		menuBg.setBackground(Color.BLACK);

		JLabel playerID=new JLabel("ID: "+player.getID()+" さん(直近３０戦表示)");

		playerID.setHorizontalAlignment(JLabel.CENTER);
		playerID.setBounds(0,50,1500,50);
		playerID.setFont(new Font("MS Gothic",Font.PLAIN,50));
		playerID.setForeground(Color.YELLOW);

		JLabel playerResult=new JLabel("勝: "+player.getWin()
			+" 負: "+player.getLose()+" 分: "+player.getDraw()
			+" 投了: "+player.getGiveUp());

		playerResult.setHorizontalAlignment(JLabel.CENTER);
		playerResult.setBounds(0,100,1500,50);
		playerResult.setFont(new Font("MS Gothic",Font.PLAIN,50));
		playerResult.setForeground(Color.YELLOW);

		JPanel resultArea=new JPanel();
	    resultArea.setLayout(null);

		JLabel historyColumn1;
		JLabel historyColumn2;

		JLabel[] historyID=new JLabel[s.length];
		JLabel[] historyResult=new JLabel[s.length];

		JScrollPane scrollpane = new JScrollPane(resultArea);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getVerticalScrollBar().setUnitIncrement(25);//スクローススピード

		scrollpane.setBounds(50,250,1500-105,625);
		scrollpane.setBorder(null);//枠線消す

		removeAll();
		background();
		backToMenu(0);

		resultArea.setBackground(Color.GRAY);
		resultArea.setPreferredSize(new  Dimension(750,50*(s.length)+1));//Task数に合わせてPanelのサイズを変更する

		historyColumn1=new JLabel("対戦相手のID");
		historyColumn1.setHorizontalAlignment(JLabel.CENTER);
		historyColumn1.setFont(new Font("MS Gothic",Font.PLAIN,40));
		historyColumn1.setBounds(50,200,700,50);
		historyColumn1.setForeground(Color.BLACK);
		historyColumn1.setBackground(Color.BLUE);
        historyColumn1.setOpaque(true);

		historyColumn2=new JLabel("勝敗");
		historyColumn2.setHorizontalAlignment(JLabel.CENTER);
		historyColumn2.setFont(new Font("MS Gothic",Font.PLAIN,40));
		historyColumn2.setBounds(750,200,700-5,50);
		historyColumn2.setForeground(Color.BLACK);
		historyColumn2.setBackground(Color.RED);
        historyColumn2.setOpaque(true);

		for(int i=0;i<s.length;i++){

	        String name=s[i].split("/")[0];
	        String r=s[i].split("/")[1];
	        String str=null;

	        //win=00,lose=01,draw=10,give up=11
	        if(r.equals("00")) {
	        	str="勝利";
	        }
	        else if(r.equals("01")) {
	        	str="敗北";
	        }
	        else if(r.equals("10")) {
	        	str="引き分け";
	        }
	        else if(r.equals("11")) {
	        	str="投了";
	        }


	        historyID[i]=new JLabel(name);
	        historyID[i].setHorizontalAlignment(JLabel.CENTER);
	        historyID[i].setFont(new Font("MS Gothic",Font.PLAIN,40));
	        historyID[i].setBounds(0,0+(50*(i)),700,50);
	        historyID[i].setForeground(Color.BLACK);

	        if((i%2)==0) {
	        	historyID[i].setBackground(new Color(0,204,255));
	        }else {
	        	historyID[i].setBackground(new Color(204,255,255));
	        }

	        historyID[i].setOpaque(true);


	        historyResult[i]=new JLabel(str);
	        historyResult[i].setHorizontalAlignment(JLabel.CENTER);
	        historyResult[i].setFont(new Font("MS Gothic",Font.PLAIN,40));
	        historyResult[i].setBounds(700,0+(50*(i)),800-105,50);
	        historyResult[i].setForeground(Color.BLACK);

	        if((i%2)==0) {
		        historyResult[i].setBackground(new Color(255,153,204));
	        }else {
		        historyResult[i].setBackground(Color.PINK);
	        }

	        historyResult[i].setOpaque(true);

			resultArea.add(historyID[i]);
			resultArea.add(historyResult[i]);

		}


		add(playerID,0);
		add(playerResult,0);
		add(historyColumn1,0);
		add(historyColumn2,0);
		add(scrollpane,0);

		revalidate();

		repaint();

	}

	public void searchError() {
		removeAll();
		background();
		backToMenu(0);

		JLabel errorMsg=new JLabel("検索されたIDのplayerは存在しません。");


		errorMsg.setHorizontalAlignment(JLabel.CENTER);
		errorMsg.setBounds(500,200,500,50);
		errorMsg.setFont(new Font("MS Gothic",Font.PLAIN,25));
		errorMsg.setForeground(Color.RED);

		add(errorMsg,0);

		repaint();
	}

	public void playMain(Player[] onlinePlayers,Player[] offerPlayers) {//オンラインプレイヤーの表示
		screenIsPlayMain=1;

		removeAll();
		background();
		backToMenu(1);

		client.receiveHandler(1);//この画面では、常にオファーを受信するため

		JLabel title=new JLabel("現在オンラインのプレイヤー");

		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(0,50,1500,50);
		title.setFont(new Font("MS Gothic",Font.PLAIN,50));
		title.setForeground(Color.BLACK);

		JLabel titleMsg=new JLabel("対戦を申し込みたいplayerIDをクリックしてください。");

		titleMsg.setHorizontalAlignment(JLabel.CENTER);
		titleMsg.setBounds(0,100,1500,50);
		titleMsg.setFont(new Font("MS Gothic",Font.PLAIN,30));
		titleMsg.setForeground(Color.WHITE);

		JButton getOffer=new JButton("オファーが来た場合はここに表示されます。");//offer来た時に表示されるオファー承認画面への遷移ボタン

		getOffer.setHorizontalAlignment(JLabel.CENTER);
		getOffer.setBounds(750,900,700,50);
		getOffer.setFont(new Font("MS Gothic",Font.PLAIN,30));
		getOffer.setBackground(Color.WHITE);

		if(offerPlayers.length>0) {
			getOffer.setText(offerPlayers.length+" 件の対局オファー");


			getOffer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {//login_button_click_event
					offerScreen(offerPlayers);
				}
			});
		}

		BufferedImage reloadImage=null;

		try {
			reloadImage=ImageIO.read(new File("./reload.png"));
		}catch(Exception e) {
			e.printStackTrace();
			reloadImage=null;
		}

		//ImageIcon icon = new ImageIcon("./reload.png");

		JButton reload=new JButton(new ImageIcon(reloadImage));//更新ボタン

		reload.setBounds(50,50,100,100);
		//reload.setContentAreaFilled(false);
		reload.setBackground(Color.WHITE);
		reload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//login_button_click_event
				reloadPlayMain();
			}
		});

		JPanel playerArea=new JPanel();
		playerArea.setLayout(null);

		JButton[] onlineID=new JButton[onlinePlayers.length];
		JLabel[] onlineResult=new JLabel[onlinePlayers.length];

		JLabel onlineColumn1;
		JLabel onlineColumn2;

		JScrollPane scrollpane = new JScrollPane(playerArea);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getVerticalScrollBar().setUnitIncrement(25);//スクローススピード

		scrollpane.setBounds(50,250,1500-105,625);
		scrollpane.setBorder(null);//枠線消す



		playerArea.setBackground(Color.GRAY);
		playerArea.setPreferredSize(new  Dimension(750,50*(onlinePlayers.length)+1));//Task数に合わせてPanelのサイズを変更する


		onlineColumn1=new JLabel("対戦相手のID");
		onlineColumn1.setHorizontalAlignment(JLabel.CENTER);
		onlineColumn1.setFont(new Font("MS Gothic",Font.PLAIN,40));
		onlineColumn1.setBounds(50,200,700,50);
		onlineColumn1.setForeground(Color.BLACK);
		onlineColumn1.setBackground(Color.BLUE);
		onlineColumn1.setOpaque(true);

		onlineColumn2=new JLabel("勝敗");
		onlineColumn2.setHorizontalAlignment(JLabel.CENTER);
		onlineColumn2.setFont(new Font("MS Gothic",Font.PLAIN,40));
		onlineColumn2.setBounds(750,200,700-5,50);
		onlineColumn2.setForeground(Color.BLACK);
		onlineColumn2.setBackground(Color.RED);
		onlineColumn2.setOpaque(true);

		for(int i=0;i<onlinePlayers.length;i++){

	        onlineID[i]=new JButton(onlinePlayers[i].getID());
	        onlineID[i].setHorizontalAlignment(JLabel.CENTER);
	        onlineID[i].setFont(new Font("MS Gothic",Font.PLAIN,40));
	        onlineID[i].setBounds(0,0+(50*(i)),700,50);
	        onlineID[i].setForeground(Color.BLACK);

	        if((i%2)==0) {
	        	onlineID[i].setBackground(new Color(0,204,255));
	        }else {
	        	onlineID[i].setBackground(new Color(204,255,255));
	        }

	        onlineID[i].setOpaque(true);


	        onlineResult[i]=new JLabel("勝: "+onlinePlayers[i].getWin()+" 負: "+onlinePlayers[i].getLose()+" 分: "+onlinePlayers[i].getDraw()+" 投了: "+onlinePlayers[i].getGiveUp());
	        onlineResult[i].setHorizontalAlignment(JLabel.CENTER);
	        onlineResult[i].setFont(new Font("MS Gothic",Font.PLAIN,40));
	        onlineResult[i].setBounds(700,0+(50*(i)),800-105,50);
	        onlineResult[i].setForeground(Color.BLACK);

	        if((i%2)==0) {
	        	onlineResult[i].setBackground(new Color(255,153,204));
	        }else {
	        	onlineResult[i].setBackground(Color.PINK);
	        }

	        onlineResult[i].setOpaque(true);

	        onlineID[i].addActionListener(new clickPlayID());//actionlister clickIDへ飛ばす

	        playerArea.add(onlineID[i]);
	        playerArea.add(onlineResult[i]);

		}

		add(reload,0);
		add(title,0);
		add(titleMsg,0);
		add(getOffer,0);
		add(onlineColumn1,0);
		add(onlineColumn2,0);
		add(scrollpane,0);

		revalidate();

		repaint();

	}

	private void reloadPlayMain(){//明示化するためにわざとこのメソッド追加
		client.sendMessage("31");//サーバにメッセージ送信

	}

	private void playSelect(String id) {//オファーを送るかの画面
		screenIsPlayMain=0;

		removeAll();
		background();

		JLabel playerID=new JLabel(id);
		JLabel msg=new JLabel("さんに対戦を申し込みますか？");

		JButton yes=new JButton("はい");
		JButton no=new JButton("いいえ");

		playerID.setHorizontalAlignment(JLabel.CENTER);
		playerID.setBounds(0,200,1500,100);
		playerID.setFont(new Font("MS Gothic",Font.PLAIN,75));
		playerID.setForeground(Color.YELLOW);

		msg.setHorizontalAlignment(JLabel.CENTER);
		msg.setBounds(0,400,1500,100);
		msg.setFont(new Font("MS Gothic",Font.PLAIN,50));
		msg.setForeground(Color.WHITE);

		yes.setHorizontalAlignment(JLabel.CENTER);
		yes.setBounds(300,700,300,50);
		yes.setFont(new Font("MS Gothic",Font.PLAIN,50));
		yes.setForeground(Color.BLACK);

		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				remove(playerID);
				remove(yes);
				remove(no);

				repaint();

				client.sendMessage("41"+id);

				cancelOffer(0,id);

				msg.setText(id+" さんからの返事を待っています。");

			}
		});

		no.setHorizontalAlignment(JLabel.CENTER);
		no.setBounds(800,700,300,50);
		no.setFont(new Font("MS Gothic",Font.PLAIN,50));
		no.setForeground(Color.BLACK);

		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				reloadPlayMain();
			}
		});

		add(playerID,0);
		add(msg,0);
		add(yes,0);
		add(no,0);

		repaint();
	}

	public void opponentCancel(int i,String id) {//i; 0:送ったオファーがキャンセルされた 1:オファー受理したけど相手がキャンセルしてた
		removeAll();
		background();
		cancelOffer(1,"");

		JLabel msg=new JLabel();

		if(i==0) {
			msg.setText(id+" さんに送信したオファーはキャンセルされました。");
		}
		else if(i==1) {
			msg.setText(id+" さんは送信したオファーをキャンセルしました。");
		}

		msg.setHorizontalAlignment(JLabel.CENTER);
		msg.setBounds(0,400,1500,100);
		msg.setFont(new Font("MS Gothic",Font.PLAIN,50));
		msg.setForeground(Color.RED);

		add(msg,0);
		repaint();
	}

	public void offerScreen(Player[] offerPlayers) {//受信したオファー一覧の表示
		screenIsPlayMain=1;

		removeAll();
		background();
		backToPlayMain();

		JLabel title=new JLabel("オファーを受信したプレイヤー");

		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(0,50,1500,50);
		title.setFont(new Font("MS Gothic",Font.PLAIN,50));
		title.setForeground(Color.BLACK);

		JLabel titleMsg=new JLabel("オファーを受理したいplayerIDをクリックしてください。");

		titleMsg.setHorizontalAlignment(JLabel.CENTER);
		titleMsg.setBounds(0,100,1500,50);
		titleMsg.setFont(new Font("MS Gothic",Font.PLAIN,30));
		titleMsg.setForeground(Color.WHITE);

		JPanel playerArea=new JPanel();
		playerArea.setLayout(null);

		JButton[] onlineID=new JButton[offerPlayers.length];
		JLabel[] onlineResult=new JLabel[offerPlayers.length];

		JLabel onlineColumn1;
		JLabel onlineColumn2;

		JScrollPane scrollpane = new JScrollPane(playerArea);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getVerticalScrollBar().setUnitIncrement(25);//スクローススピード

		scrollpane.setBounds(50,250,1500-105,625);
		scrollpane.setBorder(null);//枠線消す

		playerArea.setBackground(Color.GRAY);
		playerArea.setPreferredSize(new  Dimension(750,50*(offerPlayers.length)+1));//Task数に合わせてPanelのサイズを変更する


		onlineColumn1=new JLabel("対戦相手のID");
		onlineColumn1.setHorizontalAlignment(JLabel.CENTER);
		onlineColumn1.setFont(new Font("MS Gothic",Font.PLAIN,40));
		onlineColumn1.setBounds(50,200,700,50);
		onlineColumn1.setForeground(Color.BLACK);
		onlineColumn1.setBackground(Color.BLUE);
		onlineColumn1.setOpaque(true);

		onlineColumn2=new JLabel("勝敗");
		onlineColumn2.setHorizontalAlignment(JLabel.CENTER);
		onlineColumn2.setFont(new Font("MS Gothic",Font.PLAIN,40));
		onlineColumn2.setBounds(750,200,700-5,50);
		onlineColumn2.setForeground(Color.BLACK);
		onlineColumn2.setBackground(Color.RED);
		onlineColumn2.setOpaque(true);

		for(int i=0;i<offerPlayers.length;i++){

	        onlineID[i]=new JButton(offerPlayers[i].getID());
	        onlineID[i].setHorizontalAlignment(JLabel.CENTER);
	        onlineID[i].setFont(new Font("MS Gothic",Font.PLAIN,40));
	        onlineID[i].setBounds(0,0+(50*(i)),700,50);
	        onlineID[i].setForeground(Color.BLACK);

	        if((i%2)==0) {
	        	onlineID[i].setBackground(new Color(0,204,255));
	        }else {
	        	onlineID[i].setBackground(new Color(204,255,255));
	        }

	        onlineID[i].setOpaque(true);


	        onlineResult[i]=new JLabel("勝: "+offerPlayers[i].getWin()+" 負: "+offerPlayers[i].getLose()+" 分: "+offerPlayers[i].getDraw()+" 投了: "+offerPlayers[i].getGiveUp());
	        onlineResult[i].setHorizontalAlignment(JLabel.CENTER);
	        onlineResult[i].setFont(new Font("MS Gothic",Font.PLAIN,40));
	        onlineResult[i].setBounds(700,0+(50*(i)),800-105,50);
	        onlineResult[i].setForeground(Color.BLACK);

	        if((i%2)==0) {
	        	onlineResult[i].setBackground(new Color(255,153,204));
	        }else {
	        	onlineResult[i].setBackground(Color.PINK);
	        }

	        onlineResult[i].setOpaque(true);

	        onlineID[i].addActionListener(new clickOfferID());//actionlister clickIDへ飛ばす

	        playerArea.add(onlineID[i]);
	        playerArea.add(onlineResult[i]);

		}

		add(title,0);
		add(titleMsg,0);
		add(onlineColumn1,0);
		add(onlineColumn2,0);
		add(scrollpane,0);

		revalidate();

		repaint();

	}


	private void offerSelect(String id) {//オファーを受理するかの画面
		screenIsPlayMain=0;

		removeAll();
		background();

		JLabel playerID=new JLabel(id);
		JLabel msg=new JLabel("さんの対戦オファーを受理しますか？");

		JButton yes=new JButton("はい");
		JButton no=new JButton("いいえ");

		playerID.setHorizontalAlignment(JLabel.CENTER);
		playerID.setBounds(0,200,1500,100);
		playerID.setFont(new Font("MS Gothic",Font.PLAIN,75));
		playerID.setForeground(Color.YELLOW);

		msg.setHorizontalAlignment(JLabel.CENTER);
		msg.setBounds(0,400,1500,100);
		msg.setFont(new Font("MS Gothic",Font.PLAIN,50));
		msg.setForeground(Color.WHITE);

		yes.setHorizontalAlignment(JLabel.CENTER);
		yes.setBounds(300,700,300,50);
		yes.setFont(new Font("MS Gothic",Font.PLAIN,50));
		yes.setForeground(Color.BLACK);

		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event

				removeAll();//一応ボタン連続でほかのおさないように

				client.sendMessage("51"+id);
				//オファー受理して対戦
				System.out.println(id+"さんのオファー受理");

			}
		});

		no.setHorizontalAlignment(JLabel.CENTER);
		no.setBounds(800,700,300,50);
		no.setFont(new Font("MS Gothic",Font.PLAIN,50));
		no.setForeground(Color.BLACK);

		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//_click_event
				removeAll();//一応ボタン連続でほかのおさないように

				client.sendMessage("50"+id);
				client.removeOfferPlayer(id);

				reloadPlayMain();
				//オファーを拒否
				System.out.println(id+"さんのオファー拒否");
			}
		});

		add(playerID,0);
		add(msg,0);
		add(yes,0);
		add(no,0);

		repaint();
	}

	public int getScreenIsPlayMain() {
		return screenIsPlayMain;
	}

	public void setScreenIsPlayMain(int i) {
		screenIsPlayMain=i;
	}

	private class clickPlayID implements ActionListener{//idをクリックしたときのアクションイベント
		 public void actionPerformed(ActionEvent e) {
			 playSelect(e.getActionCommand());
		 }
	}

	private class clickOfferID implements ActionListener{//idをクリックしたときのアクションイベント
		 public void actionPerformed(ActionEvent e) {
			 offerSelect(e.getActionCommand());
		 }
	}

}
