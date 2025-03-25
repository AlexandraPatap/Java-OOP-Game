package PaooGame;

import PaooGame.Game;
import PaooGame.Objects.Floare_obiect;
import PaooGame.Objects.Inima_obiect;
import PaooGame.Objects.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InterfataUtilizator {
    Game gp;
    Graphics2D g2;
    BufferedImage heart_blank, heart_half, heart_full,floare;
    Font arial_40,ink_40;
    public int commandNum = 0;
    public int pauseStateScreen = 0;

    public InterfataUtilizator(Game gp)
    {
        this.gp = gp;

        arial_40= new Font("Arial", Font.BOLD, 30);
        ink_40= new Font("Ink Free", Font.BOLD,40);

        ///Creez obiect pt inima
        SuperObject heart= new Inima_obiect();
        heart_blank=heart.image;
        heart_half=heart.image2;
        heart_full=heart.image3;

        //creez obiect pt flori
        Floare_obiect floareObiect= new Floare_obiect();
        floare=floareObiect.image;
    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;

        g2.setFont(ink_40);
        g2.setColor(Color.white);

        //ECRAN TITLU
        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }

        //ECRAN DE JOC
        else if(gp.gameState == gp.playState)
        {
            //deseneaza scorul florilor
            g2.drawImage(floare,12*gp.tileSize, gp.tileSize/4,gp.tileSize,gp.tileSize, null);
            g2.drawString("x "+ gp.player.flori,13*gp.tileSize,gp.tileSize);

            //deseneaza inimile
            drawPlayerLife();
        }

        //ECRAN DE PAUZA
        else if(gp.gameState == gp.pauseState)
        {
            drawPauseScreen();
        }

        //ECRAN GAME OVER
        else if(gp.gameState == gp.overState)
        {
            drawGameOverScreen();
        }

        //ECRAN FINISH GAME
        else if(gp.gameState == gp.finishState)
        {
            drawFinishScreen();
        }

    }

    public void drawPlayerLife()
    {
        int x= gp.tileSize/2;
        int y= gp.tileSize/2;
        int i=0;

        //desenez maxlife (baza imaginilor inimii va fi una goala)
        //maxlife/2 --> sa desenez doar 3 inimi
        while(i<gp.player.maxlife/2)
        {
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+= gp.tileSize;
        }

        //reset (adica sa o iau de la prima inima si verific ce fel de inima trebuie desenata)
        x= gp.tileSize/2;
        y= gp.tileSize/2;
        i=0;

        //desenez viata curenta
        //initial toate inimile vor fi pline
        // vom pune imaginile pe parcurs in functie de viata curenta a jucatorului
        while(i<gp.player.life)
        {
//            if(gp.player.life < (gp.player.maxlife+1))
//            {
                g2.drawImage(heart_half,x,y,null);
                i++;
                //daca viata curenta este mai mare si dupa ce a marit inseamna ca are o inima plina si redesenam inima
                if(i<gp.player.life && i <= gp.player.maxlife)
                {
                    g2.drawImage(heart_full,x,y,null);
                }
                i++;
                x+= gp.tileSize;
//            }
//            else
//            {
//                if(i < gp.player.maxlife/2)
//                {
//                    g2.drawImage(heart_full,x,y,null);
//                }
//                i++;
//                x += gp.tileSize;
//                gp.player.life = gp.player.maxlife;
//            }
        }
    }

    public void drawPauseScreen()
    {

        if(pauseStateScreen == 0)
        {
            //poza joc
            try{
                BufferedImage fundal = ImageIO.read(getClass().getResourceAsStream("/levels/level1.png"));
                g2.drawImage(fundal,0,0,gp.screenWidth, gp.screenHeight, null);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //commandNum = 0;
            String text = "PAUSED";
            int x = getXptCentru(text);
            float y = gp.tileSize*2;

            g2.setColor(Color.darkGray);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.white);
            g2.drawString(text,x,y);

            //meniu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));

            text = "Save Game";
            x= getXptCentru(text);
            y= (float) (gp.tileSize*4);
            g2.setColor(Color.white);
            g2.drawString(text,x+3,y+3);
            if(commandNum == 0)
            {
                g2.drawString(">",x-gp.tileSize+3,y+3);
            }
            g2.setColor(new Color(0,100,160));
            g2.drawString(text,x,y);
            if(commandNum == 0)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Controls";
            x= getXptCentru(text);
            y= (float) (gp.tileSize*5.3);
            g2.setColor(Color.white);
            g2.drawString(text,x+3,y+3);
            if(commandNum == 1)
            {
                g2.drawString(">",x-gp.tileSize+3,y+3);
            }
            g2.setColor(new Color(0,100,160));
            g2.drawString(text,x,y);
            if(commandNum == 1)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }

        }
        else if(pauseStateScreen == 1)
        {
            //poza joc
            try{
                BufferedImage fundal = ImageIO.read(getClass().getResourceAsStream("/levels/level1.png"));
                g2.drawImage(fundal,0,0,gp.screenWidth, gp.screenHeight, null);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //controls
            String text = "Controls";
            int x= getXptCentru(text);
            float y= (float) (gp.tileSize*2);
            g2.setColor(Color.red);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.white);
            g2.drawString(text,x,y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));
            text = "W - printul se deplaseaza in sus";
            x= getXptCentru(text);
            y= gp.tileSize*4;
            g2.setColor(Color.white);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.black);
            g2.drawString(text,x,y);

            text = "S - printul se deplaseaza in jos";
            x= getXptCentru(text);
            y= gp.tileSize*5;
            g2.setColor(Color.black);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.white);
            g2.drawString(text,x,y);

            text = "A - printul se deplaseaza in stanga";
            x= getXptCentru(text);
            y= gp.tileSize*6;
            g2.setColor(Color.black);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.white);
            g2.drawString(text,x,y);

            text = "D - printul se deplaseaza in dreapta";
            x= getXptCentru(text);
            y= gp.tileSize*7;
            g2.setColor(Color.black);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.white);
            g2.drawString(text,x,y);

            text = "Back";
            x= getXptCentru(text);
            y=gp.tileSize*9;
            g2.setColor(Color.white);
            g2.drawString(text,x+3,y+3);
            if(commandNum == 0)
            {
                g2.drawString(">",x-gp.tileSize+3,y+3);
            }
            g2.setColor(new Color(0,100,160));
            g2.drawString(text,x,y);
            if(commandNum == 0)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }
        }
    }

    public void drawTitleScreen()
    {
        //poza joc
        try{
            BufferedImage fundal = ImageIO.read(getClass().getResourceAsStream("/levels/level1.png"));
            g2.drawImage(fundal,0,0,gp.screenWidth, gp.screenHeight, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //nume joc
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,65F));
        String text= "Save the princess";
        int x = getXptCentru(text);
        float y = gp.tileSize*2;

        //shadow
        g2.setColor(Color.darkGray);
        g2.drawString(text,x+3,y+3);

        //culoare initiala
        g2.setColor(new Color(220,20,60));
        g2.drawString(text,x,y);

        //meniu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));

        text = "New Game";
        x= getXptCentru(text) - 15;
        y= (float) (gp.tileSize*4);
        g2.setColor(Color.white);
        g2.drawString(text,x+3,y+3);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize+3,y+3);
        }
        g2.setColor(new Color(0,100,160));
        g2.drawString(text,x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "Load Game";
        x= getXptCentru(text) - 10;
        y= (float) (gp.tileSize*5.3);
        g2.setColor(Color.white);
        g2.drawString(text,x+3,y+3);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize+3,y+3);
        }
        g2.setColor(new Color(0,100,160));
        g2.drawString(text,x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "Quit";
        x= getXptCentru(text) - 9;
        y= (float) (gp.tileSize*6.6);
        g2.setColor(Color.white);
        g2.drawString(text,x+3,y+3);
        if(commandNum == 2)
        {
            g2.drawString(">",x-gp.tileSize+3,y+3);
        }
        g2.setColor(new Color(0,100,160));
        g2.drawString(text,x,y);
        if(commandNum == 2)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawGameOverScreen()
    {
        //poza joc
        try{
            BufferedImage fundal = ImageIO.read(getClass().getResourceAsStream("/levels/level1.png"));
            g2.drawImage(fundal,0,0,gp.screenWidth, gp.screenHeight, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //titlul
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,65F));
        String text= "Game Over";
        int x = getXptCentru(text);
        float y = gp.tileSize*2;

        //shadow
        g2.setColor(Color.darkGray);
        g2.drawString(text,x+3,y+3);

        //culoare initiala
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //meniu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));

        text = "Retry";
        x= getXptCentru(text) - 10;
        y= (float) (gp.tileSize*5.3);
        g2.setColor(Color.white);
        g2.drawString(text,x+3,y+3);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize+3,y+3);
        }
        g2.setColor(new Color(0,100,160));
        g2.drawString(text,x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "Quit";
        x= getXptCentru(text) - 9;
        y= (float) (gp.tileSize*6.6);
        g2.setColor(Color.white);
        g2.drawString(text,x+3,y+3);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize+3,y+3);
        }
        g2.setColor(new Color(0,100,160));
        g2.drawString(text,x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawFinishScreen()
    {
        //poza joc
        try{
            BufferedImage fundal = ImageIO.read(getClass().getResourceAsStream("/levels/level1.png"));
            g2.drawImage(fundal,0,0,gp.screenWidth, gp.screenHeight, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //titlul
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,65F));
        String text= "Congratulations";
        int x = getXptCentru(text);
        float y = gp.tileSize*2;

        //shadow
        g2.setColor(Color.darkGray);
        g2.drawString(text,x+3,y+3);

        //culoare initiala
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //meniu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));

        text = "Ai recuperat printesa!!!";
        x= getXptCentru(text) ;
        y= (float) (gp.tileSize*4);
        g2.setColor(Color.white);
        g2.drawString(text,x+3,y+3);

        text = "Quit";
        x= getXptCentru(text) - 10;
        y= (float) (gp.tileSize*5.3);
        g2.setColor(Color.white);
        g2.drawString(text,x+3,y+3);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize+3,y+3);
        }
        g2.setColor(new Color(0,100,160));
        g2.drawString(text,x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public int getXptCentru(String text)
    {
        int lenght = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - lenght/2;
        return x;
    }

}
