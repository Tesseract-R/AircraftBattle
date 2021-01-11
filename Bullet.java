import java.awt.*;
import java.io.*;
public class Bullet implements Serializable {
	int bX,bY;
	int bWidth,bHeight;
	int speed=5;
	int xspeed=1;
	transient Image bimage;
	Bullettype bullettype;
	int shotid;
	int power;
	int parent_id;
	public Bullet(int x,int y,int w,int h,Bullettype btype){
		super();
		  bX=x;
		  bY=y;
		  bWidth=w;
		  bHeight=h;
		  bullettype=btype;
		  power=bullettype.power;
		  parent_id=0;
	}
	public Bullet(int x,int y,int w,int h,Bullettype btype,int shotid_in){//创建散弹
		super();
		  bX=x;
		  bY=y;
		  bWidth=w;
		  bHeight=h;
		  bullettype=btype;
		  shotid=shotid_in;
		  power=bullettype.power;
		  parent_id=0;
	}
	public Bullet(int x,int y,int w,int h,Bullettype btype,int shotid_in,ControlplaneAdvance controller,int id){//创建散弹
		super();
		  bX=x;
		  bY=y;
		  bWidth=w;
		  bHeight=h;
		  bullettype=btype;
		  shotid=shotid_in;
		  power=bullettype.power+controller.baseDamage;
		  parent_id=id;
	}
	public Bullet(int x,int y,int w,int h,Bullettype btype,ControlplaneAdvance controller,int id){
		super();
		  bX=x;
		  bY=y;
		  bWidth=w;
		  bHeight=h;
		  bullettype=btype;
		  power=bullettype.power+controller.baseDamage;
		  parent_id=id;
	}
	public void hit(){
		
	}
	public void moveToTop(){
		
	}
	public void moveToBottom(){
		
	}
	public void moveToleft(){
		
	}
	public void moveToRihgt(){
		
	}
}
