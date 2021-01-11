import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*; 
import java.io.*;
import java.applet.*;
import java.net.*;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFrame;

class Flag {
	 int f1=0,f2=0;
	 public Flag(){ }
	 public synchronized void putf1begin() {
	    while (f1==1)  try{ wait();} catch(Exception e){}
	 }
	 public synchronized void putf1end() { 
	    f1=1;	
	    notifyAll();  
	 }
	 public synchronized void getf1begin() {
      while (f1==0)  try{ wait();} catch(Exception e){}
		 }
	 public synchronized void getf1end() { 
		    f1=0;	
		    notifyAll();  
		 }
	 
	 public synchronized void putf2begin() {
		   while (f2==1)  try{ wait();} catch(Exception e){}
		    }
	 
	 public synchronized void putf2end() {	    
		    f2=1;	
		    notifyAll();  
		 }
	 public synchronized void getf2begin() {
		   while (f2==0)  try{ wait();} catch(Exception e){}
		    }
	 
	 public synchronized void getf2end() {	    
		    f2=0;	
		    notifyAll();  
		 }

}
public class Battlefield  extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	fieldmode mode;
	Image       OffScreen1,OffScreen2,O2;
	Graphics2D     drawOffScreen1,drawOffScreen2,g;
    Image        myplane,myplane1,eplane1,eplane2,bullet,explode,backgroud,a1,a2,a3,a4,gameoverimage,winimage,myplaneL1,myplaneL2,myplaneL3;
    int key;
    boolean flag1,flag2;
    boolean controlflag[]= new boolean[5];
    boolean controlflag1[]= new boolean[5];
    boolean locationreflesh=false;
    Airplane Controlplane,Controlplane1;
    ControlplaneAdvance controller,controller1;//飞机控制相关
    Bullettype nmlBullet,shotBullet,biBullet;//普通子弹 散弹 双列子弹
    Accessorytype lives,boxs,oil,bibox,shotbox,shield,smasher,stoneleft,stoneright;
    //道具类型:生命，箱子，油罐，双排子弹箱，散弹箱，盾牌，加速器
   	ArrayList<Bullet> bulletsList;
   	ArrayList<Airplane> planeList;
   	ArrayList<Explode> explodeList;
   	ArrayList<Accessory> accessoryList;
    TextField t1,t2,t3,t4,t5,t6 ;
    Panel p1,p2;
    Button start,save,load;
    Timer timer,timer2,timer3,timer4,timer5;
    Drawer	d1;
    Displayer d2;
    Backgroudmusic m1;
    Scenemusic m2;
   	int delay=5000;
   	float backy=638;
