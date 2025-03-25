package PaooGame.Objects;

import PaooGame.Entity.Gardianul_noptii;
import PaooGame.Entity.regele;
import PaooGame.Game;

public class AssetSetter {
    Game gp;

    public AssetSetter(Game gp)
    {
        this.gp=gp;
    }

    public void setObject()
    {
        if(gp.tileM.level == 1)
        {
            gp.obj[0] = new Floare_obiect();
            gp.obj[0].worldX = 3 * gp.tileSize;
            gp.obj[0].worldY = 6 * gp.tileSize;

            gp.obj[1] = new Floare_obiect();
            gp.obj[1].worldX = 4 * gp.tileSize;
            gp.obj[1].worldY = 10 * gp.tileSize;

            gp.obj[2] = new Floare_obiect();
            gp.obj[2].worldX = 9 * gp.tileSize;
            gp.obj[2].worldY = 11 * gp.tileSize;

            gp.obj[3] = new Floare_obiect();
            gp.obj[3].worldX = 11 * gp.tileSize;
            gp.obj[3].worldY = 9 * gp.tileSize;

            gp.obj[4] = new Floare_obiect();
            gp.obj[4].worldX = 11 * gp.tileSize;
            gp.obj[4].worldY = 5 * gp.tileSize;

            gp.obj[5] = new Floare_obiect();
            gp.obj[5].worldX = 12 * gp.tileSize;
            gp.obj[5].worldY = 3 * gp.tileSize;

            gp.obj[6] = new Floare_obiect();
            gp.obj[6].worldX = 15 * gp.tileSize;
            gp.obj[6].worldY = 9 * gp.tileSize;

            gp.obj[7] = new Floare_obiect();
            gp.obj[7].worldX = 18* gp.tileSize;
            gp.obj[7].worldY = 4 * gp.tileSize;

            gp.obj[8] = new Floare_obiect();
            gp.obj[8].worldX = 23 * gp.tileSize;
            gp.obj[8].worldY = 2 * gp.tileSize;

            gp.obj[9] = new Floare_obiect();
            gp.obj[9].worldX = 19 * gp.tileSize;
            gp.obj[9].worldY = 13 * gp.tileSize;

            gp.obj[10] = new Mar_obiect();
            gp.obj[10].worldX = 14 * gp.tileSize;
            gp.obj[10].worldY = 1 * gp.tileSize;

            gp.obj[11] = new Mar_obiect();
            gp.obj[11].worldX = 22 * gp.tileSize;
            gp.obj[11].worldY = 2 * gp.tileSize;

            gp.obj[12] = new Kiwi_obiect();
            gp.obj[12].worldX = 5 * gp.tileSize;
            gp.obj[12].worldY = 10 * gp.tileSize;

            gp.obj[13] = new Kiwi_obiect();
            gp.obj[13].worldX = 17 * gp.tileSize;
            gp.obj[13].worldY = 6 * gp.tileSize;

            gp.obj[14] = new Kiwi_obiect();
            gp.obj[14].worldX = 21 * gp.tileSize;
            gp.obj[14].worldY = 10 * gp.tileSize;
        }
        else if(gp.tileM.level == 2)
        {
            gp.obj[0] = new Floare_obiect();
            gp.obj[0].worldX = 1 * gp.tileSize;
            gp.obj[0].worldY = 9 * gp.tileSize;

            gp.obj[1] = new Floare_obiect();
            gp.obj[1].worldX = 1 * gp.tileSize;
            gp.obj[1].worldY = 3 * gp.tileSize;

            gp.obj[2] = new Floare_obiect();
            gp.obj[2].worldX = 8 * gp.tileSize;
            gp.obj[2].worldY = 13 * gp.tileSize;

            gp.obj[3] = new Floare_obiect();
            gp.obj[3].worldX = 9 * gp.tileSize;
            gp.obj[3].worldY = 8 * gp.tileSize;

            gp.obj[4] = new Floare_obiect();
            gp.obj[4].worldX = 11 * gp.tileSize;
            gp.obj[4].worldY = 3 * gp.tileSize;

            gp.obj[5] = new Floare_obiect();
            gp.obj[5].worldX = 13 * gp.tileSize;
            gp.obj[5].worldY = 1 * gp.tileSize;

            gp.obj[6] = new Floare_obiect();
            gp.obj[6].worldX = 15 * gp.tileSize;
            gp.obj[6].worldY = 7 * gp.tileSize;

            gp.obj[7] = new Floare_obiect();
            gp.obj[7].worldX = 19 * gp.tileSize;
            gp.obj[7].worldY = 8 * gp.tileSize;

            gp.obj[8] = new Floare_obiect();
            gp.obj[8].worldX = 23 * gp.tileSize;
            gp.obj[8].worldY = 1 * gp.tileSize;

            gp.obj[9] = new Floare_obiect();
            gp.obj[9].worldX = 20 * gp.tileSize;
            gp.obj[9].worldY = 12 * gp.tileSize;

            gp.obj[10] = new Mar_obiect();
            gp.obj[10].worldX = 10 * gp.tileSize;
            gp.obj[10].worldY = 3 * gp.tileSize;

            gp.obj[11] = new Mar_obiect();
            gp.obj[11].worldX = 21 * gp.tileSize;
            gp.obj[11].worldY = 1 * gp.tileSize;

            gp.obj[12] = new Kiwi_obiect();
            gp.obj[12].worldX = 5 * gp.tileSize;
            gp.obj[12].worldY = 5 * gp.tileSize;

            gp.obj[13] = new Kiwi_obiect();
            gp.obj[13].worldX = 11 * gp.tileSize;
            gp.obj[13].worldY = 10 * gp.tileSize;

            gp.obj[14] = new Kiwi_obiect();
            gp.obj[14].worldX = 23 * gp.tileSize;
            gp.obj[14].worldY = 8 * gp.tileSize;
        }
        else if(gp.tileM.level == 3)
        {
            gp.obj[0] = new Floare_obiect();
            gp.obj[0].worldX = 1 * gp.tileSize;
            gp.obj[0].worldY = 3 * gp.tileSize;

            gp.obj[1] = new Floare_obiect();
            gp.obj[1].worldX = 2 * gp.tileSize;
            gp.obj[1].worldY = 1 * gp.tileSize;

            gp.obj[2] = new Floare_obiect();
            gp.obj[2].worldX = 3 * gp.tileSize;
            gp.obj[2].worldY = 13 * gp.tileSize;

            gp.obj[3] = new Floare_obiect();
            gp.obj[3].worldX = 12 * gp.tileSize;
            gp.obj[3].worldY = 4 * gp.tileSize;

            gp.obj[4] = new Floare_obiect();
            gp.obj[4].worldX = 9 * gp.tileSize;
            gp.obj[4].worldY = 13 * gp.tileSize;

            gp.obj[5] = new Floare_obiect();
            gp.obj[5].worldX = 15 * gp.tileSize;
            gp.obj[5].worldY = 1 * gp.tileSize;

            gp.obj[6] = new Floare_obiect();
            gp.obj[6].worldX = 19 * gp.tileSize;
            gp.obj[6].worldY = 4 * gp.tileSize;

            gp.obj[7] = new Floare_obiect();
            gp.obj[7].worldX = 20 * gp.tileSize;
            gp.obj[7].worldY = 12 * gp.tileSize;

            gp.obj[8] = new Floare_obiect();
            gp.obj[8].worldX = 21 * gp.tileSize;
            gp.obj[8].worldY = 8 * gp.tileSize;

            gp.obj[9] = new Floare_obiect();
            gp.obj[9].worldX = 19 * gp.tileSize;
            gp.obj[9].worldY = 1 * gp.tileSize;

            gp.obj[10] = new Mar_obiect();
            gp.obj[10].worldX = 6 * gp.tileSize;
            gp.obj[10].worldY = 5 * gp.tileSize;

            gp.obj[11] = new Mar_obiect();
            gp.obj[11].worldX = 12 * gp.tileSize;
            gp.obj[11].worldY = 8 * gp.tileSize;

            gp.obj[12] = new Kiwi_obiect();
            gp.obj[12].worldX = 3 * gp.tileSize;
            gp.obj[12].worldY = 1 * gp.tileSize;

            gp.obj[13] = new Kiwi_obiect();
            gp.obj[13].worldX = 16 * gp.tileSize;
            gp.obj[13].worldY = 13 * gp.tileSize;

            gp.obj[14] = new Kiwi_obiect();
            gp.obj[14].worldX = 22 * gp.tileSize;
            gp.obj[14].worldY = 13 * gp.tileSize;

            gp.obj[15] = new Printesa();
            gp.obj[15].worldX = 24 * gp.tileSize;
            gp.obj[15].worldY = 1 * gp.tileSize;
        }
    }

