package CustomComponents;

import Utility.AudioClip;
import WindowPackage.Window;
import com.sun.java.accessibility.util.SwingEventMonitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <u>Custom Button class:</u><br/>
 * Used to create a button for custom graphical representation.
 */

public class CButton extends JButton {


    public static final float ICON_SCALE = 0.15f;
    private static final ImageIcon ROLLOVER_CHECKED = WindowPackage.Window.loadImage("Icons/check_rollover", ICON_SCALE);
    private static final ImageIcon ROLLOVER_UNCHECKED = WindowPackage.Window.loadImage("Icons/uncheck_rollover", ICON_SCALE);
    private static final ImageIcon CHECKED = WindowPackage.Window.loadImage("Icons/check", ICON_SCALE);
    private static final ImageIcon UNCHECKED = Window.loadImage("Icons/uncheck", ICON_SCALE);
    private static final ImageIcon RADIO_PRESSED = WindowPackage.Window.loadImage("Icons/radio_pressed", CButton.ICON_SCALE);
    private static final ImageIcon RADIO_ROLLOVER_UNCHECKED = WindowPackage.Window.loadImage("Icons/radio_uncheck_rollover", CButton.ICON_SCALE);
    private static final ImageIcon RADIO_CHECKED = WindowPackage.Window.loadImage("Icons/radio_check", CButton.ICON_SCALE);
    private static final ImageIcon RADIO_UNCHECKED = Window.loadImage("Icons/radio_uncheck", CButton.ICON_SCALE);

    private Color disabledColor = Color.gray;
    private Color normalColor = Color.white;
    private Color pressedColor = Color.lightGray;
    private Color rolloverColor = CLabel.YELLOW;

    private Font normalFont = UIManager.getFont("large");
    private Font disabledFont = UIManager.getFont("strikethrough");

    private boolean isRadio;
    private boolean isChecked;

    private final static CButtonAudioListener audioListener = new CButtonAudioListener();

    /**
     * Calls the parent constructor - {@link JButton#JButton(String)}.
     * Few settings like border and font are set
     * @param text The text to display.
     */
    public CButton(String text) {
        super(text);
        setFont(UIManager.getFont("large"));
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setFocusPainted(false);

        setCursor(Window.PRESSED_CURSOR);

        addMouseMotionListener(audioListener);
        addMouseListener(audioListener);
    }

    public void silentClicks() {
        removeMouseListener(audioListener);
        removeMouseMotionListener(audioListener);
    }

    /**
     * Calls the constructor - {@link CButton#CButton(String)}.
     * Used when the button is used as an icon display.
     * Manually set {@code foreground} color and {@code fontSize}.
     */
    public CButton(String text, Color foreground, String fontSize) {
        this(text);
        setForeground(foreground);
        setFont(UIManager.getFont(fontSize));
    }

    /**
     * Calls the constructor - {@link CButton#CButton(String, Color, String)}.
     * Used when the button is used as checkbox.
     * @param enabledAction Called when the button is checked
     * @param disabledAction Called when the button is unchecked
     * @param defaultValue Is button checked or unchecked, initially
     */
    public CButton(String text, Color foreground, String fontSize,
                   Runnable enabledAction, Runnable disabledAction, boolean defaultValue) {
        this(text, foreground, fontSize);

        addActionListener(new CheckButtonEvent(enabledAction, disabledAction));

        setChecked(defaultValue);

        setHorizontalTextPosition(SwingConstants.LEFT);
        setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    /**
     * Calls the constructor - {@link CButton#CButton(String, Runnable, Runnable, boolean)}.<br/>
     * Sets Default value for button actions, foreground and fontSize
     */
    public CButton(String text, boolean defaultValue) {
        this(text, null, null, defaultValue);
    }

    /**
     * Calls the constructor - {@link CButton#CButton(String, Color, String, Runnable, Runnable, boolean)}.
     * Sets Default value for foreground and fontSize
     */
    public CButton(String text, Runnable enabledAction, Runnable disabledAction, boolean defaultValue) {
        this(text, CLabel.LIGHT_GRAY, "large", enabledAction, disabledAction, defaultValue);
    }

    /**
     * Action Listener to check and uncheck button
     */
    public static class CheckButtonEvent implements ActionListener {

        private Runnable enabledAction;
        private Runnable disabledAction;

        public CheckButtonEvent(Runnable enabledAction, Runnable disabledAction) {
            this.enabledAction = enabledAction;
            this.disabledAction = disabledAction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CButton btnClicked = (CButton) e.getSource();

            if (btnClicked.isChecked) {

                btnClicked.setChecked(false);
                if(disabledAction != null)
                    disabledAction.run();

                return;
            }

            btnClicked.setChecked(true);
            if(enabledAction != null)
                enabledAction.run();
        }

        public Runnable getEnabledAction() {
            return enabledAction;
        }

        public void setEnabledAction(Runnable enabledAction) {
            this.enabledAction = enabledAction;
        }

        public Runnable getDisabledAction() {
            return disabledAction;
        }

        public void setDisabledAction(Runnable disabledAction) {
            this.disabledAction = disabledAction;
        }
    }

    public void setChecked(boolean value) {
        isChecked = value;
        if(isRadio) {
            setIcon(isChecked ? RADIO_CHECKED : RADIO_UNCHECKED);
            setRolloverIcon(isChecked ? RADIO_CHECKED : RADIO_ROLLOVER_UNCHECKED);
            setPressedIcon(RADIO_PRESSED);
            return;
        }
        setIcon(isChecked ? CHECKED : UNCHECKED);
        setRolloverIcon(isChecked ? ROLLOVER_CHECKED : ROLLOVER_UNCHECKED);
        setPressedIcon(getIcon());
    }

    /**
     * General {@code paintComponent} method with modification of graphics
     * for each button state.
     * @param g Graphics object (Not used in the overridden method)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getIcon() != null)
            return;

        UIManager.put("Button.disabledText", disabledColor);

        setForeground(model.isPressed() ? pressedColor : model.isRollover() ? rolloverColor : normalColor);

        Font font = isEnabled() ? normalFont : disabledFont;
        setFont(model.isRollover() || model.isPressed() ? UIManager.getFont("underlineLarge") : font);
    }

    private static class CButtonAudioListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getButton() != MouseEvent.BUTTON1)
                return;
            AudioClip.getAudioClip("Press").play();
        }

        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            AudioClip.getAudioClip("Over").play();
        }
    }

    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setRolloverColor(Color rolloverColor) {
        this.rolloverColor = rolloverColor;
    }

    public void setNormalFont(Font normalFont) {
        this.normalFont = normalFont;
    }

    public void setDisabledFont(Font disabledFont) {
        this.disabledFont = disabledFont;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }
}