//   	boolean fire=false;
//自动发子弹
   	boolean fire=true;
   	boolean fire1=true;
   	boolean goon=true;
   	int gameover=0;
	boolean hasAccessory=false;
	boolean addplane=false;
	boolean isSleep=true;

	Flag flag;
	//////
	
	////////
	public int getRandomIntNum(int a, int b)
	{
	  Random random = new Random();
	  int c = random.nextInt();
//	Random里的nextInt()方法，随机产生一个 int 型的数；
	  if(c<0)
	  {
	    c = -c ;
	  }
	  int d = ((c %(b-a)) + a + 1);
//	这里让变量d变成a和b之中的数， % 是取余运算；
	return d;

	}
	public Battlefield (fieldmode mode_in)
    { 
		OffScreen1     =  new BufferedImage(1000,900,BufferedImage.TYPE_INT_RGB);
		drawOffScreen1 = (Graphics2D)OffScreen1.getGraphics();
		OffScreen2     =  new BufferedImage(1000,900,BufferedImage.TYPE_INT_RGB);
		drawOffScreen2 = (Graphics2D)OffScreen2.getGraphics();
		mode=mode_in;
        flag=new Flag();
		myplane = getToolkit().getImage("Airplanes/airplane3.gif");
		if(mode.biperson)
			myplane1=getToolkit().getImage("Airplanes/airplane14.gif");
		
		if(mode.endless) {
			myplaneL1=getToolkit().getImage("Airplanes/airplane11.gif");
			myplaneL2=getToolkit().getImage("Airplanes/airplane10.gif");
			myplaneL3=getToolkit().getImage("Airplanes/airplane12.gif");
		}
		eplane1 = getToolkit().getImage("Airplanes/airplane4.gif"); 
		eplane2 = getToolkit().getImage("Airplanes/airplane4-1.gif"); 
		a1=getToolkit().getImage("accessory/lives.gif"); 
		a2=getToolkit().getImage("accessory/box1.gif"); 
		a3=getToolkit().getImage("accessory/oil.gif"); 
		a4=getToolkit().getImage("accessory/stone.gif");
		
		Airplane.eplane1=eplane1;
		Airplane.eplane2=eplane2;
		bullet=getToolkit().getImage("Bullets/Bullet11.gif"); 
		explode=getToolkit().getImage("Bullets/explosion.gif");
		backgroud=getToolkit().getImage("Backgrounds/bg.jpg");
		gameoverimage=getToolkit().getImage("accessory/gameover.gif");
		winimage=getToolkit().getImage("accessory/win.gif");
		
       	planeList = new ArrayList<Airplane>(); 
       	bulletsList = new ArrayList<Bullet>(); 
       	explodeList = new ArrayList<Explode>(); 
       	accessoryList = new ArrayList<Accessory>(); 
    }
	public void gameperpare(){
		controller=new ControlplaneAdvance();
        nmlBullet=new Bullettype(20,bullet);
        shotBullet=new Bullettype(20,bullet);
        biBullet=new Bullettype(2,bullet);
		lives = new Accessorytype(1,a1);
		boxs = new Accessorytype(2,a2);
		oil=new Accessorytype(3,a3);
		bibox = new Accessorytype(4,a2,biBullet);
		shotbox = new Accessorytype(5,a2,shotBullet);
		shield=new Accessorytype(6,a2);
		smasher=new Accessorytype(7,a2);
		stoneleft=new Accessorytype(8,a4,true);
		stoneright=new Accessorytype(8,a4,false);
		
		if(mode.biperson) {
        	controller1=new ControlplaneAdvance();
        	Controlplane1=new Airplane(700,500,80,66,nmlBullet,controller1);
            Controlplane1.speed=10;
            Controlplane=new Airplane(300,500,80,66,nmlBullet,controller);
        }else {
        	Controlplane=new Airplane(500,500,80,66,nmlBullet,controller);
        }
        
        Controlplane.speed=10;
        
        
        
        p2.addKeyListener(new MyKeyListener(controlflag,controlflag1));
		m1=new Backgroudmusic();
        m2=new Scenemusic();
	}
	public void gamebegin(){
//		初始化
		TimerTask task=new TimerTask(){
			 @SuppressWarnings("deprecation")
			public void run(){
				 hasAccessory=true; 
			     m2.beepclip.loop();
			 }	
			};
		timer = new Timer();
		timer.schedule(task,0,delay);

	     TimerTask task2=new TimerTask(){
			 public void run(){
						 Controlplane.oil-=5 ; 
				         t3.setText(Controlplane.oil+"");
				         if(mode.biperson) {Controlplane1.oil-=5 ;
				         t4.setText(Controlplane1.oil+"");}
					 }	
					};
		timer2 = new Timer();
		timer2.schedule(task2,3000,3000); // 每三秒钟油量减5

//	    TimerTask task3=new TimerTask(){
//			 public void run(){
//                 addplane=true;
//                       }	
//			};
//		timer3 = new Timer();
//		timer3.schedule(task3,2000,2000);
		
		TimerTask task4=new TimerTask(){
			 public void run(){
						 locationreflesh=true;
					 }	
					};
		timer4 = new Timer();
		timer4.schedule(task4,0,30);
		
		TimerTask task5=new TimerTask(){
			 public void run(){
						 isSleep=false;
					 }	
					};
		timer5 = new Timer();
		timer5.schedule(task5,8000);
		
		Controlplane.pX=500;
		Controlplane.pY=650;
		Controlplane.life=100;
		//Controlplane.bulletnum=100;
		Controlplane.oil=100;
		Controlplane.controller.over=false;
		if(mode.biperson) {
			Controlplane1.pX=700;
			Controlplane1.pY=650;
			Controlplane1.life=100;
			//Controlplane.bulletnum=100;
			Controlplane1.oil=100;
			Controlplane1.controller.over=false;
			t2.setText(Controlplane1.life+"");
			Controlplane.pX=300;
		}
		t1.setText(Controlplane.life+"");

		flag1=false;
		flag2=false;
		


        g=(Graphics2D)this.p2.getGraphics(); 
       	planeList.clear(); 
       	bulletsList.clear(); 
       	explodeList.clear(); 
       	accessoryList.clear(); 
       	Airplane ptemp=new Airplane(90,50,78,68,nmlBullet,bibox);
    	planeList.add(ptemp);
        for (int i=2;i<=8;i++){
        	Airplane p1=new Airplane(90*i,50,78,68,nmlBullet);
        	planeList.add(p1);
        	p1.intervel=p1.getRandomIntNum(0,6);
            p1.eplane=1;

        }
        
        p2.requestFocus();
        m1=new Backgroudmusic();
        m1.run();
        d1=new Drawer();
    	d2=new Displayer();
    	d1.start();
        d2.start();
	}
	public void gameUnlimit(){
		TimerTask task=new TimerTask(){
			 public void run(){  
				 if(planeList.size()<8) {
					 addplane=true;
				 }
             }	
		};
		timer3 = new Timer();
		timer3.schedule(task,1000,100);
		
	}
	@SuppressWarnings("deprecation")
	public void gameContrl(Graphics2D drawOffScreen){

		if(mode.advance && isSleep) {
			drawOffScreen.drawImage(winimage,450,300,null);
		}
		else {
//控制
 
 //   	 drawOffScreen.fillRect(0, 0, 1000, 900);
		  drawOffScreen.drawImage(backgroud,0,0,1000,900,0,(int)backy,360,320+(int)backy,null);
		  backy-=.2;
		//  System.out.println((int)backy+"");
		  if (backy<0) backy=638; 

		//  drawOffScreen.drawImage(backgroud,0,0,1000,900,null);  
    	   if (addplane){
			if (planeList.size()<15) {
				if(getRandomIntNum(1, 10)<=5){
					switch(getRandomIntNum(1, 10)) {
					case 1:planeList.add(new Airplane(nmlBullet,lives));break;
					case 2:planeList.add(new Airplane(nmlBullet,oil));break;
					case 3:planeList.add(new Airplane(nmlBullet,oil));break;
					case 4:planeList.add(new Airplane(nmlBullet,bibox));break;
					case 5:planeList.add(new Airplane(nmlBullet,shotbox));break;
					case 6:planeList.add(new Airplane(nmlBullet,shield));break;
					case 7:planeList.add(new Airplane(nmlBullet,smasher));break;
					default:planeList.add(new Airplane(nmlBullet,lives));break;
					}
				}
				else
					planeList.add(new Airplane(nmlBullet));
			}    		   
		    addplane=false;
           }
    	  Iterator<Airplane> pnums = planeList.iterator();
    	   while(pnums.hasNext()) { 
    		      Airplane p = pnums.next(); 
                  p.fly();
                 if (p.eplane==1) drawOffScreen.drawImage(Airplane.eplane1,p.pX,p.pY,null);  
                 if (p.eplane==2) drawOffScreen.drawImage(Airplane.eplane2,p.pX,p.pY,null);  
                 
                  //发射子弹
     		     if ((p.getRandomIntNum(0, 300))==2)  {
     		    	 if(p.bullettype==nmlBullet) {
     		    		 Bullet b2=new Bullet(p.pX+p.pWidth/2-3,p.pY+p.pHeight,13,13,nmlBullet);
     		    		 b2.speed=-3;
     		    		 bulletsList.add(b2);     
     		    	 }
     		       }
    		      //判断是否被击中?
    		      Iterator<Bullet> bnums = bulletsList.iterator();
    	    	   while(bnums.hasNext()) { 
    	  		      Bullet b = bnums.next(); 
    	  		      if (p.hit(b)) {
    	  		    	  if(b.parent_id==1) {
    	  		    		Controlplane.controller.exp+=20;
    	  		    		if(Controlplane.controller.exp%500==0) {
    	  		    			Controlplane.controller.exp=0;
    	  		    			Controlplane.controller.level+=1;
    	  		    		}
    	  		    	  }
    	  		    	  if(mode.endless) {
    	  		    		  t5.setText(Controlplane.controller.level+""); 
    	  		    		t6.setText(Controlplane.controller.exp+"");
    	  		    	  }
    	 		    	  b=null;
    	 		    	  bnums.remove();
    	 		    	  m2.hitclip.play();
    	 		          };  		      
    	  	       //判断是否撞击控制飞机
    	 		     if (p.hit(Controlplane)) 
 	 		    	  m2.explodeclip.play();
    	 		    if (mode.biperson&&p.hit(Controlplane1)) 
   	 		    	  m2.explodeclip.play();
    	    	   } 
    	    	   
    	    	   //判断是否撞击附件
     		      Iterator<Accessory> anums =accessoryList.iterator();
      	    	   while(anums.hasNext()) { 
      	    	    	Accessory a = anums.next(); 
      	  		        if (p.hit(a)){
      	  		        t1.setText(Controlplane.life+"");
      	 		    	  a=null;
    	 		    	  anums.remove();	
         		    	  m2.beepclip.stop();
         		    	  m2.eatclip.play();
       	  		        };
   	    	        } 
    	    	   if (p.life<0) {
    	    		  if(p.bespecial==true)
    	    			  accessoryList.add(new Accessory(p.pX,p.pY,p.atype));
    		    	  explodeList.add(new Explode(p.pX,p.pY));
    		    	  p=null;
    		    	  pnums.remove();
  	 		    	  m2.explodeclip.play();
    		          };  		    
    	   } 
//附件
    	   if (hasAccessory){
    		   int temp;
    		   if(mode.advance) {
    			   temp=getRandomIntNum(1,7);
    		   }
    		   else {
    			   temp=getRandomIntNum(1,3);
    		   }
    		   switch(temp) {
    		   case 1:
    			   accessoryList.add(new Accessory(boxs));
    			   break;
    		   case 2:
    			   accessoryList.add(new Accessory(lives));
    			   break;   
    		   case 3:
    			   accessoryList.add(new Accessory(oil));
    			   break;
    		   case 4:
    			   accessoryList.add(new Accessory(stoneleft));
    			   break;
    		   case 5:
    			   accessoryList.add(new Accessory(stoneleft));
    			   break;
    		   case 6:
    			   accessoryList.add(new Accessory(stoneright));
    			   break;
    		   case 7:
    			   accessoryList.add(new Accessory(stoneright));
    			   break;
    		   default:break;
    		   }
    		   
    		   hasAccessory=false;

               }
        	   Iterator<Accessory> anums = accessoryList.iterator();
        	   while(anums.hasNext()) { 
        		   Accessory a = anums.next(); 
        		   drawOffScreen.drawImage(a.atype.aImage,a.aX,a.aY,null);  
        		   if(mode.advance && a.atype.id==8) {
        			   if(a.atype.stoneDirect) {
        				   a.aX-=1;
        			   }
        			   else {
        				   a.aX+=1;
        			   }
        		   }
     		      a.aY+=a.speed; 
     		      if (a.aY>900){
     		    	  a=null;
     		    	  anums.remove();
     		    	  m2.beepclip.stop();
     		    	  continue;
        			     
      		        };  
     		      if (Controlplane.hit(a)){
     		    	  a=null;
     		    	 t1.setText(Controlplane.life+"");
     		    	  anums.remove();
     		    	  m2.beepclip.stop();
     		    	  m2.eatclip.play();

     		    	  continue;
        			  //t2.setText(Controlplane.life+"");    
      		        };  
      		      if (mode.biperson&&Controlplane1.hit(a)){
     		    	  a=null;
     		    	  anums.remove();
     		    	  m2.beepclip.stop();
     		    	  m2.eatclip.play();
     		    	  t2.setText(Controlplane1.life+"");  
     		    	  continue;
        			  //t2.setText(Controlplane.life+"");    
      		        }; 
      		      //判断是否被击中?
      		      Iterator<Bullet> bnums = bulletsList.iterator();
      	    	   while(bnums.hasNext()) { 
      	  		      Bullet b = bnums.next(); 
      	  		      if (a.hit(b)) {
      	 		    	  b=null;
      	 		    	  bnums.remove();
      	 		    	 m2.hitclip.play();
      	  		      }; 
      	    	   }
    	    	   if (a.life<0) {
     		    	  explodeList.add(new Explode(a.aX,a.aY));
     		    	  a=null;
     		    	  m2.beepclip.stop();
     		    	  anums.remove();
  	 		    	  m2.explodeclip.play();
     		          };  		 
     	        } 
//子弹
    	   if (fire){
    		   if(Controlplane.bullettype==nmlBullet) {
    			   bulletsList.add(new Bullet(Controlplane.pX+Controlplane.pWidth/2-3,Controlplane.pY,13,13,nmlBullet,controller,1));
    		   }else if(Controlplane.bullettype==shotBullet) {
    			   bulletsList.add(new Bullet(Controlplane.pX+Controlplane.pWidth/2-3,Controlplane.pY,13,13,shotBullet,1,controller,1));
    			   bulletsList.add(new Bullet(Controlplane.pX+Controlplane.pWidth/2-3,Controlplane.pY,13,13,shotBullet,2,controller,1));
    			   bulletsList.add(new Bullet(Controlplane.pX+Controlplane.pWidth/2-3,Controlplane.pY,13,13,shotBullet,3,controller,1));
    		   }else if(Controlplane.bullettype==biBullet) {
    			   bulletsList.add(new Bullet(Controlplane.pX+Controlplane.pWidth/2+4,Controlplane.pY,13,13,biBullet,controller,1));
    			   bulletsList.add(new Bullet(Controlplane.pX+Controlplane.pWidth/2-10,Controlplane.pY,13,13,biBullet,controller,1));
    		   }
    		   fire=false;
    	   //t1.setText(Controlplane.bulletnum+"");  
           }
    	   if (mode.biperson&&fire1){
    		   if(Controlplane1.bullettype==nmlBullet) {
    			   bulletsList.add(new Bullet(Controlplane1.pX+Controlplane1.pWidth/2-3,Controlplane1.pY,13,13,nmlBullet,controller1,2));
    		   }else if(Controlplane1.bullettype==shotBullet) {
    			   bulletsList.add(new Bullet(Controlplane1.pX+Controlplane1.pWidth/2-3,Controlplane1.pY,13,13,shotBullet,1,controller1,2));
    			   bulletsList.add(new Bullet(Controlplane1.pX+Controlplane1.pWidth/2-3,Controlplane1.pY,13,13,shotBullet,2,controller1,2));
    			   bulletsList.add(new Bullet(Controlplane1.pX+Controlplane1.pWidth/2-3,Controlplane1.pY,13,13,shotBullet,3,controller1,2));
    		   }else if(Controlplane1.bullettype==biBullet) {
    			   bulletsList.add(new Bullet(Controlplane1.pX+Controlplane1.pWidth/2+4,Controlplane1.pY,13,13,biBullet,controller1,2));
    			   bulletsList.add(new Bullet(Controlplane1.pX+Controlplane1.pWidth/2-10,Controlplane1.pY,13,13,biBullet,controller1,2));
    		   }
    		   fire1=false;
    	   //t1.setText(Controlplane.bulletnum+"");  
           }
    	   Iterator<Bullet> bnums = bulletsList.iterator();
    	   while(bnums.hasNext()) { 
 		      Bullet b = bnums.next(); 
 		      drawOffScreen.drawImage(bullet,b.bX,b.bY,null);  
 		      b.bY-=b.speed; 
 		      if(b.bullettype==shotBullet) {
 		    	  switch (b.shotid) {
				case 1:
					b.bX-=b.xspeed;
					break;

				case 3:
					b.bX+=b.xspeed;
					break;
				default:
					break;
				}
 		      }
  		      if ((b.bY<0) || (b.bY>900)||(b.bX<0)||(b.bX>1000)){
 		    	  b=null;
 		    	  bnums.remove();
 		    	  continue;
  		      }
 		      if ((Controlplane.hit(b))){
 		    	  b=null;
 		    	  bnums.remove();
 		    	  m2.hitclip.play();
    			  t1.setText(Controlplane.life+"");    
 		    	     
 		    	  t3.setText(Controlplane.oil+"");   
 		    	  continue;
  		      };
  		    if (mode.biperson)
  		    	if(Controlplane1.hit(b)){
		    	  b=null;
		    	  bnums.remove();
		    	  m2.hitclip.play();    
		    	  t2.setText(Controlplane1.life+""); 
		    	  t4.setText(Controlplane1.oil+""); 
		    	//  t2.setText(Controlplane.life+"");    
		    	  //t3.setText(Controlplane.oil+"");    
		      };
 	        } 
    	    if (gameover==0) {
    	    	if(Controlplane.controller.over==false)
    	    		if(mode.endless) {
    	    			switch(Controlplane.controller.level) {
    	    			case 1:myplane=myplaneL1;break;
    	    			case 2:myplane=myplaneL2;break;
    	    			case 3:myplane=myplaneL3;break;
    	    			default:break;
    	    			}
    	    		}
    	    		drawOffScreen.drawImage(myplane,Controlplane.pX,Controlplane.pY,null);
    	    	if(mode.biperson)
    	    		if(Controlplane1.controller.over==false)
    	    			drawOffScreen.drawImage(myplane1,Controlplane1.pX,Controlplane1.pY,null);
    	    			}
    	    if (gameover==-1) drawOffScreen.drawImage(gameoverimage,450,300,null);  
    	    if (gameover==1) drawOffScreen.drawImage(winimage,450,300,null);  

  //判断是否被击中?
 		      if ((Controlplane.life<0) || (Controlplane.oil<0)) {
 		    	  if(flag1==false)
 		    		  explodeList.add(new Explode(Controlplane.pX,Controlplane.pY));
 		    	  flag1=true;
		    	  Controlplane.life=0;
		    	  Controlplane.oil=0;
		    	  Controlplane.controller.over=true;
	 		      m2.explodeclip.play();
		          };
		       if(mode.biperson==true) {
		    	   if ((Controlplane1.life<0) || (Controlplane1.oil<0)) {
		    		   if(flag2==false)
				    	  explodeList.add(new Explode(Controlplane1.pX,Controlplane1.pY));
		    		   flag2=true;
				       Controlplane1.life=0;
				    	  Controlplane1.oil=0;
				    	  Controlplane1.controller.over=true;
			 		      m2.explodeclip.play();
				          };
		    	   if((Controlplane.controller.over)&&(Controlplane1.controller.over)) {
			    	   gameover=-1;
		       }}else
		    	   if((Controlplane.controller.over)) {
			    	   gameover=-1;
		       
		       }
//判断是否胜利?
           if (planeList.size()==0) gameover=1;
//		          
	     if ((explodeList.size()==0) && (gameover!=0)) {
	         goon=false;
	     }
		   
		   Iterator<Explode> enums = explodeList.iterator();
 	   	   while(enums.hasNext()) { 
		      Explode e = enums.next(); 
		      drawOffScreen.drawImage(explode,e.eX,e.eY,null);  
		      e.life--; 
	
		      if (e.life<0) {
		    	  e=null;
		    	  enums.remove();
		          };  		      
  	        }     
 	    	//g.drawImage(OffScreen1,0,0,this.p2);
//更新位置
 	   	if(Controlplane.controller.over==false&&locationreflesh) {
 	   		if(mode.biperson==false)locationreflesh=false;
 		   if(controlflag[0] == true)Controlplane.pX+= Controlplane.speed+Controlplane.controller.speedincrement;
	    	   if(controlflag[1] == true)Controlplane.pX-= Controlplane.speed+Controlplane.controller.speedincrement;
	    	   if(controlflag[2] == true)Controlplane.pY-= Controlplane.speed+Controlplane.controller.speedincrement;
	    	   if(controlflag[3] == true)Controlplane.pY+= Controlplane.speed+Controlplane.controller.speedincrement; 
 	   }
 	   
 	   if(mode.biperson&&(Controlplane1.controller.over==false)&&locationreflesh) {
 		  locationreflesh=false;
 		   if(controlflag1[0] == true)Controlplane1.pX+= Controlplane1.speed+Controlplane1.controller.speedincrement;
	    	   if(controlflag1[1] == true)Controlplane1.pX-= Controlplane1.speed+Controlplane1.controller.speedincrement;
	    	   if(controlflag1[2] == true)Controlplane1.pY-= Controlplane1.speed+Controlplane1.controller.speedincrement;
	    	   if(controlflag1[3] == true)Controlplane1.pY+= Controlplane1.speed+Controlplane1.controller.speedincrement;
 	   }
 	}
	}

	/**
	 * 按键反应
	 */
  class MyKeyListener implements KeyListener{
	  Airplane Cplane=Controlplane;
	  Airplane Cplane1=Controlplane1;
	  int keyboard[]=new int[5];
	  int keyboard1[]=new int[5];
	       boolean key_flag[]= new boolean[5];
	       boolean key_flag1[]= new boolean[5];
	       MyKeyListener(boolean controlflag[],boolean controlflag1[]) {
	    	   key_flag=controlflag;
	    	   key_flag1=controlflag1;
	    	   for(int i=0;i<5;i++) {
	    		   key_flag[i]=false;
	    		   key_flag1[i]=false;
	    	   }
	    	   keyboard[0]=KeyEvent.VK_RIGHT;
	    	   keyboard[1]=KeyEvent.VK_LEFT;
	    	   keyboard[2]=KeyEvent.VK_UP;
	    	   keyboard[3]=KeyEvent.VK_DOWN;
	    	   keyboard[4]=KeyEvent.VK_SPACE;
	    	   keyboard1[0]=KeyEvent.VK_D;
	    	   keyboard1[1]=KeyEvent.VK_A;
	    	   keyboard1[2]=KeyEvent.VK_W;
	    	   keyboard1[3]=KeyEvent.VK_S;
	    	   keyboard1[4]=KeyEvent.VK_F;
	       }
	       @SuppressWarnings("deprecation")
		public void movePlane() {
	    	   if(Cplane.controller.over==false) {
		    	   if(key_flag[4] == true) {
		    		  // if  (Cplane.bulletnum-->=0)
		 	 	    	  fire=true;
		  		    	  m2.gunshotclip.play();
		    	   }   
	    	   }
	    	   
	    	   if(mode.biperson&&(Cplane1.controller.over==false)) {
		    	   if(key_flag1[4] == true) {
		    		  // if  (Cplane.bulletnum-->=0)
		 	 	    	  fire1=true;
		  		    	  m2.gunshotclip.play();
		    	   }  
	    	   }
	    	   
	       }
	 	   public void keyTyped(KeyEvent e)  { }
	 	   public void keyPressed(KeyEvent e)
	 	   {
	 		   key = e.getKeyCode();
	 		   if(key == keyboard[0])
	 	      {
	 			  key_flag[0]=true;
	 	      }
	 	      else if(key == keyboard[1])
	 	      {
	 	    	 key_flag[1]=true;
	 	      }
	 	      else if(key == keyboard[2])
	 	      {
	 	    	 key_flag[2]=true;
	 	      }
	 	      else if(key == keyboard[3])
	 	      {
	 	    	 key_flag[3]=true;
	 	      }
	 	      else if(key == keyboard[4])
	 	      {
	 	    	 key_flag[4]=true;
	 	      }
	 	      else if(key == keyboard1[0])
	 	      {
	 			  key_flag1[0]=true;
	 	      }
	 	      else if(key == keyboard1[1])
	 	      {
	 	    	 key_flag1[1]=true;
	 	      }
	 	      else if(key == keyboard1[2])
	 	      {
	 	    	 key_flag1[2]=true;
	 	      }
	 	      else if(key == keyboard1[3])
	 	      {
	 	    	 key_flag1[3]=true;
	 	      }
	 	      else if(key == keyboard1[4])
	 	      {
	 	    	 key_flag1[4]=true;
	 	      } 
	 		  movePlane();
	 	   }
	 	   public void keyReleased(KeyEvent e)
	 	   { 
	 		  key = e.getKeyCode();
	 		   if(key == KeyEvent.VK_RIGHT)
	 	      {
	 			  key_flag[0]=false;
	 	      }
	 	      else if(key == KeyEvent.VK_LEFT)
	 	      {
	 	    	 key_flag[1]=false;
	 	      }
	 	      else if(key == KeyEvent.VK_UP)
	 	      {
	 	    	 key_flag[2]=false;
	 	      }
	 	      else if(key == KeyEvent.VK_DOWN)
	 	      {
	 	    	 key_flag[3]=false;
	 	      }
	 	      else if(key == KeyEvent.VK_SPACE)
	 	      {
	 	    	 key_flag[4]=false;
	 	      } 
	 	     else if(key == keyboard1[0])
	 	      {
	 			  key_flag1[0]=false;
	 	      }
	 	      else if(key == keyboard1[1])
	 	      {
	 	    	 key_flag1[1]=false;
	 	      }
	 	      else if(key == keyboard1[2])
	 	      {
	 	    	 key_flag1[2]=false;
	 	      }
	 	      else if(key == keyboard1[3])
	 	      {
	 	    	 key_flag1[3]=false;
	 	      }
	 	      else if(key == keyboard1[4])
	 	      {
	 	    	 key_flag1[4]=false;
	 	      } 
	 		  movePlane();
	 	   }	   
	    }

	/**
	 * 显示菜单
	 */
	public void showcomponent(){
	   MenuBar m_MenuBar = new MenuBar(); 
	   Menu menuFile = new Menu("MenuFile");     //创建菜单
	   m_MenuBar.add(menuFile);                 //将菜单加入菜单条
	   MenuItem  f1 =  new MenuItem("Open");   //创建各菜单项
	   MenuItem  f2 = new MenuItem("Close");
	   menuFile.add(f1);                                       //加入菜单
	   menuFile.add(f2);
       setMenuBar(m_MenuBar);  
     //;
    	p1 = new Panel();
	    add(p1,"North");
	    p1.setLayout(new GridLayout(1,10)); 
	    if(mode.biperson) {
   	    p1.add(new Label("Health"),0);
	    t1 = new TextField(3);
	    p1.add(t1,1);
	    t2 = new TextField(3);
	    p1.add(t2,2);
   	    p1.add(new Label("Oil"),3);
	    t3 = new TextField(3);
	    p1.add(t3,4);
	    t4 = new TextField(3);
	    p1.add(t4,5);
        start=new Button("Start");
        p1.add(start,6);
	    start.addActionListener(new Startaction());
        save=new Button("Save");
        p1.add(save,7);
        save.addActionListener(new Saveaction());
        load=new Button("Load");
        p1.add(load,8);
	    }else {
	    	if(mode.endless) {
	    		p1.add(new Label("Health"),0);
			    t1 = new TextField(3);
			    p1.add(t1,1);
		   	    p1.add(new Label("Oil"),2);
			    t3 = new TextField(3);
			    p1.add(t3,3);
			    p1.add(new Label("Level"),4);
			    t5 = new TextField(3);
			    p1.add(t5,5);
			    p1.add(new Label("EXP"),6);
			    t6 = new TextField(3);
			    p1.add(t6,7);
		        start=new Button("Start");
		        p1.add(start,8);
			    start.addActionListener(new Startaction());
		        save=new Button("Save");
		        p1.add(save,9);
		        save.addActionListener(new Saveaction());
		        load=new Button("Load");
		        p1.add(load,10);
	    	}
	    	else {
	   	    p1.add(new Label("Health"),0);
		    t1 = new TextField(3);
		    p1.add(t1,1);
	   	    p1.add(new Label("Oil"),2);
		    t3 = new TextField(3);
		    p1.add(t3,3);
	        start=new Button("Start");
	        p1.add(start,4);
		    start.addActionListener(new Startaction());
	        save=new Button("Save");
	        p1.add(save,5);
	        save.addActionListener(new Saveaction());
	        load=new Button("Load");
	        p1.add(load,6);
	    	}
	        
	    }
        load.addActionListener(new Loadaction());
        	    
   //
	  	p2 = new Panel();
   	    add(p2);
	/*  	Choice ColorChooser = new Choice();
        ColorChooser.add("Green");
	    ColorChooser.add("Red"); 
	    ColorChooser.add("Blue");
	    p.add(ColorChooser);
	    t1 = new TextField(3);
	    p.add(t1);
	    ColorChooser.addItemListener(new ItemListener(){
 			 public void itemStateChanged(ItemEvent e){
				 String s= e.getItem().toString();
				 t1.setText(s);}
	    });*/
	   }
