package river;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical interface for the River application
 *
 * @author Gregory Kulczycki
 */
public class RiverGUI extends JPanel implements MouseListener {
    // ==========================================================
    // Fields (hotspots)
    // ==========================================================

    private final Rectangle farmerButtonRect = new Rectangle(235, 118, 154, 30);
    private final Rectangle monsterButtonRect = new Rectangle(405, 118, 156, 30);

    // ==========================================================
    // Private Fields
    // ==========================================================

    private final int baseY = 275;
    private final int boatY = 335;

    private final int startBaseX = 20;
    private final int startBoatX = 140;
    private final int finishBaseX = 670;
    private final int finishBoatX = 550;

    private final int itemWidth = 50;
    private final int itemHeight = 50;
    private final int boatHeight = 50;
    private final int boatWidth = 110;

    private Map<Item, Rectangle> itemRectMap;
    private Map<Item, Integer> itemXOffsetMap;
    private Map<Item, Integer> itemYOffsetMap;

    private GameEngine engine; // Model
    private Rectangle boatRect;
    private boolean restart = false;
    Graphics g;


    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {

        engine = new FarmerGameEngine();
        addMouseListener(this);

        this.itemRectMap = new HashMap<>();
        this.itemXOffsetMap = new HashMap<>();
        this.itemYOffsetMap = new HashMap<>();

        this.itemRectMap.put(Item.ITEM_0, new Rectangle(startBaseX, baseY, itemWidth, itemHeight));
        this.itemRectMap.put(Item.ITEM_1, new Rectangle(startBaseX, baseY, itemWidth, itemHeight));
        this.itemRectMap.put(Item.ITEM_2, new Rectangle(startBaseX, baseY, itemWidth, itemHeight));
        this.itemRectMap.put(Item.ITEM_3, new Rectangle(startBaseX, baseY, itemWidth, itemHeight));
        this.itemRectMap.put(Item.ITEM_4, new Rectangle(startBaseX, baseY, itemWidth, itemHeight));
        this.itemRectMap.put(Item.ITEM_5, new Rectangle(startBaseX, baseY, itemWidth, itemHeight));

        this.itemXOffsetMap.put(Item.ITEM_0, 0);
        this.itemXOffsetMap.put(Item.ITEM_1, 60);
        this.itemXOffsetMap.put(Item.ITEM_2, 0);
        this.itemXOffsetMap.put(Item.ITEM_3, 60);
        this.itemXOffsetMap.put(Item.ITEM_4, 0);
        this.itemXOffsetMap.put(Item.ITEM_5, 60);

        this.itemYOffsetMap.put(Item.ITEM_0, 0);
        this.itemYOffsetMap.put(Item.ITEM_1, 0);
        this.itemYOffsetMap.put(Item.ITEM_2, -60);
        this.itemYOffsetMap.put(Item.ITEM_3, -60);
        this.itemYOffsetMap.put(Item.ITEM_4, -120);
        this.itemYOffsetMap.put(Item.ITEM_5, -120);

        this.boatRect = new Rectangle(startBoatX, boatY, boatWidth, boatHeight);
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {

        this.g = g;

        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) break;
            refreshItemRectangle(item);
        }
        refreshBoatRectangle();

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) break;
            paintItem(item);
        }

        paintBoat();

        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        paintMessage(message, g);
        if (restart) {
            paintGameButtons("Farmer Game", farmerButtonRect);
            paintGameButtons("Monster Game", monsterButtonRect);
        }

    }

    private void refreshItemRectangle(Item item) {
        if (engine.getItemLocation(item) == Location.START) {
            this.itemRectMap.put(item, new Rectangle(startBaseX + this.itemXOffsetMap.get(item),
                    baseY + this.itemYOffsetMap.get(item), itemWidth, itemHeight));
        }
        if (engine.getItemLocation(item) == Location.FINISH) {
            this.itemRectMap.put(item, new Rectangle(finishBaseX + this.itemXOffsetMap.get(item),
                    baseY + this.itemYOffsetMap.get(item), itemWidth, itemHeight));
        }
        if (engine.getItemLocation(item) == Location.BOAT) {
            if (engine.getBoatLocation() == Location.START) {
                this.itemRectMap.put(item, new Rectangle(startBaseX + this.itemXOffsetMap.get(item) + boatWidth + 10,
                        baseY + this.itemYOffsetMap.get(item), itemWidth, itemHeight));
            }
            if (engine.getBoatLocation() == Location.FINISH) {
                this.itemRectMap.put(item, new Rectangle(finishBaseX + this.itemXOffsetMap.get(item) - 120,
                        baseY + this.itemYOffsetMap.get(item), itemWidth, itemHeight));
            }
        }
    }

    private void refreshBoatRectangle() {
        if (engine.getBoatLocation() == Location.START) {
            this.boatRect = new Rectangle(startBoatX, boatY, boatWidth, boatHeight);
        } else {
            this.boatRect = new Rectangle(finishBoatX, boatY, boatWidth, boatHeight);
        }
    }

    private void paintItem(Item item) {
        paintRectangle(engine.getItemColor(item), engine.getItemLabel(item), this.itemRectMap.get(item));
    }

    private void paintBoat() {
        g.setColor(Color.ORANGE);
        g.fillRect(this.boatRect.x, this.boatRect.y, this.boatRect.width, this.boatRect.height);
    }

    private void paintRectangle(Color color, String label, Rectangle rect) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);
        int fontSize = (rect.height >= 40) ? 32 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = rect.x + rect.width / 2 - fm.stringWidth(label) / 2;
        int strYCoord = rect.y + rect.height / 2 + fontSize / 2 - 4;
        g.drawString(label, strXCoord, strYCoord);
    }

    private void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    private void paintGameButtons(String str, Rectangle rect) {
        g.setColor(Color.BLACK);
        paintBorder(rect, 3, g);
        paintRectangle(Color.PINK, str, rect);
    }

    private void paintBorder(Rectangle r, int thickness, Graphics g) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {
        if (restart) {
            if (!(this.farmerButtonRect.contains(e.getPoint()) || this.monsterButtonRect.contains(e.getPoint()))) {
                return;
            }
            if (this.farmerButtonRect.contains(e.getPoint())) {
                engine = new FarmerGameEngine();
            } else if (this.monsterButtonRect.contains(e.getPoint())) {
                engine = new MonsterGameEngine();
            }
            restart = false;
            repaint();
            return;
        }

        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) break;
            else {
                if (this.itemRectMap.get(item).contains(e.getPoint())) {
                    itemClickAction(item);
                }
            }
        }

        if (this.boatRect.contains(e.getPoint())) {
            engine.rowBoat();
        }
        repaint();
    }

    public void itemClickAction(Item item) {
        if (engine.getItemLocation(item) == Location.START) {
            engine.loadBoat(item);
        } else if (engine.getItemLocation(item) == Location.BOAT) {
            engine.unloadBoat(item);
        } else if (engine.getItemLocation(item) == Location.FINISH) {
            engine.loadBoat(item);
        }
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
