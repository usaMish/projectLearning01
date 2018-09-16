package client;

import java.util.Random;



public class Othello {
	static int b_num;

	static int w_num;
	static int emp_num;

	public static final int BLACK =1;

	public static final int toBLACK =5;;

	public static final int WHITE =2;

	static int sendedMessage=0;

	static int emp_status;

	 static int r1;

	static int r2;

	static Client cl;

	static int bw;//自分が黒か白か

	 static int x=8;

	 static int i;

	static int j;

	static int randJudge=0;

	 static int [][] othello=new int[x][x];

	 static int endJudge=0;	//終了判定
	 static int passEnd=0;
	 static  int winJudge=0;
	 static int end=0;//終了決定

	 Othello(int bw,Client cl) {

		 //0…おけない、1…黒、2…白、4…おける

		 for(i=0; i<x; i++) {

			 for(j=0; j<x; j++) {

				 othello[i][j]=0;//盤面初期化

			 }

		 }

		 othello[3][3]=2;//縦、横

		 othello[4][4]=2;

		 othello[4][3]=1;//2で白用

		 othello[3][4]=1;





		 this.bw=bw;//黒か白か

		 this.cl=cl;

	 }



	 static void print() { //描画



		 for(j=0; j<x; j++) {

			 for(i=0; i<x; i++) {

				if(othello[i][j]==0) {

					System.out.print("□");

				}else if(othello[i][j]==1) {

					System.out.print("●");

				}else if(othello[i][j]==2) {

					System.out.print("○");

				}else if(othello[i][j]==4) {

					System.out.print("□");

				}else if(othello[i][j]==5) {

					System.out.print("５");

				}

			 }

			 System.out.println();

		 }

		 System.out.println();



	 }



	 static void printChoice() { //描画



		 for(j=0; j<x; j++) {

			 for(i=0; i<x; i++) {

				if(othello[i][j]==0) {

					System.out.print("０");

				}else if(othello[i][j]==1) {

					System.out.print("●");

				}else if(othello[i][j]==2) {

					System.out.print("○");

				}else if(othello[i][j]==4) {

					System.out.print("４");

				}

			 }

			 System.out.println();

		 }

		 System.out.println();



	 }



