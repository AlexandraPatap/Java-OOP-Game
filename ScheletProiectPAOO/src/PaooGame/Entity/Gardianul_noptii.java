package PaooGame.Entity;

import PaooGame.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gardianul_noptii extends Entity{
    Game gp;
    public Gardianul_noptii(Game gp)
    {
        this.gp=gp;
        name = "Gardianul noptii";
        type = 2;
        solidArea= new Rectangle();
        solidArea.x= 8;
        solidArea.y= 13;
        solidDefaultX = solidArea.x;
        solidDefaultY = solidArea.y;
        solidArea.width= 28;
        solidArea.height= 28;

        speed=4;
        direction= "left";

        getGardianImage();
    }

    public void getGardianImage()
    {
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_0.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_0_st.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_1_st.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_2_st.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_3_st.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/inamici/inamic1_3.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        //CHECK TILE COLLISION
            collisionOn = false;
            nextLevel = false;
            gp.cChecker.checkTile(this);

            //CHECK ENTITY COLLISION
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(type == 2 && contactPlayer == true)
            {
                if(gp.player.invincibil == false)
                {
                    gp.player.life -= 2;
                    gp.player.invincibil = true;
                }
            }

//           //CHECK OBJECT COLLISION
//           int indexOb = gp.cChecker.checkObject(this,false);

            //System.out.println("inamic collision: " + collisionOn);

            //IF COLLISION IS TRUE, MONSTER SWICTH DIRECTION
            if(collisionOn == true || gp.player.inamicOn == true)
            {
                if(gp.player.inamicOn == true)
                {
                    if(gp.player.invincibil == false)
                    {
                        gp.player.life = gp.player.life - 2;
                        gp.player.invincibil = true;
                    }

                    switch (direction)
                    {
                        case "left":
                            direction = "right";
                            break;
                        case "right":
                            direction = "left";
                            break;
                    }
                    gp.player.inamicOn = false;
                }

                switch (direction)
                {
                    case "left":
                        direction = "right";
                        break;
                    case "right":
                        direction = "left";
                        break;
                }
            }

            //IF COLLISION IS FALSE, MONSTER CAN MOVE
            if(collisionOn == false && gp.player.inamicOn == false)
            {
                switch (direction)
                {
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
                    spriteNum = 4;
                }else if(spriteNum == 4){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX; //aflam coordonatele pt camera
        int screenY = worldY - gp.player.worldY + gp.player.screenY;



        switch(direction)
        {
            case "left":
                if(spriteNum == 1)
                {
                    image = up2;
                }
                if(spriteNum == 2)
                {
                    image = left1;
                }
                if(spriteNum == 3)
                {
                    image = left2;
                }
                if(spriteNum == 4)
                {
                    image = left3;
                }
                break;
            case "right":
                if(spriteNum == 1)
                {
                    image = up1;
                }
                if(spriteNum == 2)
                {
                    image = right1;
                }
                if(spriteNum == 3)
                {
                    image = right2;
                }
                if(spriteNum == 4)
                {
                    image = right3;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,48,48,null);

        //DREPTUNGHIUL DE COLIZIUNE
//        g2.setColor(new Color(250,10,160));
//        g2.drawRect(screenX + solidArea.y,screenY + solidArea.y,solidArea.width,solidArea.height);
    }
}
