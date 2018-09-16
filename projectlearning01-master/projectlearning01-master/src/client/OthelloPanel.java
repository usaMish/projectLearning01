package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class OthelloPanel extends JPanel implements MouseListener,MouseMotionListener,ActionListener,Runnable {
	public static final String TIME_LIMIT ="10";
    private static JButton buttonArray[][];//ボタン用の配列
    private static JButton theButton;
    JButton giveUp;
    JButton pass;
    JButton whichTurn;
    JTextField jtext;
    JButton jstart;
    JButton jend;
    Thread th=null;
    static int turn;//手番
    static int rivalI; //相手のi
    static int rivalJ; //相手のj
    Player my;
    Player player;
    //private static JLabel win;
    private static JLabel blackData01;
    private static JLabel blackData02;
    private static JLabel blackData03;
    private static JLabel blackData04;
    private static JLabel blackData05;
    private static JLabel whiteData01;
    private static JLabel whiteData02;
    private static JLabel whiteData03;
    private static JLabel whiteData04;
    private static JLabel whiteData05;
    private static JLabel Tlimit;
    private static ImageIcon blackIcon;
	private static ImageIcon whiteIcon;
	private static ImageIcon choiceIcon;
	private static ImageIcon boardIcon;
	 Client client;
    static Othello oB;


    public  OthelloPanel(int a, Client cl,Player p1,Player p2) {
        cl.setSize(1500,1000);        //ウィンドウのサイズを設定する
       this.client=cl;
   	oB =new Othello(a,cl);
       //アイコンの設定
        whiteIcon = new ImageIcon("./White.jpg");
        blackIcon = new ImageIcon("./Black.jpg");
        boardIcon = new ImageIcon("./GreenFrame.jpg");
        choiceIcon = new ImageIcon("./yg.gif");
        //ボタンの生成
        buttonArray = new JButton[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
            	if(oB.othello[i][j]==1) {

            buttonArray[i][j] = new JButton(blackIcon);//ボタンにアイコンを設定する

            	}else if(oB.othello[i][j]==2) {

                    buttonArray[i][j] = new JButton(whiteIcon);

            	}else {

                    buttonArray[i][j] = new JButton(boardIcon);

            	}

            buttonArray[i][j].setBounds(550+i*50,50+j*50,50,50);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）

            buttonArray[i][j].addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする

            buttonArray[i][j].addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする

            buttonArray[i][j].setActionCommand(Integer.toString(i)+Integer.toString(j));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）

            add(buttonArray[i][j]);//ペインに貼り付ける

        }

        }

        this.my=p1;

        this.player=p2;



        int sum1,sum2;

        int lose1,lose2;

        oB.passEnd=0;
        oB.endJudge=0;
        oB.end=0;

        sum1=Integer.parseInt(this.my.getWin())+Integer.parseInt(this.my.getDraw())+Integer.parseInt(this.my.getLose())+Integer.parseInt(this.my.getGiveUp());

        sum2=Integer.parseInt(this.player.getWin())+Integer.parseInt(this.player.getDraw())+Integer.parseInt(this.player.getLose())+Integer.parseInt(this.player.getGiveUp());

        lose1=Integer.parseInt(this.my.getLose())+Integer.parseInt(this.my.getGiveUp());

        lose2=Integer.parseInt(this.player.getLose())+Integer.parseInt(this.player.getGiveUp());


        if(oB.bw==1) {
        blackData01=new JLabel("黒");
        }else {
            blackData01=new JLabel("白");
        }

        blackData01.setFont(new Font("MS Gothic",Font.PLAIN,36));

        blackData01.setBounds(350,50,100,50);

        add(blackData01);



        blackData02=new JLabel("プレイヤ名");

        blackData02.setFont(new Font("MS Gothic",Font.PLAIN,14));

        blackData02.setBounds(350,100,100,50);

        add(blackData02);



        blackData03=new JLabel(this.my.getID());

        blackData03.setFont(new Font("MS Gothic",Font.PLAIN,20));

        blackData03.setBounds(350,120,100,50);

        add(blackData03);



        blackData04=new JLabel("戦績");

        blackData04.setFont(new Font("MS Gothic",Font.PLAIN,14));

        blackData04.setBounds(350,180,100,50);

        add(blackData04);



        blackData05=new JLabel(sum1+"戦 "+this.my.getWin()+"勝 "+lose1+"敗 "+this.my.getDraw()+"分");

        blackData05.setFont(new Font("MS Gothic",Font.PLAIN,20));

        blackData05.setBounds(350,200,300,50);

        add(blackData05);





        if(oB.bw==1) {
            whiteData01=new JLabel("白");
        }else {
            whiteData01=new JLabel("黒");
        }



        whiteData01.setFont(new Font("MS Gothic",Font.PLAIN,36));

        whiteData01.setBounds(1000,50,100,50);

        add(whiteData01);



        whiteData02=new JLabel("プレイヤ名");

        whiteData02.setFont(new Font("MS Gothic",Font.PLAIN,14));

        whiteData02.setBounds(1000,100,100,50);

        add(whiteData02);



        whiteData03=new JLabel(this.player.getID());

        whiteData03.setFont(new Font("MS Gothic",Font.PLAIN,20));

        whiteData03.setBounds(1000,120,100,50);

        add(whiteData03);



        whiteData04=new JLabel("戦績");

        whiteData04.setFont(new Font("MS Gothic",Font.PLAIN,14));

        whiteData04.setBounds(1000,180,100,50);

        add(whiteData04);



        whiteData05=new JLabel(sum2+"戦 "+this.player.getWin()+"勝 "+lose2+"敗 "+this.player.getDraw()+"分");

        whiteData05.setFont(new Font("MS Gothic",Font.PLAIN,20));

        whiteData05.setBounds(1000,200,300,50);

        add(whiteData05);









        giveUp=new JButton("投了");

        giveUp.setBounds(350,500,300,50);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）

        giveUp.addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする

        giveUp.addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする

        giveUp.setActionCommand("0");//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）

        add(giveUp);



        pass=new JButton("パス");

        pass.setBounds(850,500,300,50);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）

        pass.addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする

        pass.addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする

        pass.setActionCommand("1");//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）

        add(pass);





        jend=new JButton("終了");

        jend.setBounds(50,600,100,50);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）

        jend.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {//_click_event

                client.sendMessage("8");

                client.changePanel(2,1);

            }

        });

        jend.setEnabled(false);//対局終了時にtrueに変更

        jend.setActionCommand("4");

        add(jend);







        jtext=new JTextField(TIME_LIMIT);

        jtext.setBounds(700,500,100,50);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）

		jtext.setFont(new Font("SanSerif",Font.ITALIC,30));

		jtext.setHorizontalAlignment(jtext.CENTER);

        //jtext.setFont(new Font("MS Gothic",Font.PLAIN,24));

        add(jtext);





        Tlimit=new JLabel("制限時間");

        Tlimit.setFont(new Font("MS Gothic",Font.PLAIN,14));

        Tlimit.setBounds(700,450,100,50);

		Tlimit.setHorizontalAlignment(Tlimit.CENTER);

        add(Tlimit);

