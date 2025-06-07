package WindowPackage.MenuPackage.SettingsPanels;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import WindowPackage.MenuPackage.AbstractSubPanel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <u>Settings class</u><p>
 * This panel will have components that will take player input and
 * Have any vowel or not, present in the next Hangman word.
 */
public class VowelContainSubPanel extends AbstractSubPanel {

    /**
     * Contains all the vowel buttons - A,E,I,O,U
     */
    private final CButton[] vowels;

    /**
     * Calls Parent constructor and adds the necessary components.
     */
    public VowelContainSubPanel() {
        super();

        // Vowel Buttons
        CButton skipBtn = new CButton("Skip");
        skipBtn.setEnabled(false);

        vowels = new CButton[] {
                new CButton("A"), new CButton("E"), new CButton("I"),
                new CButton("O"), new CButton("U"), skipBtn
        };

        int gridX = 0;
        int gridW = vowels.length+1;

        VowelButtonEvent vowelButtonEvent = new VowelButtonEvent();

        for (CButton vowel : vowels) {
            System.out.println(vowel.getCursor());
            vowel.addActionListener(vowelButtonEvent);
            addComponent(vowel, gridX++, 1, 1, 30, 35, 0, 0);
        }

        // Skip button
        skipBtn.addActionListener(vowelButtonEvent);
        addComponent(skipBtn, 0, 2, gridW, 0, 0, 0, 0);

        // Title
        CLabel title = new CLabel("Word should contain Vowel");
        addComponent(title, 0, 0, gridW, 0, 0, 0, 0);

        // Hint
        CLabel hint = new CLabel("(pick any vowel or skip)");
        hint.setFont(UIManager.getFont("small"));
        addComponent(hint, 0, 3, gridW, 0, 0, 0, 0);
    }

    /**
     * @return any selected vowel. If skip is selected then empty string is returned.
     */
    public String getValue() {
        for(int i = 0; i < vowels.length-1; i++) {
            if(!getComponents()[i].isEnabled())
                return ((CButton)getComponents()[i]).getText();
        }
        return "";
    }

    /**
     * Action Listener for the vowels and skip button.
     */
    private static class VowelButtonEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Component clickedVowel = (Component) e.getSource();
            Component[] vowels = clickedVowel.getParent().getComponents();

            for(Component vowel : vowels) {
                vowel.setEnabled(!vowel.equals(clickedVowel));
            }
        }

    }
}
