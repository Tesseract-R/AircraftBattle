import java.io.*;


public class fieldmode implements Serializable {
    boolean biperson;//双人模式
    boolean endless;//无尽模式
    int degree;//难度
    int planeSelect;

    public fieldmode(int id_in, int difficulty, int select) {
        biperson = false;
        endless = false;
        switch (id_in) {
            case 1:
                biperson = true;
                break;
            case 2:
                endless = true;
                break;
            default:
                break;
        }
        degree = difficulty;
        planeSelect = select;
    }

}
