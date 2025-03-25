package PaooGame;

import PaooGame.Entity.Entity;

public class CollisionChecker {
    Game gp;
    public CollisionChecker(Game gp)
    {
        this.gp=gp;
    }

    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        //aflam coloanele si randurile patratelului solid al player ului
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/ gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tilenum1, tilenum2;

        //pt fiecare directie verific colturile patratelului solid ce 2 tile-uri atinge
        //incercam sa prezicem in ce va intra
        //cu ajutorul la speed calculez in cam ce ar intra personajul si vad daca acel tile e solid sau nu
        switch(entity.direction)
        {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tilenum1= gp.tileM.mapTileNum[entityTopRow][entityLeftCol] - 1;
                tilenum2= gp.tileM.mapTileNum[entityTopRow][entityRightCol] - 1;
                if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true) //verificam daca tile e solid
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tilenum1].nextLevel == true || gp.tileM.tile[tilenum2].nextLevel == true)
                {
                    entity.nextLevel = true;
                    gp.levelOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tilenum1= gp.tileM.mapTileNum[entityBottomRow][entityLeftCol] - 1;
                tilenum2= gp.tileM.mapTileNum[entityBottomRow][entityRightCol] - 1;
                if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true) //verificam daca tile e solid
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tilenum1].nextLevel == true || gp.tileM.tile[tilenum2].nextLevel == true)
                {
                    entity.nextLevel = true;
                    gp.levelOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tilenum1= gp.tileM.mapTileNum[entityTopRow][entityLeftCol] - 1;
                tilenum2= gp.tileM.mapTileNum[entityBottomRow][entityLeftCol] - 1;
                if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true) //verificam daca tile e solid
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tilenum1].nextLevel == true || gp.tileM.tile[tilenum2].nextLevel == true)
                {
                    entity.nextLevel = true;
                    gp.levelOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tilenum1= gp.tileM.mapTileNum[entityTopRow][entityRightCol] - 1;
                tilenum2= gp.tileM.mapTileNum[entityBottomRow][entityRightCol] - 1;
                if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true) //verificam daca tile e solid
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tilenum1].nextLevel == true || gp.tileM.tile[tilenum2].nextLevel == true)
                {
                    entity.nextLevel = true;
                    gp.levelOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) //primim o entitate si vom veriica daca e jucatorul
    {
        //verificam daca jucatorul se atinge cu vreun obiect si
        // returnam indexul obiectului din vector
        int index = 999;

        for(int i =0 ; i<gp.obj.length;i++)
        {
            if(gp.obj[i] != null) //sa existe obiect
            {
                //pozitia portiunii solide din jucator
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //pozitia portiunii solide din obiect
                gp.obj[i].solidS.x = gp.obj[i].worldX + gp.obj[i].solidS.x; //x si y sunt 0 si nu se intampla nimic neaparat dar nu incurca
                gp.obj[i].solidS.y = gp.obj[i].worldY + gp.obj[i].solidS.y;

                switch (entity.direction)
                {
                    //unde va fi portiunea solida a jucatorului in functie de directie
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidS)) //verifica daca se intalnesc portiunile solide
                        {
                            //System.out.println("up collision");
                            if(gp.obj[i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)//primim o entitate si vom verifica daca e jucatorul
                            {
                                index=i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidS))
                        {
                            //System.out.println("down collision");
                            if(gp.obj[i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)
                            {
                                index=i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidS))
                        {
                            //System.out.println("left collision");
                            if(gp.obj[i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)
                            {
                                index=i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x +=entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidS))
                        {
                            //System.out.println("right collision");
                            if(gp.obj[i].collision == true)
                            {
                                entity.collisionOn = true;
                            }
                            if(player == true)
                            {
                                index=i;
                            }
                        }
                        break;
                }
                //le repunem la default ca toate calculele de mai sus le vor schimba si incrementa
                entity.solidArea.x = entity.solidDefaultX;
                entity.solidArea.y = entity.solidDefaultY;
                gp.obj[i].solidS.x = gp.obj[i].solidDefaultX;
                gp.obj[i].solidS.y = gp.obj[i].solidDefaultY;
            }
        }

        return index;
    }

    public int checkMonster(Entity entity,Entity[] tinta) /*!verifica coliziunea player monstru */
    {
        int index = 999;
        for(int i = 0; i < tinta.length; i++)
        {
            if(tinta[i] != null)
            {
                //gasim pozitia solida a entity
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //gasim pozitia solida a monstrului
                tinta[i].solidArea.x = tinta[i].worldX + tinta[i].solidArea.x;
                tinta[i].solidArea.y = tinta[i].worldY + tinta[i].solidArea.y;

                switch (entity.direction)
                {
                    //unde va fi portiunea solida a jucatorului in functie de directie
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x +=entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(tinta[i].solidArea) && tinta[i] != entity) {
                    entity.inamicOn = true;
                    entity.collisionOn = true;
                    index = i;
                }

                //le repunem la default ca toate calculele de mai sus le vor schimba si incrementa
                entity.solidArea.x = entity.solidDefaultX;
                entity.solidArea.y = entity.solidDefaultY;
                gp.monstri2[i].solidArea.x = gp.monstri2[i].solidDefaultX;
                gp.monstri2[i].solidArea.y = gp.monstri2[i].solidDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) /*!verifica coliziunea monstru player*/
    {
        boolean contactplayer = false;

        //gasim pozitia solida a entity
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //gasim pozitia solida a monstrului
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction)
        {
            //unde va fi portiunea solida a jucatorului in functie de directie
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x +=entity.speed;
                break;
        }

        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.inamicOn = true;
            entity.collisionOn = true;
            contactplayer = true;
        }

        //le repunem la default ca toate calculele de mai sus le vor schimba si incrementa
        entity.solidArea.x = entity.solidDefaultX;
        entity.solidArea.y = entity.solidDefaultY;
        gp.player.solidArea.x = gp.player.solidDefaultX;
        gp.player.solidArea.y = gp.player.solidDefaultY;

        return contactplayer;
    }


}
