package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * <u>Menu Class</u><p>
 * This class has the Initial Menu components.
 */
public class MainMenu extends AbstractMenuPanel {

    /**
     * Calls parent constructor - {@link JPanel#JPanel(LayoutManager)}
     * to set the layout to {@code GridBagLayout}.
     * Sets the Opacity to false and adds necessary components.
     */
    public MainMenu() {
        super();

        addBkgImage("Backgrounds/title");
        menuBackground.setLocation((Window.WIDTH - menuBackground.getWidth())/2, 75);

        addButton("Play", 1, 200, new MenuContainer.StateChangeButtonEvent(MenuState.GAME_MODE_MENU, null));
        addButton("Settings", 2, 10, new MenuContainer.StateChangeButtonEvent(MenuState.SETTINGS_MENU, null));
        addButton("Quit", 3, 10, new MenuContainer.ExitWindowEvent());
    }

    /**
     * Adds a button with specified constraints and action listener.
     * @param text The text to be displayed.
     * @param gridY The row of the button on the panel.
     * @param top Top padding of the button.
     * @param actionListener Action Listener that is to be added to the button.
     */
    private void addButton(String text, int gridY, int top, ActionListener actionListener) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets.set(top, 0, 10, 0);
        constraints.gridy = gridY;

        CButton button = new CButton(text);
        button.addActionListener(actionListener);
        add(button, constraints);
    }

}
