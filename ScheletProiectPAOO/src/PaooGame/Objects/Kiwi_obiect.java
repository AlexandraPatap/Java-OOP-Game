package PaooGame.Objects;

import PaooGame.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Kiwi_obiect extends SuperObject{
    public Kiwi_obiect()
    {
        name = "Kiwi";
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/textures/kiwi1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/textures/kiwi2.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/textures/kiwi3.png"));
            image4 = ImageIO.read(getClass().getResourceAsStream("/textures/kiwi4.png"));
            image5 = ImageIO.read(getClass().getResourceAsStream("/textures/kiwi5.png"));
            image6 = ImageIO.read(getClass().getResourceAsStream("/textures/kiwi6.png"));
            image7 = ImageIO.read(getClass().getResourceAsStream("/textures/kiwi7.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
        collision = true;
    }

    @Override
    public void draw(Graphics2D g2, Game gp) {
        BufferedImage im = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX; //aflam coordonatele pt camera
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        spriteC++;
        if (spriteC > 7) //la fiecare 10 frames se schimba imaginea player
        {
            if (spriteN == 1) {
                spriteN = 2;
            } else if (spriteN == 2) {
                spriteN = 3;
            } else if (spriteN == 3) {
                spriteN = 4;
            }else if (spriteN == 4) {
                spriteN = 5;
            }else if (spriteN == 5) {
                spriteN = 6;
            }else if (spriteN == 6) {
                spriteN = 7;
            }else if (spriteN == 7) {
                spriteN = 1;
            }
            spriteC = 0;
        }

        if(spriteN == 1)
        {
            im= this.image;
        }
        if(spriteN == 2)
        {
            im= this.image2;
        }
        if(spriteN == 3)
        {
            im= this.image3;
        }
        if(spriteN == 4)
        {
            im= this.image4;
        }
        if(spriteN == 5)
        {
            im= this.image5;
        }
        if(spriteN == 6)
        {
            im= this.image6;
        }
        if(spriteN == 7)
        {
            im= this.image7;
        }

        g2.drawImage(im, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
