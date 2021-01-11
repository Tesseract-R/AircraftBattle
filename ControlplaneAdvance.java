import java.io.*;

public class ControlplaneAdvance implements Serializable {
	int baseDamage;
	int baseDefense;
	int speedincrement;
	int level;//等级
	int exp;//经验值
	int tempDefense;
	boolean over;


public ControlplaneAdvance(){
	super();
	baseDamage=20;
	baseDefense=19;
	speedincrement=0;
	over=false;
	level=1;
	exp=0;
}


}
