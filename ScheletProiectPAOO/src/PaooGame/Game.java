package PaooGame;

import PaooGame.Entity.Entity;
import PaooGame.Entity.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Objects.AssetSetter;
import PaooGame.Objects.SuperObject;
//import PaooGame.Tile.Tile;
//import PaooGame.Tile.Maps;
//import PaooGame.Tile.MapLoader;
import PaooGame.Input.KeyHandler;
import PaooGame.Tiles.TileManager;

import java.sql.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    //private MapLoader mapLoad;
    //private Maps map;
    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private Graphics g;          /*!< Referinta catre un context grafic.*/

    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    //private Tile tile;

    //SCREEN SETTINGS
    final int originTiles = 48;
    final int scale =1;

    public final int tileSize = originTiles * scale;
    public final int maxScreenCol = 15;
    public final int maxScreenRow = 10;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD SETTINGS
    public final int maxWorldCol = 25;
    public final int maxWorldRow = 15;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //SYSTEM
    public TileManager tileM = new TileManager(this,1);
    KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public InterfataUtilizator ui= new InterfataUtilizator(this);
    public boolean levelOn = false; //am trecut la nivel urmator
    public boolean load = false; //fac load game
    public boolean niv2 = false , niv3 = false; //verific daca vin dde la nivelul 2 sau 3

    //ENTITY AND OBJECT
    public Player player= Player.getInstance(this,keyH);
    public SuperObject[] obj = new SuperObject[20]; //putem afisa maxim 15 obiecte in acelasi timp
    //altfel incetineste jocul
    public Entity[] monstri2 = new Entity[2];
    public Entity[] monstri3 = new Entity[4];

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int overState = 3;
    public final int finishState = 4;

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height)
    {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;

    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame()
    {

        wnd = new GameWindow("Save the Princess", screenWidth, screenHeight);
            /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
            /// Se incarca toate elementele grafice (dale)
        wnd.GetCanvas().addKeyListener(keyH);
        wnd.GetCanvas().setFocusable(true); //jocul e concentrat sa primeasca key input

        conectareBaza();
        //creareTabel();

        Assets.Init();

    }

    //am creat o metoda pentru a mai putea adauga pe parcurs alte setup stuff
    public void setupGame()
    {
        aSetter.setObject();
        gameState = titleState;
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()
    {
            /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

            /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState == true)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }

    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()
    {
        if(runState == false)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;
                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame()
    {
        if(runState == true)
        {
                /// Actualizare stare thread
            runState = false;
                /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try
            {
                    /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                    /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            }
            catch(InterruptedException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else
        {
                /// Thread-ul este oprit deja.
            return;
        }
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update()
    {
        //System.out.println("Game state " + gameState);
        if(gameState == playState)
        {
            player.update();

            if(tileM.level == 2)
            {
                //monstri2
                for(int i=0;i< monstri2.length; i++)
                {
                    if(monstri2[i] != null)
                    {
                        monstri2[i].update();
                    }
                }
            }

            if(tileM.level == 3)
            {
                //monstri3
                for(int i=0;i< monstri3.length; i++)
                {
                    if(monstri3[i] != null)
                    {
                        monstri3[i].update();
                    }
                }
            }

        }
        if(gameState == pauseState)
        {
            //ecran de pauza
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw()
    {
            /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
                /// Se executa doar la primul apel al metodei Draw()
            try
            {
                    /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
            /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        Graphics2D g2= (Graphics2D)g;

        /// operatie de desenare

        //TITLE SCREEN
        if(gameState == titleState)
        {
            ui.draw(g2);
        }
        else if(gameState == playState)
        {
            //System.out.println("level " + tileM.level);
            if(tileM.level == 1)
            {
                if(load == true)
                {
                    load = false;
                }
                if(levelOn == true)
                {
                    if(niv2 == true)
                    {
                        //monstri2
                        for(int i=0;i< monstri2.length; i++)
                        {
                            if(monstri2[i] != null)
                            {
                                monstri2[i]= null;
                            }
                        }
                        niv2=false;
                    }

                    if(niv3 == true)
                    {
                        //obiecte
                        for(int i=0;i< obj.length; i++)
                        {
                            if(obj[i] != null)
                            {
                                obj[i]= null;
                            }
                        }

                        //monstri3
                        for(int i=0;i< monstri3.length; i++)
                        {
                            if(monstri3[i] != null)
                            {
                                monstri3[i]= null;
                            }
                        }
                        niv3=false;
                    }

                }
                //harta nivel 1
                tileM.draw(g2);
                                /*harta varianta cu clase separate pt tile
                                mapLoad = new MapLoader(1);
                                map = new Maps(1);
                                map.DrawMap(g, mapLoad.getMap(), mapLoad.getLines(), mapLoad.getColumns());
                                */

                //obiect
                for(int i=0;i< obj.length; i++)
                {
                    if(obj[i] != null)
                    {
                        obj[i].draw(g2,this);//vedem ce fel de obiect si afisam
                    }
                }

                //jucator = printul
                player.draw(g2);

                //interfata_utilizator: desenam viata
                ui.draw(g2);
            }
            else if(tileM.level == 2)
            {
                if (levelOn == true || load == true)
                {
                    niv2=true;
                    tileM.getTileImage();
                    tileM.loadMap();
                    aSetter.setObject();
                    aSetter.setMonstri2(this);
                    if(load == false)
                    {
                        player.setPosition();
                    }
                    else
                    {
                        load = false;
                    }
                    levelOn=false;

                }
                //harta 2
                tileM.draw(g2);

                //obiect
                for(int i=0;i< obj.length; i++)
                {
                    if(obj[i] != null)
                    {
                        obj[i].draw(g2,this);//vedem ce fel de obiect si afisam
                    }
                }

                //monstri2
                for(int i=0;i< monstri2.length; i++)
                {
                    if(monstri2[i] != null)
                    {
                        monstri2[i].draw(g2);
                    }
                }

                //jucator = printul
                player.draw(g2);

                //interfata_utilizator: desenam viata
                ui.draw(g2);
            }
            else if(tileM.level == 3)
            {
                if (levelOn == true || load == true)
                {
                    niv3=true;
                    tileM.getTileImage();
                    tileM.loadMap();
                    aSetter.setObject();
                    aSetter.setMonstri3(this);
                    if(load == false)
                    {
                        player.setPosition();
                    }
                    else
                    {
                        load=false;
                    }


                    //monstri2
                    for(int i=0;i< monstri2.length; i++)
                    {
                        if(monstri2[i] != null)
                        {
                            monstri2[i]= null;
                        }
                    }
                    levelOn=false;
                }

                //harta 3
                tileM.draw(g2);

                //obiect
                for(int i=0;i< obj.length; i++)
                {
                    if(obj[i] != null)
                    {
                        obj[i].draw(g2,this);//vedem ce fel de obiect si afisam
                    }
                }


                //monstri3
                for(int i=0;i< monstri3.length; i++) {
                    if (monstri3[i] != null) {
                        monstri3[i].draw(g2);
                    }
                }

                //jucator = printul
                player.draw(g2);


                //interfata_utilizator: desenam viata
                ui.draw(g2);
            }
        }
        else if (gameState == pauseState)
        {
            ui.draw(g2);
        }
        else if(gameState == overState)
        {
            ui.draw(g2);
        }
        else if(gameState == finishState)
        {
            ui.draw(g2);
        }
        // end operatie de desenare
            /// Se afiseaza pe ecran
        bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
        g2.dispose();
    }

    public int GetWidth()
    {
        return wnd.GetWndWidth();
    }
    public int GetHeight()
    {
        return wnd.GetWndHeight();
    }

    public void conectareBaza()
    {
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c= DriverManager.getConnection("jdbc:sqlite:SaveThePrincess.db");
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void creareTabel()
    {
        Connection c= null;
        Statement stmt = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c= DriverManager.getConnection("jdbc:sqlite:SaveThePrincess.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE SCORURI " +
                    "(LEVEL INT PRIMARY KEY NOT NULL, " +
                    "X INT NOT NULL," +
                    "Y INT NOT NULL," +
                    "FLORI INT NOT NULL," +
                    "LIFE INT NOT NULL)";
            stmt.execute(sql);
            stmt.close();
            c.close();
        }catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Created table successfully");
    }

    public void saveGame(int levelIndex, int xPos, int yPos , int flower, int life)
    {
        Connection c= null;
        Statement stmt = null;
        System.out.println("Saving game");

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SaveThePrincess.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            // stergem inregistrarile anterioare, nu salvam decat ultima "evolutie"
            String sql = "DELETE from SCORURI;";
            try (Statement stmtt = c.createStatement()) {
                stmtt.executeUpdate(sql);
            }

            sql = "INSERT INTO SCORURI (LEVEL,X,Y,FLORI,LIFE) " +
                    "VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setInt(1, levelIndex);
                pstmt.setInt(2, xPos);
                pstmt.setInt(3, yPos);
                pstmt.setInt(4, flower);
                pstmt.setInt(5, life);
                pstmt.executeUpdate();
            }
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public void loadGame()
    {

        Connection c= null;
        Statement stmt = null;
        System.out.println("Loading game");

        try
        {
            Class.forName("org.sqlite.JDBC");
            c= DriverManager.getConnection("jdbc:sqlite:SaveThePrincess.db");
            c.setAutoCommit(false);
            stmt= c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM SCORURI;");
            while(rs.next())
            {
                tileM.level = rs.getInt("LEVEL");
                player.worldX = rs.getInt("X");
                player.worldY = rs.getInt("Y");
                player.flori = rs.getInt("FLORI");
                player.life = rs.getInt("LIFE");
            }
            rs.close();
            stmt.close();
            c.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Load done successfully");
    }
}

