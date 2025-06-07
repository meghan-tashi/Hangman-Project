package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import WindowPackage.MenuPackage.GameModePanels.ModeSelectionSubPanel;

import java.awt.*;

/**
 * <u>Menu class</u><p>
 * This class has components which are used to set multiplayer settings.
 */
public class GameModeMenu extends AbstractMenuPanel {

    public GameModeMenu() {
        super();

        addTitleText("Select Game Mode");

        addSubPanel(new ModeSelectionSubPanel(), 100, 0, 0, 0, 0, 1, 0);

        CButton startButton = new CButton("Start");
        startButton.addActionListener(new MenuContainer.StateChangeButtonEvent(MenuState.GAME_START, null));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 2;
        constraints.insets.bottom = 200;

        add(startButton, constraints);
    }
}
