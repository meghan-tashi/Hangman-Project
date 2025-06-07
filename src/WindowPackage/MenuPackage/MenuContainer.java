package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import WindowPackage.GamePackage.GameContainer;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <u>Container class</u><p>
 * Used to store Main and Settings menu.
 * It has {@code MenuState}s which is can be changed in order to
 * display the required menu panels or game panel.
 */
public class MenuContainer extends JPanel {

    /**
     * Singleton of this class.
     */
    public static final MenuContainer instance = new MenuContainer();

    private MainMenu mainMenu;
    private SettingsMenu settingsMenu;
    private GameModeMenu gameModeMenu;

    /**
     * Back button to switch back to the {@code MainMenu} from any other {@code MenuState}.
     */
    private CButton back;

    /**
     * Sets layout, size and opacity.
     * Initializes {@code MaineMenu}, {@code SettingsMenu} and {@code back} button.
     */
    public void init() {
        setLayout(null);
        setSize(Window.WIDTH, Window.HEIGHT);
        setOpaque(false);

        mainMenu = new MainMenu();
        settingsMenu = new SettingsMenu();
        gameModeMenu = new GameModeMenu();

        back = new CButton("Main Menu", Color.WHITE, "small");

        back.setIcon(Window.loadImage("Icons/backBtn", Window.IMAGE_SCALE));
        back.setRolloverIcon(Window.loadImage("Icons/backBtn_rollover", Window.IMAGE_SCALE));
        back.setPressedIcon(Window.loadImage("Icons/backBtn_pressed", Window.IMAGE_SCALE));
        back.setSize(back.getPreferredSize());
        back.addActionListener(new StateChangeButtonEvent(MenuState.MAIN_MENU, () -> back.setVisible(false)));

        add(mainMenu);
        add(settingsMenu);
        add(gameModeMenu);
        add(back);

        Window.PANE.add(this);

        changeState(MenuState.MAIN_MENU);
    }

    /**
     * Changes the current state to specified {@code MenuState}
     * and sets the visibilities of different panels according to the state.
     */
    public void changeState(MenuState state) {

        boolean isStateGameStart = state.equals(MenuState.GAME_START);
        boolean isStateGameModeMenu = state.equals(MenuState.GAME_MODE_MENU);
        boolean isStateMainMenu = state.equals(MenuState.MAIN_MENU);
        boolean isStateSettingMenu = state.equals(MenuState.SETTINGS_MENU);

        // Pane Visibility
        GameContainer.instance.setVisible(isStateGameStart);
        mainMenu.setVisible(isStateMainMenu);
        settingsMenu.setVisible(isStateSettingMenu);
        gameModeMenu.setVisible(isStateGameModeMenu);

        back.setVisible(!isStateMainMenu);

        // Other Tasks
        if(isStateGameStart) {
            GameContainer.instance.startWordGuessing(settingsMenu.getWordSettings());
            GameContainer.instance.setInitialLives(settingsMenu.getLives());
        }
    }

    /**
     * Action Listener for the buttons that will change the {@code MenuState} to the specified state.
     * @param postStateChangeAction a Runnable interface to run an action after the state has changed.
     */
    public record StateChangeButtonEvent(MenuState state, Runnable postStateChangeAction) implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuContainer.instance.changeState(state);

            if(postStateChangeAction == null) return;
            postStateChangeAction.run();
        }
    }

    /**
     * Action Listener to exit the application.
     */
    public static class ExitWindowEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}