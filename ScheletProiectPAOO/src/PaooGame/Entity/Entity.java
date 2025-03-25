package PaooGame.Entity;


import PaooGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Entity
    \brief. Implementeaza notiunea abstracta de entitate activa din joc, "element cu care se poate interactiona: monstru, floare etc.".
 */
public abstract class Entity
{
    Game gp;
    public String name;
    public int worldX,worldY;
    public int speed;
    public BufferedImage up1,up2,up3,down1,down2,down3,left1,left2,left3,right1,right2,right3; //folosit sa stocam imagini
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidDefaultX, solidDefaultY;
    public boolean collisionOn = false;
    public boolean nextLevel = false;
    public boolean inamicOn = false;
    public boolean invincibil = false; /*! nu primeste damage*/
    public int invincibilCounter = 0; /*! cat timp nu primeste damage*/
    public int type;
    public int actionLockCounter =0;

    public void update() {
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX; //aflam coordonatele pt camera
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        switch(direction)
        {
            case "up":
                if(spriteNum == 1)
                {
                    image = up1;
                }
                if(spriteNum == 2)
                {
                    image = up2;
                }
                if(spriteNum == 3)
                {
                    image = up3;
                }
                break;
            case "down":
                if(spriteNum == 1)
                {
                    image = down1;
                }
                if(spriteNum == 2)
                {
                    image = down2;
                }
                if(spriteNum == 3)
                {
                    image = down3;
                }
                break;
            case "left":
                if(spriteNum == 1)
                {
                    image = left1;
                }
                if(spriteNum == 2)
                {
                    image = left2;
                }
                if(spriteNum == 3)
                {
                    image = left3;
                }
                break;
            case "right":
                if(spriteNum == 1)
                {
                    image = right1;
                }
                if(spriteNum == 2)
                {
                    image = right2;
                }
                if(spriteNum == 3)
                {
                    image = right3;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,48,48,null);
    }
}