package WindowPackage.GamePackage;

import CustomComponents.CButton;
import Utility.AudioClip;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * <u>Container class:</u><p>
 * A JPanel container which has sub panels which contain the Alphabets A-Z in form of buttons.
 * These buttons are used to guess the alphabets in the word given.
 * Sub Panels are used only to position the buttons.
 */
public class AlphabetContainer extends JPanel implements KeyListener {

    /**
     * The constraints of this class.
     */
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 300;
    private static final int POS_OFFSET = 50;

    /**
     * Layout used for the Sub Panels.
     */
    private final FlowLayout rowLayout = new FlowLayout(FlowLayout.CENTER, 10, 10);

    /**
     * List used to store all the buttons for easy access.
     */
    private final ArrayList<CButton> alphabetButtons;

    /**
     * Action Listener for all the buttons.
     */
    private final AlphabetButtonEvent alphabetButtonEvent;

    /**
     * Calls parent constructor to set the Layout as GridLayout.
     * Sets the bounds and opacity of this panel.
     * And then adds the alphabets (buttons).
     */
    public AlphabetContainer() {
        super(new GridLayout());

        int x = Window.WIDTH - (PANEL_WIDTH + POS_OFFSET);
        int y = Window.HEIGHT - (PANEL_HEIGHT + POS_OFFSET);
        setBounds(x, y, PANEL_WIDTH, PANEL_HEIGHT);
        setOpaque(false);
        addKeyListener(this);

        alphabetButtons = new ArrayList<>();
        alphabetButtonEvent = new AlphabetButtonEvent();

        addRow("Q","W","E","R","T","Y","U","I","O","P");
        addRow("A","S","D","F","G","H","J","K","L");
        addRow("Z","X","C","V","B","N","M");
    }

    /**
     * This allows to add many buttons to one sub panel as a row.
     * An Action Listener is set for buttons - {@code AlphabetButtonEvent}.
     * All the buttons are added to {@code alphabetButtons} list for access purpose.
     */
    private void addRow(String... alphabets) {
        GridLayout layout = (GridLayout) getLayout();
        layout.setRows(layout.getRows()+1);

        JPanel subPanel = new JPanel(rowLayout);
        subPanel.setOpaque(false);

        for(String alphabet : alphabets) {

            CButton button = new CButton(alphabet);
            button.addActionListener(alphabetButtonEvent);
            subPanel.add(button);
            alphabetButtons.add(button);
        }

        add(subPanel);
    }

    /**
     * Basically sets all the buttons as enabled.
     * And requests focus for the key listener.
     */
    public void reset() {
        for(CButton button : alphabetButtons)
            button.setEnabled(true);

        requestFocus();
    }

    /**
     * Allows user to use keyboard instead of buttons to guess Alphabets.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        String alphabet = String.valueOf(e.getKeyChar()).toUpperCase();

        for (CButton button : alphabetButtons) {
            if (button.getText().equals(alphabet)) {
                guessAlphabetEvent(button);
                break;
            }
        }

        AudioClip.getAudioClip("Press").play();
    }

    /**
     * Called when a button is pressed or a character key is released.
     * It disables the button clicked or the released character key.
     */
    private void guessAlphabetEvent(CButton button) {
        if(GameContainer.instance.isEmpty() || !isEnabled() || !button.isEnabled()) return;

        GameContainer.instance.guessAlphabet(button.getText());
        button.setEnabled(false);

        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Action Listener class used for alphabet buttons.
     */
    private class AlphabetButtonEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            guessAlphabetEvent((CButton) e.getSource());
        }
    }

}