    public void setMonstri2(Game gp)
    {
        gp.monstri2[0] = new Gardianul_noptii(gp);
        gp.monstri2[0].worldX = 11 * gp.tileSize;
        gp.monstri2[0].worldY = 7 * gp.tileSize;

        gp.monstri2[1] = new Gardianul_noptii(gp);
        gp.monstri2[1].worldX = 19 * gp.tileSize;
        gp.monstri2[1].worldY = 10 * gp.tileSize;
    }

    public void setMonstri3(Game gp)
    {
        gp.monstri3[0] = new Gardianul_noptii(gp);
        gp.monstri3[0].worldX = 1 * gp.tileSize;
        gp.monstri3[0].worldY = 3 * gp.tileSize;

        gp.monstri3[1] = new Gardianul_noptii(gp);
        gp.monstri3[1].worldX = 12 * gp.tileSize;
        gp.monstri3[1].worldY = 11 * gp.tileSize;

        gp.monstri3[2] = new Gardianul_noptii(gp);
        gp.monstri3[2].worldX = 10 * gp.tileSize;
        gp.monstri3[2].worldY = 1 * gp.tileSize;

        gp.monstri3[3] = new regele(gp);
        gp.monstri3[3].worldX = 14 * gp.tileSize;
        gp.monstri3[3].worldY = 7 * gp.tileSize;
    }
}
