package river;

import java.awt.*;

public class MonsterGameEngine extends AbstractGameEngine{

    public static final Item MONSTER1 = Item.ITEM_0;
    public static final Item MUNCHKINS1 = Item.ITEM_1;
    public static final Item MONSTER2 = Item.ITEM_2;
    public static final Item MUNCHKINS2 = Item.ITEM_3;
    public static final Item MONSTER3 = Item.ITEM_4;
    public static final Item MUNCHKINS3 = Item.ITEM_5;


    @Override
    public int numberOfItems() {
        return 6;
    }


    public MonsterGameEngine() {
        boatLocation = Location.START;
        itemMap.put(MONSTER1, new GameObject("M1", Location.START, Color.GREEN));
        itemMap.put(MONSTER2, new GameObject("M2", Location.START, Color.GREEN));
        itemMap.put(MONSTER3, new GameObject("M3", Location.START, Color.GREEN));
        itemMap.put(MUNCHKINS1, new GameObject("K1", Location.START, Color.BLUE));
        itemMap.put(MUNCHKINS2, new GameObject("K2", Location.START, Color.BLUE));
        itemMap.put(MUNCHKINS3, new GameObject("K3", Location.START, Color.BLUE));
        itemCount = 0;
    }

    @Override
    public boolean gameIsWon() {
        return (getItemLocation(MONSTER1) == Location.FINISH && getItemLocation(MUNCHKINS1) == Location.FINISH
                && getItemLocation(MONSTER2) == Location.FINISH && getItemLocation(MUNCHKINS2) == Location.FINISH
                && getItemLocation(MONSTER3) == Location.FINISH && getItemLocation(MUNCHKINS3) == Location.FINISH);
    }

    @Override
    public boolean gameIsLost() {
        if (monstersAtLeftShore() > munchkinsAtLeftShore() && munchkinsAtLeftShore() > 0) {
            return true;
        }
        if (monstersAtRightShore() > munchkinsAtRightShore() && munchkinsAtRightShore() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        itemMap.get(MONSTER1).setLocation(Location.START);
        itemMap.get(MUNCHKINS1).setLocation(Location.START);
        itemMap.get(MONSTER2).setLocation(Location.START);
        itemMap.get(MUNCHKINS2).setLocation(Location.START);
        itemMap.get(MONSTER3).setLocation(Location.START);
        itemMap.get(MUNCHKINS3).setLocation(Location.START);
        boatLocation = Location.START;
    }

    private boolean itemAtLeftShore(Item item){
        if (getItemLocation(item) == Location.START) {
            return true;
        }
        else if (boatLocation == Location.START && getItemLocation(item) == Location.BOAT) {
            return true;
        }
        return false;
    }

    private boolean itemAtRightShore(Item item){
        if (getItemLocation(item) == Location.FINISH) {
            return true;
        }
        else if (boatLocation == Location.FINISH && getItemLocation(item) == Location.BOAT) {
            return true;
        }
        return false;
    }

    private int monstersAtLeftShore() {
        int count = 0;
        if (itemAtLeftShore(MONSTER1)) {
            count = count + 1;
        }
        if (itemAtLeftShore(MONSTER2)) {
            count = count + 1;
        }
        if (itemAtLeftShore(MONSTER3)) {
            count = count + 1;
        }
        return count;
    }

    private int monstersAtRightShore() {
        int count = 0;
        if (itemAtRightShore(MONSTER1)) {
            count = count + 1;
        }
        if (itemAtRightShore(MONSTER2)) {
            count = count + 1;
        }
        if (itemAtRightShore(MONSTER3)) {
            count = count + 1;
        }
        return count;
    }

    private int munchkinsAtLeftShore() {
        int count = 0;
        if (itemAtLeftShore(MUNCHKINS1)) {
            count = count + 1;
        }
        if (itemAtLeftShore(MUNCHKINS2)) {
            count = count + 1;
        }
        if (itemAtLeftShore(MUNCHKINS3)) {
            count = count + 1;
        }
        return count;
    }

    private int munchkinsAtRightShore() {
        int count = 0;
        if (itemAtRightShore(MUNCHKINS1)) {
            count = count + 1;
        }
        if (itemAtRightShore(MUNCHKINS2)) {
            count = count + 1;
        }
        if (itemAtRightShore(MUNCHKINS3)) {
            count = count + 1;
        }
        return count;
    }

}

