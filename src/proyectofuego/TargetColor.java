package proyectofuego;

import java.awt.Color;

/**
 *
 * @author Jaume
 */
public class TargetColor {
    
    private int position;
    private Color color;

    public TargetColor(int position, Color color) {
        this.position = position;
        this.color = color;
    }
    
    /**
     * Normal getter.
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Normal getter.
     * @return position
     */
    public int getPosition() {
        return position;
    }
}
