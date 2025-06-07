package WindowPackage.MenuPackage.SettingsPanels;

import CustomComponents.CLabel;
import CustomComponents.CSlider;
import WindowPackage.MenuPackage.AbstractSubPanel;

/**
 * <u>Settings class</u><p>
 * This panel will have components that will take player input
 * and have the amount of lives/chances to guess Hangman word.
 */
public class LivesSubPanel extends AbstractSubPanel {

    /**
     * Max amount of Chances a player can have
     */
    public static final int TOTAL_LIVES = 11;

    /**
     * Calls Parent constructor and adds the necessary components.
     */
    public LivesSubPanel() {
        super();

        // Title
        CLabel title = new CLabel("Lives");

        // Label Minimum
        CLabel minLabel = new CLabel("Count:", CLabel.LIGHT_GRAY);

        // Slider Minimum
        CSlider countSlider = new CSlider(6, TOTAL_LIVES, 6);

        addComponent(countSlider, 1, 1, 1, 50, 10, 50, 0);
        addComponent(minLabel, 0, 1, 1, 50, 0, 50, 0);
        addComponent(title, 0, 0, 2, 0, 0, 0, 0);
    }

    /**
     * @return the initial hangman stage index.
     */
    public int getValue() {
        return TOTAL_LIVES - ((CSlider)getComponent(0)).getValue();
    }

}
