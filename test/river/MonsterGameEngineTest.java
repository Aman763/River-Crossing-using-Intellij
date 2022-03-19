package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class MonsterGameEngineTest {

    public static final Item MONSTER1 = Item.ITEM_0;
    public static final Item MUNCHKINS1 = Item.ITEM_1;
    public static final Item MONSTER2 = Item.ITEM_2;
    public static final Item MUNCHKINS2 = Item.ITEM_3;
    public static final Item MONSTER3 = Item.ITEM_4;
    public static final Item MUNCHKINS3 = Item.ITEM_5;
    private MonsterGameEngine engine;


    @Before
    public void setUp() throws Exception {
        engine = new MonsterGameEngine();

    }

    @Test
    public void testObjectCallThroughs() {
//        GameObject farmer = new GameObject("Farmer");
        Assert.assertEquals("M1", engine.getItemLabel(MONSTER1));
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER1));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(MONSTER1));

        Assert.assertEquals("M2", engine.getItemLabel(MONSTER2));
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER2));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(MONSTER2));

        Assert.assertEquals("M3", engine.getItemLabel(MONSTER3));
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER3));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(MONSTER3));

        /* TODO Check getters for wolf, goose, and beans */

//        GameObject goose = new GameObject("Goose");
//        Assert.assertEquals("Goose", goose.getName());
//        Assert.assertEquals(location.START, goose.getLocation());
//        Assert.assertEquals("Honk", goose.getSound());

        Assert.assertEquals("K1", engine.getItemLabel(MUNCHKINS1));
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKINS1));
        Assert.assertEquals(Color.BLUE, engine.getItemColor(MUNCHKINS1));

//        GameObject wolf = new GameObject("Wolf");
//        Assert.assertEquals("Wolf", wolf.getName());
//        Assert.assertEquals(location.START, wolf.getLocation());
//        Assert.assertEquals("Howl", wolf.getSound());

        Assert.assertEquals("K2", engine.getItemLabel(MUNCHKINS2));
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKINS2));
        Assert.assertEquals(Color.BLUE, engine.getItemColor(MUNCHKINS2));

//        GameObject beans = new GameObject("Beans");
//        Assert.assertEquals("Beans", beans.getName());
//        Assert.assertEquals(location.START, beans.getLocation());
//        Assert.assertEquals("", beans.getSound());

        Assert.assertEquals("K3", engine.getItemLabel(MUNCHKINS3));
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKINS3));
        Assert.assertEquals(Color.BLUE, engine.getItemColor(MUNCHKINS3));
    }

    @Test
    public void testMidTransport() {
//        GameEngine engine = new GameEngine();
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKINS2));
        /*
         * TODO Transport he goose to the other side, unload it, and check that it has
         * the appropriate location
         */

        engine.loadBoat(MUNCHKINS2);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(MUNCHKINS2));
        engine.unloadBoat(MUNCHKINS2);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(MUNCHKINS2));
    }

    @Test
    public void testWinningGame() {

//        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(MONSTER1);
        engine.loadBoat(MONSTER2);
        engine.rowBoat();
        engine.unloadBoat(MONSTER2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        /*
         * TODO The steps above are the first two steps in a winning game, complete the
         * steps and check that the game is won.
         */

        engine.loadBoat(MONSTER3);
        engine.rowBoat();
        engine.unloadBoat(MONSTER3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(MONSTER1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(MUNCHKINS1);
        engine.loadBoat(MUNCHKINS2);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(MUNCHKINS2);
        engine.loadBoat(MONSTER2);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(MONSTER2);
        engine.loadBoat(MUNCHKINS3);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(MUNCHKINS1);
        engine.unloadBoat(MUNCHKINS3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(MONSTER3);
        engine.rowBoat();
        engine.loadBoat(MONSTER1);
        engine.rowBoat();
        engine.unloadBoat(MONSTER1);
        engine.rowBoat();
        engine.loadBoat(MONSTER2);
        engine.rowBoat();
        engine.unloadBoat(MONSTER2);
        engine.unloadBoat(MONSTER3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());

    }

    @Test
    public void testLosingGame() {

//        GameEngine engine = new GameEngine();

        // transport the goose
//        engine.loadBoat(Item.GOOSE);
        engine.loadBoat(MUNCHKINS1);
//        engine.rowBoat();
//        engine.unloadBoat(Item.GOOSE);
        transportHelper(MONSTER1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        /*
         * TODO Once you transport the goose, go back alone, pick up the wolf, transport
         * the wolf, then go back alone again. After you go back alone the second time,
         * check that the game is lost.
         */

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

//        engine.loadBoat(Item.WOLF);
//        engine.rowBoat();
//        engine.unloadBoat(Item.WOLF);
        engine.loadBoat(MUNCHKINS2);

        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

//        GameEngine engine = new GameEngine();

        // transport the goose
//        engine.loadBoat(GOOSE);
//        engine.rowBoat();
//        engine.unloadBoat(GOOSE);
//        Assert.assertFalse(engine.gameIsLost());
//        Assert.assertFalse(engine.gameIsWon());

        // save the state
//        Location topLoc = engine.getItemLocation(WOLF);
//        Location midLoc = engine.getItemLocation(GOOSE);
//        Location bottomLoc = engine.getItemLocation(BEANS);
//        Location playerLoc = engine.getItemLocation(FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
//        engine.loadBoat(WOLF);
//
//         check that the state has not changed
//        Assert.assertEquals(topLoc, engine.getItemLocation(WOLF));
//        Assert.assertEquals(midLoc, engine.getItemLocation(GOOSE));
//        Assert.assertEquals(bottomLoc, engine.getItemLocation(BEANS));
//        Assert.assertEquals(playerLoc, engine.getItemLocation(FARMER));
    }

    public void transportHelper(Item id){
        engine.loadBoat(id);
        engine.rowBoat();
        engine.unloadBoat(id);
    }
}