public static void main(String args[]){
	
	JFrame fs = new JFrame("模式选择");
    fs.setSize(1000, 900);
    //fs.setLocation(580, 240);
    fs.setLayout(null);
    JButton b1 = new JButton("单人模式");
    b1.setBounds(100, 100, 150, 50);
    JButton b2 = new JButton("双人模式");
    b2.setBounds(300, 100, 150, 50);
	JButton b3 = new JButton("无限模式");
    b3.setBounds(500, 100, 150, 50);
    JButton b4 = new JButton("闯关模式");
    b4.setBounds(700, 100, 150, 50);
    //JRadioButton b4 = new JRadioButton("xx模式");
    //b4.setBounds(100, 700, 200, 50);

    fs.add(b1);
    fs.add(b2);
    fs.add(b3);
	fs.add(b4);
    
    b1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	fieldmode mode=new fieldmode(0,1);
            Battlefield f = new Battlefield(mode);
        	f.addWindowListener(new WindowAdapter()
      			  {
      			   public void windowClosing(WindowEvent e) {
      			    System.exit(0);
      			   }
      			  });
            f.showcomponent();
            f.setSize(1000, 900);
            f.setVisible(true);
            fs.dispose();
            f.gameperpare();
//            f.gamebegin();
        }
    });
    b2.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
			fieldmode mode=new fieldmode(1,1);
        	Battlefield f = new Battlefield(mode);
        	f.addWindowListener(new WindowAdapter()
      			  {
      			   public void windowClosing(WindowEvent e) {
      			    System.exit(0);
      			   }
      			  });
            f.showcomponent();
            f.setSize(1000, 900);
            f.setVisible(true);
            fs.dispose();
            f.gameperpare();
            f.gameUnlimit();
            //f.gamebegin();
        }
    });
	b3.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
			fieldmode mode=new fieldmode(2,1);
        	Battlefield f = new Battlefield(mode);
        	f.addWindowListener(new WindowAdapter()
      			  {
      			   public void windowClosing(WindowEvent e) {
      			    System.exit(0);
      			   }
      			  });
            f.showcomponent();
            f.setSize(1000, 900);
            f.setVisible(true);
            fs.dispose();
            f.gameperpare();
            f.gameUnlimit();
            //f.gameUnlimit();
            //f.gamebegin();
        }
    });
    b4.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
			fieldmode mode=new fieldmode(3,1);
        	Battlefield f = new Battlefield(mode);
        	f.addWindowListener(new WindowAdapter()
      			  {
      			   public void windowClosing(WindowEvent e) {
      			    System.exit(0);
      			   }
      			  });
        	//f.showPrologue(drawOffScreen1);
            f.showcomponent();
            f.setSize(1000, 900);
            f.setVisible(true);
            fs.dispose();
            f.gameperpare();
            f.gameUnlimit();
            //f.gamebegin();
        }
    });
    fs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    fs.setVisible(true);
    
	}	
