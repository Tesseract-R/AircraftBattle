import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

public class fieldmode implements Serializable {
	boolean biperson;//˫��ģʽ
	boolean endless;//�޾�ģʽ
	boolean advance;//����ģʽ
	int degree;//�Ѷ�
	
	public fieldmode(int id_in,int difficulty){
		biperson=false;
		endless=false;
		advance=false;
		switch(id_in) {
		case 1:biperson=true;break;
		case 2:endless=true;break;
		case 3:advance=true;break;
		default:break;
		}
		degree=difficulty;
	}
	
}
