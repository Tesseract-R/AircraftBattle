import java.awt.*;
import java.io.*;
public class Bullettype implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int power;
	transient Image bimage;
	public Bullettype(int power_in,Image bimage_in){
		super();
		power=power_in;
		bimage=bimage_in;
	}
}