class Drawer extends Thread {
	public void run() {
		 while (goon)
         {
         flag.putf1begin();
         gameContrl(drawOffScreen1);
         flag.putf1end();
        
         flag.putf2begin();

         gameContrl(drawOffScreen2);
         flag.putf2end();

	 }
	}
}
class Displayer extends Thread {
	@SuppressWarnings("deprecation")
	public void run() {
		 while (goon)
         {
	       flag.getf1begin();
	       	 g.drawImage(OffScreen1,0,0,Battlefield.this.p2);
	       flag.getf1end();

	       flag.getf2begin();
	       	 g.drawImage(OffScreen2,0,0,Battlefield.this.p2);   
	       flag.getf2end();
	 }
		 System.out.println("Game Over");
		 timer.cancel();
		 timer=null;
		 timer2.cancel();
		 timer2=null;
		 m2.beepclip.stop();
		 m1.clip.stop();
		 m1=null;
		 start.enable();
	}
}
class Startaction implements ActionListener{
    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent event) {                    
    	goon=true;
    	gameover=0;
    	start.disable(); 
		gamebegin();
    }   
}

	/**
	 * 保存
	 */
class Saveaction implements ActionListener{
    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent event) {                    
        d1.suspend();
        d2.suspend();
        ObjectOutputStream oos;
		try {
			File f=new File("save/save.dat");
			if (f.exists())  f.delete();

			oos = new ObjectOutputStream(new FileOutputStream("save/save.dat"));
			oos.writeObject(Controlplane);
			oos.writeObject(Controlplane1);
			oos.writeObject(planeList);
			oos.writeObject(bulletsList);
			oos.writeObject(accessoryList);
			oos.writeObject(explodeList);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        d1.resume();
        d2.resume();
    }   
}
class Loadaction implements ActionListener{
    @SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent event) {                    

        ObjectInputStream ios;

			try {
				ios = new ObjectInputStream(new FileInputStream("save/save.dat"));
				Controlplane=(Airplane)ios.readObject();
				Controlplane1=(Airplane)ios.readObject();
				planeList=(ArrayList<Airplane>)ios.readObject();
				bulletsList=(ArrayList<Bullet>)ios.readObject();
    			accessoryList=(ArrayList<Accessory>)ios.readObject();
	    		explodeList=(ArrayList<Explode>)ios.readObject();
		    	ios.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TimerTask task=new TimerTask(){
				 @SuppressWarnings("deprecation")
				public void run(){
					 hasAccessory=true; 
				     m2.beepclip.loop();
				 }	
				};
			timer = new Timer();
			timer.schedule(task,0,delay);

		     TimerTask task2=new TimerTask(){
				 public void run(){
							 Controlplane.oil-=5 ; 
					         t3.setText(Controlplane.oil+""); 	
					         if(mode.biperson) {
					        	 Controlplane1.oil-=5 ;
					        	 t4.setText(Controlplane1.oil+""); 
					         }
						 }	
						};
			timer2 = new Timer();
			timer2.schedule(task2,3000,3000);
		    TimerTask task3=new TimerTask(){
				 public void run(){
	                 addplane=true;
	                       }	
				};
			timer3 = new Timer();
			timer3.schedule(task3,2000,40000);
			goon=true;
	    	gameover=0;
			p2.requestFocus();

			d1=new Drawer();
	    	d2=new Displayer();
	    	d1.start();
	        d2.start();


    }   
}

	/**
	 * 背景音乐
	 */
