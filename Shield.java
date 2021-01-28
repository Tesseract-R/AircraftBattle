import java.io.Serializable;

public class Shield implements Serializable {
    int sX, sY;
    int sWidth, sHeight;
    int life = 100;
    Shieldtype stype;

    public Shield(Airplane plane, Shieldtype stype_in) {
        sWidth = 80;
        sHeight = 30;
        sX = plane.pX;
        sY = plane.pY-sHeight*2;
        stype = stype_in;
    }

    public boolean hit(Bullet b) {
        if (b.bullettype.bulletFrom.equals("computer"))
            if ((sX < b.bX) && (b.bX < sX + sWidth) && (sY < b.bY) && (b.bY < sY + sHeight)) {
                life-=b.power;
                return true;
            } else return false;
        else return false;
    }

}
