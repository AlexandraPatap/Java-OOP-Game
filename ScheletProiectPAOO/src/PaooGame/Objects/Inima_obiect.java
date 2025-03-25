package PaooGame.Objects;

import PaooGame.Game;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Inima_obiect extends SuperObject{
    public Inima_obiect()
    {
        name = "Inima";
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/heart/heart_blank.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/heart/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/heart/heart_full.png"));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        collision = true;
    }
}
