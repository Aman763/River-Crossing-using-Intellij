package river;

import java.awt.*;


public class FarmerGameEngine extends AbstractGameEngine{

    public static final Item BEANS = Item.ITEM_0;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item FARMER = Item.ITEM_3;


    @Override
    public int numberOfItems() {
        return 4;
    }


    public FarmerGameEngine() {
        boatLocation = Location.START;
        itemMap.put(WOLF, new GameObject("W", Location.START, Color.CYAN));
        itemMap.put(GOOSE, new GameObject("G", Location.START, Color.CYAN));
        itemMap.put(BEANS, new GameObject("B", Location.START, Color.CYAN));
        itemMap.put(FARMER, new GameObject("", Location.START, Color.MAGENTA));
        itemCount = 0;
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (itemCount > 0) {
            if (getItemLocation(FARMER) != Location.BOAT) {
                return;
            }
            if (boatLocation == Location.START) {
                boatLocation = Location.FINISH;
            } else {
                boatLocation = Location.START;
            }
        }
    }

    @Override
    public boolean gameIsWon() {
        return getItemLocation(WOLF) == Location.FINISH && getItemLocation(GOOSE) == Location.FINISH
                && getItemLocation(BEANS) == Location.FINISH && getItemLocation(FARMER) == Location.FINISH;
    }

    @Override
    public boolean gameIsLost() {
        if (getItemLocation(GOOSE) == Location.BOAT) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(FARMER)) {
            return false;
        }
        if (getItemLocation(GOOSE) == boatLocation) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(WOLF)) {
            return true;
        }
        if (getItemLocation(GOOSE) == getItemLocation(BEANS)) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        itemMap.get(WOLF).setLocation(Location.START);
        itemMap.get(GOOSE).setLocation(Location.START);
        itemMap.get(BEANS).setLocation(Location.START);
        itemMap.get(FARMER).setLocation(Location.START);
        boatLocation = Location.START;
    }

}
