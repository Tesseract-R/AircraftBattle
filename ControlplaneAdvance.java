import java.io.*;

public class ControlplaneAdvance implements Serializable {
    int baseDamage;
    int baseDefense;
    int speedincrement;
    int level;//�ȼ�
    int exp;//����ֵ
    int score;
    int tempDefense;
    boolean over;


    public ControlplaneAdvance() {
        super();
        baseDamage = 20;
        baseDefense = 15;
        speedincrement = 0;
        over = false;
        level = 1;
        exp = 0;
        score = 100;
    }

}