class Backgroudmusic {
	@SuppressWarnings("deprecation")
	AudioClip clip;
	@SuppressWarnings("deprecation")
	public void run() {
	File backmusic=new File("music/Every Breath You Take.mid");
	try{
		clip=Applet.newAudioClip(backmusic.toURL());
		clip.loop();
	}catch (Exception e) {};
   }
 }

	/**
	 * 音效
	 */
class Scenemusic {
	File gunshot,explode,beep,hit,eat;
	@SuppressWarnings("deprecation")
	AudioClip gunshotclip,explodeclip,beepclip,hitclip,eatclip;
	@SuppressWarnings("deprecation")
	public  Scenemusic(){
		super();
    		gunshot=new File("music/gunshot.wav");
    		explode=new File("music/explode.wav");
    		beep=new File("music/beep.wav");
    		hit=new File("music/hit.wav");
    		eat=new File("music/eat.wav");
		try{
			gunshotclip=Applet.newAudioClip(gunshot.toURL());
			explodeclip=Applet.newAudioClip(explode.toURL());
			beepclip=Applet.newAudioClip(beep.toURL());
			hitclip=Applet.newAudioClip(hit.toURL());
			eatclip=Applet.newAudioClip(eat.toURL());

		}catch (Exception e) {};
	}
/*	public void run() {
	  while (true) {
		  if (gunshot_voice>0) {gunshotclip.play();gunshot_voice--;};
		  if (explode_voice>0) {explodeclip.play();explode_voice--;};
		  if (accessory_voice>0) {beepclip.play(); accessory_voice--;};
	    }
	  }*/
  }
}
