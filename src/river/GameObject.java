package river;

import java.awt.Color;

public class GameObject {

    //    private final String name;
    private final String label;
    private Location location;
    private final Color color;

    public GameObject(String label_arg, Location location, Color color_arg) {
        label = label_arg;
        this.location = location;
        color = color_arg;
    }

    public String getLabel() {
        return label;
    }

    public Location getLocation() {return this.location;}

    public void setLocation(Location loc) {this.location = loc;}

    public Color getColor() {
        return color;
    }


}
