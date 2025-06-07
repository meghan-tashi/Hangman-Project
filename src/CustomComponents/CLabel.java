package CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * <u>Custom Label class:</u><p>
 * Basically used to set the size as per the text/icon to be displayed
 */

public class CLabel extends JLabel {

    public static final Color YELLOW = new Color(241, 206, 1);
    public static final Color RED = new Color(216, 66, 66);
    public static final Color GREEN = new Color(81, 199, 90);
    public static final Color LIGHT_GRAY = new Color(167, 167, 167);

    public CLabel() {
        this("");
    }

    /**
     * Calls self constructor - {@link CLabel#CLabel(String)}<p>
     *     And sets the icon.
     * @param icon Icon to be displayed.
     */
    public CLabel(Icon icon) {
        this("");
        setIcon(icon);
    }

    /**
     * Calls self constructor - {@link CLabel#CLabel(String, Color)}
     * with default color as white.
     */
    public CLabel(String text) {
        this(text, Color.white);
    }

    /**
     * Calls the Parent Constructor - {@link JLabel#JLabel(String)}<p>
     *     And sets the custom foreground.
     * @param text Text to be displayed.
     * @param color Color of foreground.
     */
    public CLabel(String text, Color color) {
        super(text);
        setForeground(color);
    }

    /**
     * Sets the Width and Height of this component as per the Size(pixels) of the text.
     */
    @Override
    public void setText(String text) {
        super.setText(text);

        Font font = getFont();

        if(font == null) {
            setFont(UIManager.getFont("large"));
            font = getFont();
        }

        // FontMetrics class is used to get the size of current text.
        FontMetrics metrics = getFontMetrics(font);
        setSize(metrics.stringWidth(text), metrics.getHeight());
    }

    /**
     * Sets the icon and resizes as per the size of it.
     */
    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        setSize(getPreferredSize());
    }
}