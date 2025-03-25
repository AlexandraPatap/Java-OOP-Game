package PaooGame.Entity;

import PaooGame.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class regele extends Entity{
    Game gp;
    public regele(Game gp)
    {
        this.gp=gp;
        name = "regele";
        type = 4;
        solidArea= new Rectangle();
        solidArea.x= 8;
        solidArea.y= 13;
        solidDefaultX = solidArea.x;
        solidDefaultY = solidArea.y;
        solidArea.width= 28;
        solidArea.height= 28;

        speed=6;
        direction= "left";

        getregeImage();
    }

    public void getregeImage()
    {
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_sp1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_sp2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_sp3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_fata1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_fata2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_fata3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_st1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_st2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_st3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_dr1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_dr2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/inamici/re_dr3.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        //System.out.println("inamic collision: " + collisionOn);

        actionLockCounter++;

        if(actionLockCounter == 120)
        {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if(i<=25)
            {
                direction = "up";
            }
            if(i>25 && i<=50)
            {
                direction = "down";
            }
            if(i>50 && i <=75)
            {
                direction = "left";
            }
            if(i>75 && i<=100)
            {
                direction = "right";
            }
            actionLockCounter = 0;
        }

        //CHECK TILE COLLISION
        collisionOn = false;
        nextLevel = false;
        gp.cChecker.checkTile(this);

        //CHECK ENTITY COLLISION
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if(type == 4 && contactPlayer == true)
        {
            if(gp.player.invincibil == false)
            {
                gp.player.life -= 3;
                gp.player.invincibil = true;
            }
        }

        //IF COLLISION IS FALSE, MONSTER CAN MOVE
        if(collisionOn == false && gp.player.inamicOn == false)
        {
            switch (direction)
            {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) //la fiecare 12 frames se schimba imaginea player
        {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX; //aflam coordonatele pt camera
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                if (spriteNum == 3) {
                    image = up3;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                if (spriteNum == 3) {
                    image = down3;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                if (spriteNum == 3) {
                    image = left3;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                if (spriteNum == 3) {
                    image = right3;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, 48, 48, null);
    }
}
