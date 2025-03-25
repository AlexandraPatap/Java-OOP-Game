package PaooGame.Entity;

import PaooGame.Game;
import PaooGame.Input.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    Game gp;
    KeyHandler keyH;
    public final int screenX; //unde desenam personajul pe mapa/fereastra
    public final int screenY;
    public static Player instance;
    public int flori =0;

    //STATUSUL PERSONAJULUI
    public int maxlife;
    public int life;

    private Player(Game gp,KeyHandler keyH)
    {
        this.gp=gp;
        this.keyH=keyH;

        screenX = gp.screenWidth/2 - (48/2);
        screenY = gp.screenHeight/2 - (48/2);

        solidArea= new Rectangle(); //as putea apela constructorul cu parametrii: x,y,width,height
        //nu vrem ca tot patratelul printului sa fie solid ca ar fi greu sa te mai incadrezi in labirint
        //luam cam cat e el defapt sau incercam
        solidArea.x= 12;
        solidArea.y= 16;
        solidDefaultX = solidArea.x;
        solidDefaultY = solidArea.y;
        solidArea.width= 26;
        solidArea.height= 28;


        setDefaulValues();
        getPlayerImage();
    }

    public void setPosition()
    {
        if(gp.tileM.level == 1)
        {
            setDefaulValues();
        }
        if(gp.tileM.level == 2)
        {
            worldX=49; //pozitia jucatorului pe mapa
            worldY=336;

            //worldX=gp.tileSize*9;
            //worldY=gp.tileSize*7;
        }
        else if(gp.tileM.level == 3)
        {
            worldX=49; //pozitia jucatorului pe mapa
            worldY=576;

            //worldX=gp.tileSize*22;
            //worldY=gp.tileSize*1;
        }
    }
    public static Player getInstance(Game gp, KeyHandler keyH)
    {
        if(instance == null)
        {
            instance = new Player(gp,keyH);
        }
        return instance;
    }

    public void setDefaulValues()
    {
        worldX=49; //pozitia jucatorului pe mapa
        worldY=185;
        speed=4;
        direction= "down";

        //status
        maxlife=6; //6 jumatati de inima --> 3 vieti= 3 inimi
        life=maxlife;
    }

    public void getPlayerImage()
    {
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/spate1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/spate2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/spate3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/fata1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/fata2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/fata3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/stanga1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/stanga2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/stanga3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/dreapta1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/dreapta2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/dreapta3.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true)
        {
            if (keyH.upPressed == true) {
                direction = "up";

            } else if (keyH.downPressed == true) {
                direction = "down";

            } else if (keyH.leftPressed == true) {
                direction = "left";

            } else if (keyH.rightPressed == true) {
                direction = "right";

            }

            //CHECK TILE COLLISION
            collisionOn = false;
            nextLevel = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int indexOb = gp.cChecker.checkObject(this,true);
            pickObject(indexOb);

            //CHECK MONSTER COLLISION
            inamicOn = false;
            int indexMo = gp.cChecker.checkMonster(this,gp.monstri2);
            intalnire(indexMo);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false)
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

            if(nextLevel == true && life > 0)
            {
                gp.tileM.level++;
            }
            else if(life <= 0)
            {
                if(gp.tileM.level > 1)
                {
                    gp.tileM.level--;
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

        //cand nu se misca printul
        if(invincibil == true)
        {
            invincibilCounter++;
            if(invincibilCounter > 60)
            {
                invincibil = false;
                invincibilCounter = 0;
            }
        }

        if(life > maxlife)
        {
            life = maxlife;
        }

        if(life <= 0)
        {
            gp.levelOn = true;
            gp.gameState = gp.overState;
        }

    }

    public void pickObject(int index)
    {
        Graphics g;
        if(index != 999)
        {
            String numeObj = gp.obj[index].name;

            switch(numeObj)
            {
                case "Floare":
                    flori++;
                    gp.obj[index] =null;//sterg obiectul pe care l-am atins
                    //System.out.println("flori: " + flori);
                    break;

                case "Mar":
                    life++;
                    gp.obj[index] =null;//sterg obiectul pe care l-am atins
                    //System.out.println("life: " + life);
                    break;

                case "Kiwi":
                    life--;
                    gp.obj[index] =null;//sterg obiectul pe care l-am atins
                    //System.out.println("life: " + life);
                    break;

                case "Printesa":
                    gp.gameState = gp.finishState;
                    break;
            }
        }
    }

    public void intalnire(int index)
    {
        if(inamicOn == true && index!=999)
        {
            if(invincibil == false && life > 0) {
                life = life - 2;
                //gp.monstri2[index] = null; //sterg monstrul pe care l-am atins
                System.out.println("life: " + life);
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
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
        //DREPTUNGHIUL DE COLIZIUNE
//        g2.setColor(new Color(0,100,160));
//        g2.drawRect(screenX + solidArea.x,screenY + solidArea.y,solidArea.width,solidArea.height);
    }
}
