package WindowPackage.MenuPackage.SettingsPanels;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import WindowPackage.MenuPackage.AbstractSubPanel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <u>Settings class</u><p>
 * This panel will have components that will take player input and
 * Have the option to show hints is Enabled or not.
 */
public class HintsSubPanel extends AbstractSubPanel {

    private final CButton hintsEnableButton;

    /**
     * Calls Parent constructor and adds the necessary components.
     */
    public HintsSubPanel() {
        super();

        // Title
        CLabel title = new CLabel("Hints");
        addComponent(title, 0, 0, 3, 0, 0, 0, 0);

        // Hint Button

        hintsEnableButton = new CButton("Enable: ", true);

        addComponent(hintsEnableButton, 0, 1, 1, 30, 0, 0, 0);
    }

    /**
     * @return true if Hints are enabled else false.
     */
    public boolean isHintsEnabled() {
        return hintsEnableButton.isChecked();
    }
}
