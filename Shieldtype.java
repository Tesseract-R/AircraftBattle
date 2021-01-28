import java.awt.*;
import java.io.Serializable;

public class Shieldtype implements Serializable {
    int id;
    Image sImage;

    public Shieldtype(int id_in, Image sImage_in) {
        id = id_in;
        sImage = sImage_in;
    }



}
