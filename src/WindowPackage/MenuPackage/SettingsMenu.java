package WindowPackage.MenuPackage;

import CustomComponents.CLabel;
import Utility.WordSettings;
import WindowPackage.MenuPackage.SettingsPanels.*;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;

/**
 * <u>Menu class</u><p>
 * This class has components which are used to set the word settings.
 */
public class SettingsMenu extends AbstractMenuPanel {

    /**
     * {@code WordLengthSettings} panel containing the components to set
     * settings for word's length.
     */
    private final WordLengthSubPanel wordLengthSettings;

    /**
     * {@code LivesSettings} panel containing the components to set
     * settings for player's chances to guess the word.
     */
    private final LivesSubPanel livesSettings;
    private final VowelContainSubPanel vowelContainSettings;
    private final HintsSubPanel hintsSettings;



    /**
     * Calls Parent constructor - {@link JPanel#JPanel(LayoutManager)}
     * to set layout manager as GridBagLayout and sets size and opacity.
     * Then adds necessary components and {@code AbstractSettings} classes.
     */
    public SettingsMenu() {
        super();

        addBkgImage("Backgrounds/settingsMenuSeparator", Window.WIDTH, Window.HEIGHT);
        addTitleText("Settings");

        // Setting Panels
        wordLengthSettings = new WordLengthSubPanel();
        livesSettings = new LivesSubPanel();
        vowelContainSettings = new VowelContainSubPanel();
        hintsSettings = new HintsSubPanel();

        addSubPanel(wordLengthSettings,   125,    0,   0, 0, 0, 0, 1);
        addSubPanel(livesSettings,        125,    0, 100, 0, 1, 0, 1);
        addSubPanel(vowelContainSettings,   0,   50,   0, 0, 0, 1, 1);
        addSubPanel(hintsSettings,          0,  130, 100, 0, 1, 1, 1);
    }

    /**
     * @return {@code WordSettings} with values got from {@code AbstractSettings} panels.
     */
    public WordSettings getWordSettings() {
        return new WordSettings(
                wordLengthSettings.getValue(0),
                wordLengthSettings.getValue(1),
                vowelContainSettings.getValue(),
                hintsSettings.isHintsEnabled());
    }

    /**
     * @return The initial hangman stage index got from {@code LivesSettings} panel.
     */
    public int getLives() {
        return livesSettings.getValue();
    }

}
