package PaooGame.Input;

import PaooGame.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    Game gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(Game gp)
    {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //ECRAN TITLU
        if(gp.gameState == gp.titleState)
        {
            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2)
                {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER)
            {
                switch (gp.ui.commandNum)
                {
                    case 0:
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        //load game
                        gp.load = true;
                        gp.loadGame();
                        gp.gameState = gp.playState;
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }

        //ECRAN JOC
        if(gp.gameState == gp.playState)
        {
            if(code == KeyEvent.VK_W)
            {
                upPressed=true;
            }
            if(code == KeyEvent.VK_S)
            {
                downPressed=true;
            }
            if(code == KeyEvent.VK_A)
            {
                leftPressed=true;
            }
            if(code == KeyEvent.VK_D)
            {
                rightPressed=true;
            }
            if(code == KeyEvent.VK_P)
            {
                gp.gameState = gp.pauseState;
            }
        }

        //ECRAN PAUZA
        else if(gp.gameState == gp.pauseState)
        {
            if(gp.ui.pauseStateScreen == 0)
            {
                if(code == KeyEvent.VK_W)
                {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0)
                    {
                        gp.ui.commandNum = 1;
                    }
                }
                if(code == KeyEvent.VK_S)
                {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 1)
                    {
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER)
                {
                    if(gp.ui.commandNum == 0)
                    {
                        //save game
                        gp.saveGame(gp.tileM.level, gp.player.worldX, gp.player.worldY, gp.player.flori, gp.player.life);
                    }
                    if(gp.ui.commandNum == 1)
                    {
                        //controls
                        gp.ui.pauseStateScreen = 1;
                    }
                }
                if(code == KeyEvent.VK_P)
                {
                    gp.gameState = gp.playState;
                }
            }
            else if(gp.ui.pauseStateScreen == 1)
            {
                //controls
                if(code == KeyEvent.VK_ENTER) {
                    gp.ui.pauseStateScreen = 0;
                }
            }
        }

        //ECRAN GAME OVER
        else if(gp.gameState == gp.overState)
        {
            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1)
                {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER)
            {
                switch (gp.ui.commandNum)
                {
                    case 0:
                        //retry
                        gp.tileM.level = 1;
                        gp.tileM.getTileImage();
                        gp.tileM.loadMap();
                        gp.aSetter.setObject();
                        gp.player.setPosition();
                        gp.player.flori = 0;
                        gp.levelOn=false;
                        if(gp.niv2 == true)
                        {
                            //monstri2
                            for(int i=0;i< gp.monstri2.length; i++)
                            {
                                if(gp.monstri2[i] != null)
                                {
                                    gp.monstri2[i]= null;
                                }
                            }
                            gp.niv2=false;
                        }
                        if(gp.niv3 == true)
                        {
                            //monstri2
                            for(int i=0;i< gp.monstri3.length; i++)
                            {
                                if(gp.monstri3[i] != null)
                                {
                                    gp.monstri3[i]= null;
                                }
                            }
                            gp.niv3=false;
                        }
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        //quit
                        gp.gameState = gp.titleState;
                        break;
                }
            }
        }

        //ECRAN FINISH GAME
        else if(gp.gameState == gp.finishState)
        {
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.titleState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            upPressed=false;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed=false;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed=false;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed=false;
        }
    }
}
