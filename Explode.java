import java.awt.*;
import java.io.*;

public class Explode implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int eX, eY;
    int eWidth, eHeight;
    int life = 10;
    transient Image eimage;

    public Explode(int x, int y, Image eimage_in) {
        super();
        eX = x;
        eY = y;
        eimage = eimage_in;
    }

    public void hit() {

    }

    public void moveToTop() {

    }

    public void moveToBottom() {

    }

    public void moveToleft() {

    }

    public void moveToRihgt() {

    }
}