	 static void playW(int i1,int j1) { //白の手を打つ

	if(othello[i1][j1]==4 ) {

		 int w=0; //右

		 int judge=0;

		 othello[i1][j1]=WHITE;

		 for(j=j1; j<x; j++) {

			 if(othello[i1][j]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(j=j1; j<x; j++) {

				 if(othello[i1][j]==BLACK) {

					 othello[i1][j]=WHITE;

				 }else if(othello[i1][j]==WHITE && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(j=j1; j>=0; j--) {

			 if(othello[i1][j]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(j=j1; j>=0; j--) {

				 if(othello[i1][j]==BLACK) {

					 othello[i1][j]=WHITE;

				 }else if(othello[i1][j]==WHITE && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i<x; i++) {

			 if(othello[i][j1]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=i1; i<x; i++) {

				 if(othello[i][j1]==BLACK) {

					 othello[i][j1]=WHITE;

				 }else if(othello[i][j1]==WHITE && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i>=0; i--) {

			 if(othello[i][j1]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=i1; i>=0; i--) {

				 if(othello[i][j1]==BLACK) {

					 othello[i][j1]=WHITE;

				 }else if(othello[i][j1]==2 && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 int max=i1;

		 int min =j1;

		 if(i1<j1) {

			 max = j1;

			 min=i1;

		 }

		 for(i=0; i<=x-1-max; i++) {

			 if(othello[i1+i][j1+i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=x-1-max; i++) {

				 if(othello[i1+i][j1+i]==BLACK) {

					 othello[i1+i][j1+i]=WHITE;

				 }else if(othello[i1+i][j1+i]==WHITE && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1-i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1-i]==BLACK) {

					 othello[i1-i][j1-i]=WHITE;

				 }else if(othello[i1-i][j1-i]==WHITE && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 int j2=7-j1;

		 max=i1;

		 min =j2;

		 if(i1<j2) {

			 max = j2;

			 min=i1;

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=x-max-1; i++) {

			 if(othello[i1+i][j1-i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=x-max-1; i++) {

				 if(othello[i1+i][j1-i]==BLACK) {

					 othello[i1+i][j1-i]=WHITE;

				 }else if(othello[i1+i][j1-i]==WHITE && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



	 	w=0;

	 	judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1+i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1+i]==BLACK) {

					 othello[i1-i][j1+i]=WHITE;

				 }else if(othello[i1-i][j1+i]==WHITE && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		}  //if

	 }



	 static void playB(int i1,int j1) { //黒の手を打つ



	if(othello[i1][j1]==4) {



		 int w=0; //右

		 int judge=0;

		 othello[i1][j1]=BLACK;

		 for(j=j1; j<x; j++) {

			 if(othello[i1][j]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(j=j1; j<x; j++) {

				 if(othello[i1][j]==WHITE) {

					 othello[i1][j]=BLACK;

				 }else if(othello[i1][j]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(j=j1; j>=0; j--) {

			 if(othello[i1][j]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(j=j1; j>=0; j--) {

				 if(othello[i1][j]==WHITE) {

					 othello[i1][j]=BLACK;

				 }else if(othello[i1][j]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i<x; i++) {

			 if(othello[i][j1]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=i1; i<x; i++) {

				 if(othello[i][j1]==WHITE) {

					 othello[i][j1]=BLACK;

				 }else if(othello[i][j1]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i>=0; i--) {

			 if(othello[i][j1]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=i1; i>=0; i--) {

				 if(othello[i][j1]==WHITE) {

					 othello[i][j1]=BLACK;

				 }else if(othello[i][j1]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 int max=i1;

		 int min =j1;

		 if(i1<j1) {

			 max = j1;

			 min=i1;

		 }

		 for(i=0; i<=x-1-max; i++) {

			 if(othello[i1+i][j1+i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=x-1-max; i++) {

				 if(othello[i1+i][j1+i]==WHITE) {

					 othello[i1+i][j1+i]=BLACK;

				 }else if(othello[i1+i][j1+i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1-i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1-i]==WHITE) {

					 othello[i1-i][j1-i]=BLACK;

				 }else if(othello[i1-i][j1-i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 int j2=7-j1;

		 max=i1;

		 min =j2;

		 if(i1<j2) {

			 max = j2;

			 min=i1;

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=x-max-1; i++) {

			 if(othello[i1+i][j1-i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=x-max-1; i++) {

				 if(othello[i1+i][j1-i]==WHITE) {

					 othello[i1+i][j1-i]=BLACK;

				 }else if(othello[i1+i][j1-i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



	 	w=0;

	 	judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1+i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1+i]==WHITE) {

					 othello[i1-i][j1+i]=BLACK;

				 }else if(othello[i1-i][j1+i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		}  //if

	 }

	  void judge_2() {

		 b_num=0;

		 w_num=0;

		 emp_num=0;

		 for(i=0; i<x; i++) {

			 for(j=0; j<x; j++) {

				 if(othello[i][j]==1) {

					 b_num++;

				 }else if(othello[i][j]==2) {

					 w_num++;

				 }else{

					 emp_num++;

				 }

			 }

		 }

		 if(w_num<b_num && bw==1) {

			 System.out.println("黒の勝利");

			 sendCode(0);
			 winJudge=1;
			 end=1;
		 }else if(w_num<b_num && bw==2) {
			 end=1;
		 }

			 if(w_num>b_num && bw==2) {

			 System.out.println("白の勝利");

			 sendCode(0);
			 winJudge=1;
			 end=1;
		 }else if(w_num<b_num && bw==1) {
			 end=1;
		 }

		 if(w_num==b_num && bw==1){

		 System.out.println("引き分け");
		 winJudge=2;
		 sendCode(1);
		 end=1;
		 }else if(w_num<b_num && bw==2) {
			 end=1;
		 }

	 }

	 static void judge() {

		 for(i=0; i<x; i++) {

			 for(j=0; j<x; j++) {

				if(othello[i][j]!=1 &&  othello[i][j]!=2) {

					othello[i][j]=0;

				}

			 }

		 }



		 b_num=0;

		 w_num=0;

		 emp_num=0;

		 for(i=0; i<x; i++) {

			 for(j=0; j<x; j++) {

				 if(othello[i][j]==1) {

					 b_num++;

				 }else if(othello[i][j]==2) {

					 w_num++;

				 }else {

					 emp_num++;

				 }

			 }

		 }

		 if(w_num==0 && bw==1) { //自分が黒ならば

			 System.out.println("黒の勝利");
			 winJudge=1;
			 sendCode(0);//勝ち
			 end=1;
		 }else if(w_num==0 && bw==2){
			 end=1;
		 }
			if(b_num==0 && bw==2) { //自分が白ならば

			 System.out.println("白の勝利");
			 winJudge=1;
			 sendCode(0);
			 end=1;
		 }else if(b_num==0 && bw==1){
			 end=1;
		 }

		 if(emp_num==0){
			 System.out.println("デバッグ");
			 if(w_num<b_num && bw==1) {

				 System.out.println("黒の勝利");
				 winJudge=1;
				 sendCode(0);
				 end=1;
			 }else if(w_num>b_num && bw==2) {

				 System.out.println("白の勝利");
				 winJudge=1;
				 sendCode(0);
				 end=1;
			 }

			 else if(w_num==b_num && bw==1){

			 System.out.println("引き分け");
			 winJudge=2;
			 sendCode(1);
			 end=1;
			 }
			 end=1;

		 }

		 System.out.println(end);

		 emp_status=emp_num;



	 }



	 static void sendCode(int p) {//勝敗コードの送信用

		 if(p==0) {

			 cl.sendMessage("7win");

		 }else if(p==1){

			 cl.sendMessage("7earlyWin");

		 }else{

			 cl.sendMessage("7draw");

			 }

		 sendedMessage=1;//sendMessgeされた

	 }



	 static void random(int BorW) {



		 int c=0;

		 int limit=0;

		 Random r=new Random();

		 if(BorW==1) {

			 choiceB();

		 }else {

			 choiceW();

		 }

		 if(randJudge>0) {

		 do {

		 r1=r.nextInt(8);

		 r2=r.nextInt(8);

		 if(othello[r1][r2]==4) {

			 if(BorW==1) {

				 playB(r1,r2);

				 c++;

			 }else {

				 playW(r1,r2);

				 c++;

			 }

		 }

		 }while(c==0);

		 }



	 }



	 static void choiceW() {//Whiteを置けるかどうか

		 for(i=0; i<x; i++) {

			 for(j=0; j<x; j++) {

				if(othello[i][j]!=1 &&  othello[i][j]!=2) {

					othello[i][j]=0;

				}

			 }

		 }

		 randJudge=0;

		 int i1,j1;

		 int count=0;

		 int w=0;

		 int judge=0;

		 int x1,y1;

		 for(i1=0; i1<x; i1++) {

			 for(j1=0; j1<x; j1++) {

			 y1=i1; x1=j1;

				 if(othello[i1][j1]==0 || othello[i1][j1]==4 ) {

		 for(j=j1; j<x; j++) {

			 if(othello[i1][j]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(j=j1; j<x; j++) {

				 if(othello[i1][j]==BLACK) {

					 count++;

				 }else if(othello[i1][j]==0  && judge==0) {

					judge=1;

				 }else if(othello[i1][j]==4  && judge==0) {

					judge=1;

				 }else if( othello[i1][j]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(j=j1; j>=0; j--) {

			 if(othello[i1][j]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(j=j1; j>=0; j--) {

				 if(othello[i1][j]==BLACK) {

					 count++;

				 }else if(othello[i1][j]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1][j]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1][j]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i<x; i++) {

			 if(othello[i][j1]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=i1; i<x; i++) {

				 if(othello[i][j1]==BLACK) {

					 count++;

				 }else if(othello[i][j1]==0  && judge==0) {

						judge=1;

					 }else if(othello[i][j1]==4  && judge==0) {

						judge=1;

					 }else if( othello[i][j1]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i>=0; i--) {

			 if(othello[i][j1]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=i1; i>=0; i--) {

				 if(othello[i][j1]==BLACK) {

					 count++;

				 }else if(othello[i][j1]==0  && judge==0) {

						judge=1;

					 }else if(othello[i][j1]==4  && judge==0) {

						judge=1;

					 }else if( othello[i][j1]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }



		 }



		 w=0;

		 judge=0;

		 int max=i1;

		 int min =j1;

		 if(i1<j1) {

			 max = j1;

			 min=i1;

		 }

		 for(i=0; i<=x-1-max; i++) {

			 if(othello[i1+i][j1+i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=x-1-max; i++) {

				 if(othello[i1+i][j1+i]==BLACK) {

					 count++;

				 }else if(othello[i1+i][j1+i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1+i][j1+i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1+i][j1+i]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1-i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1-i]==BLACK) {

					 count++;

				 }else if(othello[i1-i][j1-i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1-i][j1-i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1-i][j1-i]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 int j2=7-j1;

		 max=i1;

		 min =j2;

		 if(i1<j2) {

			 max = j2;

			 min=i1;

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=x-max-1; i++) {

			 if(othello[i1+i][j1-i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=x-max-1; i++) {

				 if(othello[i1+i][j1-i]==BLACK) {

					 count++;

				 }else if(othello[i1+i][j1-i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1+i][j1-i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1+i][j1-i]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[y1][x1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



	 	w=0;

	 	judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1+i]==WHITE) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1+i]==BLACK) {

					 count++;

				 }else if(othello[i1-i][j1+i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1-i][j1+i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1-i][j1+i]==WHITE){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }

	 }//if

			 }//for

	 }//for

		 printChoice();

	 }//method



	 static void choiceB() {//BLACKを置けるかどうか

		 for(i=0; i<x; i++) {

			 for(j=0; j<x; j++) {

				if(othello[i][j]!=1 &&  othello[i][j]!=2) {

					othello[i][j]=0;

				}

			 }

		 }



		 randJudge=0;

		 int i1,j1;

		 int count=0;

		 int w=0;

		 int judge=0;

		 int x1,y1;

		 for(i1=0; i1<x; i1++) {

			 for(j1=0; j1<x; j1++) {

			 y1=i1; x1=j1;

				 if(othello[i1][j1]==0 || othello[i1][j1]==4 ) {

		 for(j=j1; j<x; j++) {

			 if(othello[i1][j]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(j=j1; j<x; j++) {

				 if(othello[i1][j]==WHITE) {

					 count++;

				 }else if(othello[i1][j]==0  && judge==0) {

					judge=1;

				 }else if(othello[i1][j]==4  && judge==0) {

					judge=1;

				 }else if( othello[i1][j]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(j=j1; j>=0; j--) {

			 if(othello[i1][j]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(j=j1; j>=0; j--) {

				 if(othello[i1][j]==WHITE) {

					 count++;

				 }else if(othello[i1][j]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1][j]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1][j]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i<x; i++) {

			 if(othello[i][j1]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=i1; i<x; i++) {

				 if(othello[i][j1]==WHITE) {

					 count++;

				 }else if(othello[i][j1]==0  && judge==0) {

						judge=1;

					 }else if(othello[i][j1]==4  && judge==0) {

						judge=1;

					 }else if( othello[i][j1]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i>=0; i--) {

			 if(othello[i][j1]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=i1; i>=0; i--) {

				 if(othello[i][j1]==WHITE) {

					 count++;

				 }else if(othello[i][j1]==0  && judge==0) {

						judge=1;

					 }else if(othello[i][j1]==4  && judge==0) {

						judge=1;

					 }else if( othello[i][j1]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 int max=i1;

		 int min =j1;

		 if(i1<j1) {

			 max = j1;

			 min=i1;

		 }

		 for(i=0; i<=x-1-max; i++) {

			 if(othello[i1+i][j1+i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=x-1-max; i++) {

				 if(othello[i1+i][j1+i]==WHITE) {

					 count++;

				 }else if(othello[i1+i][j1+i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1+i][j1+i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1+i][j1+i]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1-i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1-i]==WHITE) {

					 count++;

				 }else if(othello[i1-i][j1-i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1-i][j1-i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1-i][j1-i]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }

		 }



		 int j2=7-j1;

		 max=i1;

		 min =j2;

		 if(i1<j2) {

			 max = j2;

			 min=i1;

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=x-max-1; i++) {

			 if(othello[i1+i][j1-i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=x-max-1; i++) {

				 if(othello[i1+i][j1-i]==WHITE) {

					 count++;

				 }else if(othello[i1+i][j1-i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1+i][j1-i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1+i][j1-i]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }



		 }



	 	w=0;

	 	judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1+i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>0) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1+i]==WHITE) {

					 count++;

				 }else if(othello[i1-i][j1+i]==0  && judge==0) {

						judge=1;

					 }else if(othello[i1-i][j1+i]==4  && judge==0) {

						judge=1;

					 }else if( othello[i1-i][j1+i]==BLACK){

					 break;

				 }else {

					 count=0;

					 break;

				 }

			 }

			 if(count>0) {

				 othello[i1][j1]=4;

				 randJudge++;

				 count=0;

				 judge=0;

			 }else {

				 if( othello[i1][j1]!=4) {

				 othello[i1][j1]=0;

				 }

				 count=0;

				 judge=0;

			 }



		 }

	 }

			 }//if

	 }//for

		 printChoice();

	 }



	 static void playBex(int i1,int j1) { //黒の手を打つ



	if(othello[i1][j1]==4) {



		 int w=0; //右

		 int judge=0;

		 othello[i1][j1]=BLACK;

		 for(j=j1; j<x; j++) {

			 if(othello[i1][j]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(j=j1; j<x; j++) {

				 if(othello[i1][j]==WHITE) {

					 othello[i1][j]=toBLACK;

				 }else if(othello[i1][j]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(j=j1; j>=0; j--) {

			 if(othello[i1][j]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(j=j1; j>=0; j--) {

				 if(othello[i1][j]==WHITE) {

					 othello[i1][j]=toBLACK;

				 }else if(othello[i1][j]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i<x; i++) {

			 if(othello[i][j1]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=i1; i<x; i++) {

				 if(othello[i][j1]==WHITE) {

					 othello[i][j1]=toBLACK;

				 }else if(othello[i][j1]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=i1; i>=0; i--) {

			 if(othello[i][j1]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=i1; i>=0; i--) {

				 if(othello[i][j1]==WHITE) {

					 othello[i][j1]=toBLACK;

				 }else if(othello[i][j1]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 int max=i1;

		 int min =j1;

		 if(i1<j1) {

			 max = j1;

			 min=i1;

		 }

		 for(i=0; i<=x-1-max; i++) {

			 if(othello[i1+i][j1+i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=x-1-max; i++) {

				 if(othello[i1+i][j1+i]==WHITE) {

					 othello[i1+i][j1+i]=toBLACK;

				 }else if(othello[i1+i][j1+i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1-i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1-i]==WHITE) {

					 othello[i1-i][j1-i]=toBLACK;

				 }else if(othello[i1-i][j1-i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		 int j2=7-j1;

		 max=i1;

		 min =j2;

		 if(i1<j2) {

			 max = j2;

			 min=i1;

		 }



		 w=0;

		 judge=0;

		 for(i=0; i<=x-max-1; i++) {

			 if(othello[i1+i][j1-i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=x-max-1; i++) {

				 if(othello[i1+i][j1-i]==WHITE) {

					 othello[i1+i][j1-i]=toBLACK;

				 }else if(othello[i1+i][j1-i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



	 	w=0;

	 	judge=0;

		 for(i=0; i<=min; i++) {

			 if(othello[i1-i][j1+i]==BLACK) {

				 w++;

			 }

		 }

		 if(w>1) {

			 for(i=0; i<=min; i++) {

				 if(othello[i1-i][j1+i]==WHITE) {

					 othello[i1-i][j1+i]=toBLACK;

				 }else if(othello[i1-i][j1+i]==BLACK && judge==0) {

					 judge=1;

				 }else {

					 break;

				 }

			 }

		 }



		}  //if

	 }



	 static void BtoB() {

	 for(j=0; j<x; j++) {

		 for(i=0; i<x; i++) {

			if(othello[i][j]==5) {

				othello[i][j]=BLACK;

			}

		 }



	 }



	 }





	 static void giveUp(int borw) {

		 if(borw==1) {

			 System.out.println("白の勝利");

			 sendCode(1);

		 }else {

			 System.out.println("黒の勝利");

			 sendCode(1);

		 }

	 }



	 static String sendInf(int turn_inf , int i_inf, int j_inf) {

		 return(turn_inf+"/"+i_inf+"/"+j_inf);

	 }

}