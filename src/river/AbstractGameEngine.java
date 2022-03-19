package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AbstractGameEngine implements GameEngine{

    protected Map<Item, GameObject> itemMap;
    protected Location boatLocation;
    protected int itemCount;


    public AbstractGameEngine() {
        itemMap = new HashMap<>();
        boatLocation = Location.START;
        itemCount = 0;
    }

    @Override
    public int numberOfItems() {
        return 0;
    }

    @Override
    public String getItemLabel(Item item) {
        return itemMap.get(item).getLabel();
    }

    @Override
    public Color getItemColor(Item item) {
        return itemMap.get(item).getColor();
    }

    @Override
    public Location getItemLocation(Item item) {
        return itemMap.get(item).getLocation();
    }

    @Override
    public void setItemLocation(Item item, Location location) {
        itemMap.get(item).setLocation(location);
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item item) {
        if (itemCount < 2) {
            if (getItemLocation(item) == boatLocation) {
                itemMap.get(item).setLocation(Location.BOAT);
                itemCount = itemCount + 1;
            }
        }
    }

    @Override
    public void unloadBoat(Item item) {
        if (itemCount > 0) {
            if (getItemLocation(item) == Location.BOAT) {
                itemMap.get(item).setLocation(getBoatLocation());
                itemCount = itemCount - 1;
            }
        }
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (itemCount > 0) {
            if (boatLocation == Location.START) {
                boatLocation = Location.FINISH;
            } else {
                boatLocation = Location.START;
            }
        }
    }

    @Override
    public boolean gameIsWon() {
        return false;
    }

    @Override
    public boolean gameIsLost() {
        return false;
    }

    @Override
    public void resetGame() {
        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) break;
            setItemLocation(item, Location.START);
        }
        boatLocation = Location.START;
        itemCount = 0;
    }
}
