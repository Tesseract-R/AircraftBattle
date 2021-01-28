import java.awt.*;
import java.io.*;

public class Accessorytype implements Serializable {
    Image aImage;
    int id;
    boolean beequipment;
    Bullettype btype;
    boolean stoneDirect;
    Shieldtype Shieldtype;

    public Accessorytype(int id_in, Image aImage_in) {
        id = id_in;
        aImage = aImage_in;
        beequipment = false;
    }

    public Accessorytype(int id_in, Image aImage_in, boolean stoneDirect_in) {
        id = id_in;
        aImage = aImage_in;
        beequipment = false;
        stoneDirect = stoneDirect_in;
    }

    public Accessorytype(int id_in, Image aImage_in, Bullettype btype_in) {
        id = id_in;
        aImage = aImage_in;
        beequipment = true;
        btype = btype_in;
    }

    public Accessorytype(int id_in, Image aImage_in, Shieldtype shieldtype_in) {
        id = id_in;
        aImage = aImage_in;
        beequipment = false;
        Shieldtype = shieldtype_in;
    }


}
