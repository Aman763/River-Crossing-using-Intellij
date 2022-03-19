package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class FarmerGameEngineTest {

    public static final Item FARMER = Item.ITEM_3;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item BEANS = Item.ITEM_0;
    private FarmerGameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new FarmerGameEngine();

    }

    @Test
    public void testObjectCallThrough() {
        Assert.assertEquals(4, engine.numberOfItems());

        Assert.assertEquals("", engine.getItemLabel(FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(FARMER));

        Assert.assertEquals("G", engine.getItemLabel(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(GOOSE));

        Assert.assertEquals("W", engine.getItemLabel(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(WOLF));

        Assert.assertEquals("B", engine.getItemLabel(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(BEANS));
    }

    @Test
    public void testMidTransportGoose() {
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        engine.loadBoat(FARMER);
        engine.loadBoat(GOOSE);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(GOOSE));
        engine.unloadBoat(GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE));
    }

    @Test
    public void testMidTransportBeans() {
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        engine.loadBoat(FARMER);
        engine.loadBoat(BEANS);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(BEANS));
        engine.unloadBoat(BEANS);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(BEANS));
    }

    @Test
    public void testMidTransportWolf() {
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        engine.loadBoat(FARMER);
        engine.loadBoat(WOLF);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(WOLF));
        engine.unloadBoat(WOLF);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(WOLF));
    }

    @Test
    public void testMidTransportFarmer() {
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        engine.loadBoat(FARMER);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(FARMER));
        engine.unloadBoat(FARMER);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(FARMER));
    }

    @Test
    public void testWinningGame() {
        // transport the goose
        engine.loadBoat(GOOSE);
        engine.loadBoat(FARMER);
        engine.rowBoat();
        engine.unloadBoat(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(BEANS);
        engine.rowBoat();
        engine.unloadBoat(BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(GOOSE);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(WOLF);
        engine.rowBoat();
        engine.unloadBoat(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(GOOSE);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());

    }

    @Test
    public void testLosingGame1() {
        engine.loadBoat(FARMER);
        transportHelper(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transportHelper(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testLosingGame2() {
        engine.loadBoat(FARMER);
        transportHelper(BEANS);
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testLosingGame3() {
        engine.loadBoat(FARMER);
        transportHelper(WOLF);
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testLosingGame4() {
        engine.loadBoat(FARMER);
        transportHelper(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        transportHelper(BEANS);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testResetGame() {
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));

        engine.loadBoat(FARMER);
        transportHelper(GOOSE);
        engine.rowBoat();
        engine.resetGame();

        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
    }

    @Test
    public void testCLick1() {
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));

        engine.loadBoat(FARMER);
        transportHelper(GOOSE);
        engine.loadBoat(WOLF);

        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(FARMER));
    }

    @Test
    public void testCLick2() {
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));

        engine.loadBoat(FARMER);
        engine.loadBoat(GOOSE);
        engine.loadBoat(WOLF);

        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(FARMER));
    }

    @Test
    public void testError() {
        engine.loadBoat(FARMER);
        engine.loadBoat(GOOSE);
        engine.rowBoat();
        engine.unloadBoat(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(WOLF);
        Location midLoc = engine.getItemLocation(GOOSE);
        Location bottomLoc = engine.getItemLocation(BEANS);
        Location playerLoc = engine.getItemLocation(FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(WOLF);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(WOLF));
        Assert.assertEquals(midLoc, engine.getItemLocation(GOOSE));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(BEANS));
        Assert.assertEquals(playerLoc, engine.getItemLocation(FARMER));
    }

    public void transportHelper(Item id){
        engine.loadBoat(id);
        engine.rowBoat();
        engine.unloadBoat(id);
    }
}
