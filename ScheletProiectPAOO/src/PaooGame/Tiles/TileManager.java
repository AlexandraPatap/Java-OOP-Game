package PaooGame.Tiles;

import PaooGame.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static PaooGame.Graphics.Assets.grass;
import static PaooGame.Graphics.Assets.tree;

public class TileManager {
    Game gp;
    public Tiles[] tile;
    public int[][] mapTileNum;
    public int level;

    public TileManager(Game gp,int level)
    {
        this.gp = gp;
        this.level = level;
        tile = new Tiles[4];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap();
    }

    public void getTileImage()
    {
        try
        {
            if(level == 1)
            {
                tile[0] = new Tiles();
                tile[0].image = ImageIO.read(getClass().getResourceAsStream("/textures/iarba1.png"));

                tile[1] = new Tiles();
                tile[1].image = ImageIO.read(getClass().getResourceAsStream("/textures/tree.png"));
                tile[1].collision=true;

                tile[2] = new Tiles();
                tile[2].image = ImageIO.read(getClass().getResourceAsStream("/textures/iarba1.png"));
                tile[2].collision = true;

                tile[3] = new Tiles();
                tile[3].image = ImageIO.read(getClass().getResourceAsStream("/textures/iarba1.png"));
                tile[3].collision = true;
                tile[3].nextLevel = true;
            }
            if(level == 2)
            {
                tile[0] = new Tiles();
                tile[0].image = ImageIO.read(getClass().getResourceAsStream("/textures/iarba1.png"));

                tile[1] = new Tiles();
                tile[1].image = ImageIO.read(getClass().getResourceAsStream("/textures/padure.png"));
                tile[1].collision=true;

                tile[2] = new Tiles();
                tile[2].image = ImageIO.read(getClass().getResourceAsStream("/textures/iarba1.png"));
                tile[2].collision = true;

                tile[3] = new Tiles();
                tile[3].image = ImageIO.read(getClass().getResourceAsStream("/textures/iarba1.png"));
                tile[3].collision = true;
                tile[3].nextLevel = true;
            }
            if(level == 3)
            {
                tile[0] = new Tiles();
                tile[0].image = ImageIO.read(getClass().getResourceAsStream("/textures/pamant.png"));

                tile[1] = new Tiles();
                tile[1].image = ImageIO.read(getClass().getResourceAsStream("/textures/tree3.png"));
                tile[1].collision=true;

                tile[2] = new Tiles();
                tile[2].image = ImageIO.read(getClass().getResourceAsStream("/textures/pamant.png"));
                tile[2].collision = true;

                tile[3] = new Tiles();
                tile[3].image = ImageIO.read(getClass().getResourceAsStream("/textures/pamant.png"));
                tile[3].collision = true;
                tile[3].nextLevel = true;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap()
    {
        if(level == 1)
        {
            try{
                File path= new File("res/levels/level1.txt");
                Scanner sc = new Scanner(path);
                mapTileNum=new int[gp.maxWorldRow][gp.maxWorldCol];

                for(int i=0;i< gp.maxWorldRow;i++)
                {
                    for(int j=0;j< gp.maxWorldCol;j++)
                    {
                        mapTileNum[i][j]= sc.nextInt();
                    }
                }
                sc.close();
            }catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        if(level == 2)
        {
            try{
                File path= new File("res/levels/level2.txt");
                Scanner sc = new Scanner(path);
                mapTileNum=new int[gp.maxWorldRow][gp.maxWorldCol];

                for(int i=0;i< gp.maxWorldRow;i++)
                {
                    for(int j=0;j< gp.maxWorldCol;j++)
                    {
                        mapTileNum[i][j]= sc.nextInt();
                    }
                }
                sc.close();
            }catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        else if(level == 3)
        {
            try{
                File path= new File("res/levels/level3.txt");
                Scanner sc = new Scanner(path);
                mapTileNum=new int[gp.maxWorldRow][gp.maxWorldCol];

                for(int i=0;i< gp.maxWorldRow;i++)
                {
                    for(int j=0;j< gp.maxWorldCol;j++)
                    {
                        mapTileNum[i][j]= sc.nextInt();
                    }
                }
                sc.close();
            }catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        int worldcol =0;
        int worldrow =0;

        while(worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow)
        {
            int tileNum = mapTileNum[worldrow][worldcol] - 1;

            int worldX = worldcol * gp.tileSize;
            int worldY = worldrow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; //sa stim unde sa desenam tile
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(tile[tileNum].image, screenX,screenY, gp.tileSize,gp.tileSize,null);
            worldcol++; //sa desenam fiecare coloana

            if(worldcol == gp.maxWorldCol)
            {
                //cand ajunge la capatul liniei matricei sa treaca pe urmatoarea linie
                worldcol=0;
                worldrow++;
            }
        }
    }

}