//    }

//

//    public static void main(String[] args) {

    	turn=1;





        if(oB.bw==1) {

        whichTurn=new JButton("あなたの手番です");

        }else {

            whichTurn=new JButton("相手の手番です");

            giveUp.setEnabled(false);

            pass.setEnabled(false);

        }

        whichTurn.setBounds(550,600,400,50);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）

        whichTurn.setEnabled(false);

        add(whichTurn);

        if(oB.bw==1) {
    	oB.choiceB();
		if(oB.randJudge>0) {
	        pass.setEnabled(false);
		}else {
			whichTurn.setText("おける場所がありません。パスしてください");
		}
        }


    	oB.print();

        apply();



        if(oB.bw==1) {

		System.out.println("a");//タイマースタート

		if(th==null) {

			th=new Thread(this);

			th.start();

		}

        }

		System.out.println(oB.bw);



    	System.out.println("オセロ呼び出し");

    	//if(oB.bw==turn) {

    		if(oB.bw==1) {

    			oB.choiceB();

    			if(oB.randJudge>0) {

        	        pass.setEnabled(false);

    			}

    		}

    //	}

		apply();
		repaint();


    }



//    public OthelloPanel(int a, Client cl) {

//		// TODO 自動生成されたコンストラクター・スタブ

//	}



	public void mouseClicked(MouseEvent e) {//ボタンをクリックしたときの処理

    	if(turn==oB.bw) {//自分のターンならば、

    		int theArrayIndexOfI;

    		int theArrayIndexOfJ;

    		System.out.println("クリックされた");

    		//client.sendMessage("9");//turn,i,j

    		theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする

    		String theArrayIndex = theButton.getActionCommand();//ボタンの配列の番号を取り出す

    		System.out.println(theArrayIndex);

    		char[] ArrayIndChar=theArrayIndex.toCharArray();





        if(theArrayIndex.length()>1 && oB.bw==1) {//長さが2以上すなわち、盤面のボタンがクリック

        	theArrayIndexOfI=Character.getNumericValue(ArrayIndChar[0]);//縦番地

        	theArrayIndexOfJ=Character.getNumericValue(ArrayIndChar[1]);//横番地

        	System.out.println(theArrayIndexOfI);

        	System.out.println(theArrayIndexOfJ);

            System.out.println("black");

        	if(oB.othello[theArrayIndexOfI][theArrayIndexOfJ]==4) { //黒が手を打つ

    			//oB.bw=2;//一人プレイ用

    			whichTurn.setText("相手の手番です");
        			oB.playB(theArrayIndexOfI,theArrayIndexOfJ);

        			client.sendMessage("6"+theArrayIndexOfI+theArrayIndexOfJ);//識別子i,j

    				client.receiveHandler(1);

    				oB.randJudge=0;//未定
       				oB.passEnd=0;
       				oB.endJudge=0;//passの回reset

        	        System.out.println("afterplayendJudge="+oB.endJudge);

        			clear();//choiceの消去

        			oB.print();

        			apply(); //ボタンのアイコン更新

        			oB.judge();//勝敗判定
        			if(oB.end==1) {
        				jend.setEnabled(true);
            			clear();
        				//oB.end=0;
           				if(oB.winJudge==0) {
        					whichTurn.setText("相手の勝利です");
        				}else if(oB.winJudge==1) {
        					whichTurn.setText("あなたの勝利です");
        				}else if(oB.winJudge==2) {
        					whichTurn.setText("引き分けです");
        				}
        			}






        		    giveUp.setEnabled(false);

        	        pass.setEnabled(false);



        			repaint();//画面のオブジェクトを描画し直す



            		System.out.println("n");

        			jtext.setText(TIME_LIMIT);

            		if(th!=null) {

            				th=null;

            		}



        	}//if

        }//if

        else if(theArrayIndex.length()>1 && oB.bw==2) {//白が手を打つ

            theArrayIndexOfI=Character.getNumericValue(ArrayIndChar[0]);

            theArrayIndexOfJ=Character.getNumericValue(ArrayIndChar[1]);

            System.out.println(theArrayIndexOfI);

            System.out.println(theArrayIndexOfJ);

            System.out.println("white");

            if(oB.othello[theArrayIndexOfI][theArrayIndexOfJ]==4) {
    			whichTurn.setText("相手の手番です");
            	oB.playW(theArrayIndexOfI,theArrayIndexOfJ);

    			client.sendMessage("6"+theArrayIndexOfI+theArrayIndexOfJ);

    			client.receiveHandler(1);

   				oB.randJudge=0;//未定
   				oB.passEnd=0;
   				oB.endJudge=0;

            	clear();

    	        System.out.println("afterplayendJudge="+oB.endJudge);

            	oB.print();

            	apply(); //ボタンのアイコン更新

            	oB.judge();
    			if(oB.end==1) {
    				jend.setEnabled(true);
    				clear();
    			//	oB.end=0;
       				if(oB.winJudge==0) {
    					whichTurn.setText("相手の勝利です");
    				}else if(oB.winJudge==1) {
    					whichTurn.setText("あなたの勝利です");
    				}else if(oB.winJudge==2) {
    					whichTurn.setText("引き分けです");
    				}
    			}

            	System.out.println("通過");

            	//oB.bw=1;//一人プレイ用

            	//oB.sendInf(turn,theArrayIndexOfI,theArrayIndexOfJ);





    		    giveUp.setEnabled(false);

    	        pass.setEnabled(false);



            	repaint();//画面のオブジェクトを描画し直す

        		System.out.println("n");

    			jtext.setText(TIME_LIMIT);

        		if(th!=null) {

        				th=null;

        		}



            }//if

        }//if

        else{

            int giveOrPass=Character.getNumericValue(ArrayIndChar[0]); //giveUpならintの0、passならintの1

            if(giveOrPass==0) {
            	if(giveUp.isEnabled()==true) {
                jend.setEnabled(true);//対局終了時にtrueに変更
				clear();
            	//giveのそうさ

            	if(oB.bw==1) {

            			//oB.giveUp(1);

            //		oB.endJudge=2;

            			client.sendMessage("699");

            			client.receiveHandler(1);

	        			whichTurn.setText("投了しました。");
	        			clear();
	        			apply();


            		//	win=new JLabel("白の勝利");

            		//	add(win);

            		///	win.setBounds(0,0,100,50);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）

            			repaint();

                   		System.out.println("n");

            			jtext.setText(TIME_LIMIT);

                		if(th!=null) {

                				th=null;

                		}

                        giveUp.setEnabled(false);

                        pass.setEnabled(false);

            	}else {


        			client.sendMessage("699");

        			client.receiveHandler(1);



        			whichTurn.setText("投了しました。");

        			clear();
        			apply();


        			repaint();

               		System.out.println("n");

        			jtext.setText(TIME_LIMIT);
            		if(th!=null) {
            				th=null;
            		}

                    giveUp.setEnabled(false);

                    pass.setEnabled(false);

            	}
            }

            }else if(giveOrPass==1) {
            	if(pass.isEnabled()==true) {
            	//passのそうさ
        			client.sendMessage("688");
        			client.receiveHandler(1);
        			whichTurn.setText("相手の手番です");
        		    giveUp.setEnabled(false);
        	        pass.setEnabled(false);
        			oB.endJudge=1;
	    			if(oB.passEnd==1) {
	    				jend.setEnabled(true);
	    				oB.judge_2();
	    				//対局終了。面に空きがある場合の勝敗判定
	            		if(th!=null) {
            				th=null;
            		}
	    				System.out.println(oB.bw+"winJudge="+oB.winJudge);
	       				if(oB.winJudge==0) {
	    					whichTurn.setText("相手の勝利です");
	    				}else if(oB.winJudge==1) {
	    					whichTurn.setText("あなたの勝利です");
	    				}else if(oB.winJudge==2) {
	    					whichTurn.setText("引き分けです");
	    				}
	    				clear();
	    			}
        	        System.out.println("afterPasssendJudge="+oB.endJudge);
                    apply();

                    repaint();//画面のオブジェクトを描画し直す
               		System.out.println("n");
        			jtext.setText(TIME_LIMIT);
            		if(th!=null) {
            				th=null;
            		}

            }
            }else if(giveOrPass==3){


            }else{




            }

        }

    	}//大きなif

    }





    public static void clear() {//4の消去

        for(int j=0;j<8;j++){

            for(int i=0;i<8;i++){

            	if(oB.othello[i][j]==4) {

            		oB.othello[i][j]=0;            	}

        }

        }

    }



    public static void apply() {//アイコンの適用

        for(int j=0;j<8;j++){

            for(int i=0;i<8;i++){

            	if(oB.othello[i][j]==1) {

            		 buttonArray[i][j].setIcon(blackIcon);//blackIconに設定する

            	}else if(oB.othello[i][j]==2) {

            		 buttonArray[i][j].setIcon(whiteIcon);//whiteIconに設定する

            	}else if(oB.othello[i][j]==4){

           		 	buttonArray[i][j].setIcon(choiceIcon);//whiteIconに設定する

            	}else {

            		 buttonArray[i][j].setIcon(boardIcon);//boardIconに設定する

            	}

            }

        }

    }



    public void ChangetoW() {//白への手番変更

        try{

           	Thread.sleep(300); //3000ミリ秒Sleepする



           }catch(InterruptedException e1){}



    	apply();

    }





    public void waiting() {

    	System.out.println("waitingに到達");

		whichTurn.setText("あなたの手番です");

        giveUp.setEnabled(true);

        pass.setEnabled(true);

    	System.out.println(turn);

    	System.out.println(oB.bw);


 		if(rivalI!=9) {
		System.out.println("a");//タイマースタート
		if(th==null) {
			th=new Thread(this);
			th.start();
		}
 		}else {
			jtext.setText(TIME_LIMIT);
    		if(th!=null) {
    				th=null;
    		}
 		}

    	if(turn==oB.bw) {//相手からの情報が来たとき
    	        if(oB.bw==1) {
    		        if(rivalI<8 && rivalJ<8 ) {
    	        		oB.othello[rivalI][rivalJ]=4;//受け取ったijの番地を4にする
    	        		oB.playW(rivalI,rivalJ);//playW(i,j)を実行
    	        		 oB.endJudge=0;
    	        	 		oB.judge();
    	        	 		System.out.println("で"+oB.end);
    	        			if(oB.end==1) {
    	        				jtext.setText(TIME_LIMIT);
    	        	    		if(th!=null) {
    	        	    				th=null;
    	        	    		}
    	        				jend.setEnabled(true);
    	        				clear();
    	        			//	oB.end=0;
    		                    giveUp.setEnabled(false);//対局終了時にtrueに変更
    		                    pass.setEnabled(false);//対局終了時にtrueに変更
    	        					if(oB.winJudge==0) {
    	        						whichTurn.setText("相手の勝利です");
    	        					}else if(oB.winJudge==1) {
    	        						whichTurn.setText("あなたの勝利です");
    	        					}else if(oB.winJudge==2) {
    	        						whichTurn.setText("引き分けです");
    	        					}
    	        			}

    		        }else if(rivalI==9 && rivalJ==9 ) {
	    	            jend.setEnabled(true);//対局終了時にtrueに変更
	               		System.out.println("n");
	                    giveUp.setEnabled(false);//対局終了時にtrueに変更
	                    pass.setEnabled(false);//対局終了時にtrueに変更
	        			jtext.setText(TIME_LIMIT);
	        			whichTurn.setText("相手が投了しました。あなたの勝利です");
	            		if(th!=null) {
	            				th=null;
	            		}
	      //      		oB.endJudge=2;
    		        	oB.giveUp(2);//白のギブアップ);
        				clear();
    		        }else if(rivalI==8 && rivalJ==8) {
    		        	oB.passEnd=1;
    	    			if(oB.endJudge==1) {
    	    				jend.setEnabled(true);
    	    				clear();
    	    				//対局終了。面に空きがある場合の勝敗判定
    	    				oB.judge_2();
    	            		if(th!=null) {
	            				th=null;
	            		}
    	        		    giveUp.setEnabled(false);
    	        	        pass.setEnabled(false);
    	    				System.out.println(oB.bw+"winJudge="+oB.winJudge);
    	       				if(oB.winJudge==0) {
    	    					whichTurn.setText("相手の勝利です");
    	    				}else if(oB.winJudge==1) {
    	    					whichTurn.setText("あなたの勝利です");
    	    				}else if(oB.winJudge==2) {
    	    					whichTurn.setText("引き分けです");
    	    				}
    	    			}
    		        }
    		        System.out.println("emp="+oB.emp_num);
	    	        if(oB.emp_num==0) {
           		System.out.println("n");
    			jtext.setText(TIME_LIMIT);
        		if(th!=null) {
        				th=null;
        		}
	        }
	    	        if(oB.end!=1) {
    		        if(rivalI!=9 && oB.endJudge!=1) {
    	        		oB.choiceB();
    	        		if(oB.randJudge>0) {
    	        	        pass.setEnabled(false);
    	        		}else {
    	        			whichTurn.setText("おける場所がありません。パスしてください");
    	        		}
    		        }
    	        }
    	        		apply();
    	        		repaint();

    	            }else if(oB.bw==2){
    	    	        if(rivalI<8 && rivalJ<8 ) {
    	            	oB.othello[rivalI][rivalJ]=4;//受け取ったijの番地を4にする
    	            	oB.playB(rivalI,rivalJ);//playB(i,j)を実行
      	        		 oB.endJudge=0;
      	        		oB.judge();
      	       		System.out.println("で"+oB.end);
      	      		if(oB.end==1) {
      	      			jtext.setText(TIME_LIMIT);
      	          		if(th!=null) {
      	          				th=null;
      	          		}
      	      			jend.setEnabled(true);
      	      			clear();
      	      			//oB.end=0;
	                    giveUp.setEnabled(false);//対局終了時にtrueに変更
	                    pass.setEnabled(false);//対局終了時にtrueに変更
      	      				if(oB.winJudge==0) {
      	      					whichTurn.setText("相手の勝利です");
      	      				}else if(oB.winJudge==1) {
      	      					whichTurn.setText("あなたの勝利です");
      	      				}else if(oB.winJudge==2) {
      	      					whichTurn.setText("引き分けです");
      	      				}
      	      		}

    	    	        }else if(rivalI==9 && rivalJ==9 ) {
    	    	            jend.setEnabled(true);//対局終了時にtrueに変更
    	    				clear();
    	               		System.out.println("n");
    	                    giveUp.setEnabled(false);
    	                    pass.setEnabled(false);//対局終了時にtrueに変更
    	        			jtext.setText(TIME_LIMIT);
    	        			whichTurn.setText("相手が投了しました。あなたの勝利です");
    	            		if(th!=null) {
    	            				th=null;
    	            		}
    	  //          		oB.endJudge=2;
        		        	oB.giveUp(1);//黒のギブアップ);
        		        }else if(rivalI==8 && rivalJ==8) {
        		        	oB.passEnd=1;
        	    			if(oB.endJudge==1) {
        	    				jend.setEnabled(true);
        	    				oB.judge_2();
        	            		if(th!=null) {
    	            				th=null;
    	            		}
        	    				System.out.println(oB.bw+"winJudge="+oB.winJudge);
        	        		    giveUp.setEnabled(false);
        	        	        pass.setEnabled(false);
        	    				//対局終了。面に空きがある場合の勝敗判定
        	       				if(oB.winJudge==0) {
        	    					whichTurn.setText("相手の勝利です");
        	    				}else if(oB.winJudge==1) {
        	    					whichTurn.setText("あなたの勝利です");
        	    				}else if(oB.winJudge==2) {
        	    					whichTurn.setText("引き分けです");
        	    				}
        	       				clear();
        	    			}
        		        }

        		        System.out.println("emp="+oB.emp_num);
    	    	        if(oB.emp_num==0) {
               		System.out.println("n");
        			jtext.setText(TIME_LIMIT);
            		if(th!=null) {
            				th=null;
            		}
    	        }
    	    	        if(oB.end!=1) {
        		        if(rivalI!=9 && oB.endJudge!=1) {
    	            	oB.choiceW();
    	        		if(oB.randJudge>0) {
    	        	        pass.setEnabled(false);
    	        		}else {
    	        			whichTurn.setText("おける場所がありません。パスしてください");
    	        		}
        		        }
    	    	        }
    	            	apply();
    	            	repaint();
    	            }


    	        System.out.println("endJudge="+oB.endJudge);
         		System.out.println("emptyy="+oB.emp_num);





    	}
    }



    public void setNum(int tur,int ii,int jj) {

    	turn=tur;

    	if(oB.bw==1) {

    		oB.playW(ii,jj);

    		repaint();

    	}else {

    		oB.playB(ii,jj);

    		repaint();

    	}

    }









    class ThreadX extends Thread {

        String str;

        long msec;

       // static String greet="Hello";



        ThreadX(String str, long msec) {

            this.str = str;

            this.msec = msec;

        }



        public void run() {

            try {

                while(true) {

                    Thread.sleep(msec);  // Threadを継承しているので sleep(msec); でも可　InterruptedExceptionを投げる

                    System.out.println(str+":");

                }

            }

            catch(InterruptedException ex) {

                ex.printStackTrace();

            }

        }

    }





	public void actionPerformed(ActionEvent e) {

		// TODO 自動生成されたメソッド・スタブ



	}



	@Override

	public void mouseDragged(MouseEvent e) {

		// TODO 自動生成されたメソッド・スタブ



	}



	@Override

	public void mouseMoved(MouseEvent e) {

		// TODO 自動生成されたメソッド・スタブ



	}



	@Override

	public void mousePressed(MouseEvent e) {

		// TODO 自動生成されたメソッド・スタブ



	}



	@Override

	public void mouseReleased(MouseEvent e) {

		// TODO 自動生成されたメソッド・スタブ



	}



	@Override

	public void mouseEntered(MouseEvent e) {

		// TODO 自動生成されたメソッド・スタブ



	}



	@Override

	public void mouseExited(MouseEvent e) {

		// TODO 自動生成されたメソッド・スタブ



	}



	void startAction(ActionEvent e) {

		System.out.println("a");

		if(th==null) {

			th=new Thread(this);

			th.start();

		}

	}

	void endAction(ActionEvent e) {

		System.out.println("n");

		if(th!=null) {

				th=null;

		}

	}

	@Override

	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		int i;
		while(th!=null) {
			i=Integer.parseInt(jtext.getText());
			try {
				th.sleep(1000);
				if(th==null) break;
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				break;
			}

			if(i-1>=0) {
				jtext.setText(Integer.toString(i-1));
			}else {
				jtext.setText(TIME_LIMIT);
    			whichTurn.setText("相手の手番です");
				th=null;
				if(oB.bw==1) {
					oB.choiceB();
					System.out.println(oB.randJudge);
					if(oB.randJudge>0){
					oB.random(1);
	   // 			oB.judge();//勝敗判定
					}
					System.out.println("おけない");
				}else if(oB.bw==2){
					oB.choiceW();
					System.out.println(oB.randJudge);
					if(oB.randJudge>0){
					oB.random(2);
	    	//		oB.judge();//勝敗判定
					}
					System.out.println("おけない");
				}
				System.out.println("rand実行");
				if(oB.randJudge>0) {
					client.sendMessage("6"+oB.r1+oB.r2);//識別子i,j
					oB.endJudge=0;
					oB.passEnd=0;
					oB.judge();//勝敗判定
	    			if(oB.end==1) {
	    				jend.setEnabled(true);
	    				clear();
	    		//		oB.end=0;
	       				if(oB.winJudge==0) {
	    					whichTurn.setText("相手の勝利です");
	    				}else if(oB.winJudge==1) {
	    					whichTurn.setText("あなたの勝利です");
	    				}else if(oB.winJudge==2) {
	    					whichTurn.setText("引き分けです");
	    				}
	    			}
					giveUp.setEnabled(false);
					jtext.setText(TIME_LIMIT);
		    		if(th!=null) {
		    				th=null;
		    		}
    	//		oB.endJudge=0;//passの回reset
				}else {
	    			client.sendMessage("688");//識別子i,j
        		    giveUp.setEnabled(false);
        		    pass.setEnabled(false);
        			jtext.setText(TIME_LIMIT);
            		if(th!=null) {
            				th=null;
            		}
	    			oB.endJudge=1;//passの回inc
	    			if(oB.passEnd==1) {
	    				oB.judge_2();
	    				System.out.println(oB.bw+"winJudge="+oB.winJudge);
	    				//対局終了。面に空きがある場合の勝敗判定
	    				jend.setEnabled(true);
	    				clear();
	       				if(oB.winJudge==0) {
	    					whichTurn.setText("相手の勝利です");
	    				}else if(oB.winJudge==1) {
	    					whichTurn.setText("あなたの勝利です");
	    				}else if(oB.winJudge==2) {
	    					whichTurn.setText("引き分けです");
	    				}
	    			}
				}

				client.receiveHandler(1);
				oB.randJudge=0;//未定
//    			oB.endJudge=0;//passの回reset
    			clear();//choiceの消去
    			oB.print();
    			apply(); //ボタンのアイコン更新
    			//oB.judge();//勝敗判定
    	        pass.setEnabled(false);
    			repaint();//画面のオブジェクトを描画し直す
			}
		}
	}





	public void endOthello() {

		if(oB.sendedMessage==1) {

    		System.out.println("n");

			jtext.setText(TIME_LIMIT);

    		if(th!=null) {

    				th=null;

    		}

    		//6受け取ったらスレッド停止、終了ボタンをtrueに

		}

	}

}