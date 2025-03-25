package PaooGame.Objects;

import PaooGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image, image2, image3, image4, image5, image6, image7;
    public int spriteC = 0;
    public int spriteN = 1;
    public String name;
    public boolean collision = false;
    public int worldX, worldY; //stim coordonatele pt harta mare
    public Rectangle solidS = new Rectangle(0,0,48,48);
    public int solidDefaultX = 0;
    public int solidDefaultY = 0;

    public void draw(Graphics2D g2, Game gp)
    {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //aflam coordonatele pt camera
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
        {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}