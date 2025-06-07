package WindowPackage.MenuPackage.GameModePanels;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import CustomComponents.CRadioButtonContainer;
import WindowPackage.MenuPackage.AbstractSubPanel;

import javax.swing.*;
import java.awt.*;

public class ModeSelectionSubPanel extends AbstractSubPanel {

    private final CButton singlePlayerBtn;
    private final CButton twoPlayerBtn;

    public ModeSelectionSubPanel() {
        super();

        singlePlayerBtn = new CButton("Single Player: ", true);
        twoPlayerBtn = new CButton("Two Players: ", false);

        new CRadioButtonContainer(singlePlayerBtn, twoPlayerBtn);

        GridBagConstraints constraints = new GridBagConstraints();

        /*CLabel wordLabel = new CLabel("Player1 Enter the Word:", CLabel.LIGHT_GRAY);
        JPasswordField word = new JPasswordField();
        word.setSize(30, 20);
        word.setEchoChar('_');*/

        constraints.gridwidth = 2;
        constraints.insets.top = 20;
        add(singlePlayerBtn, constraints);

        constraints.gridy = 1;
        add(twoPlayerBtn, constraints);

        /*constraints.gridwidth = 1;
        constraints.gridy = 2;
        add(wordLabel, constraints);

        constraints.gridx = 1;
        add(word, constraints);*/
    }



}
