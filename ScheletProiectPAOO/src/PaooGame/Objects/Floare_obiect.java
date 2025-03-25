package PaooGame.Objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Floare_obiect extends SuperObject{
    public Floare_obiect()
    {
        name = "Floare";
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/textures/floare.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
        collision = true;
    }
}
