package WindowPackage.GamePackage;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import Utility.WordSettings;
import Utility.WordGenerator;
import WindowPackage.MenuPackage.MenuContainer;
import WindowPackage.MenuPackage.MenuState;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * <u>Container Class</u><p>
 * A JPanel container which has - Hangman, Lose/Win text, Guess Word, Alphabet Container.
 * This class handles logic of guessing the word and uses alphabet container for player input.
 */
public class GameContainer extends JPanel {

    /**
     * The Singleton for this class.
     */
    public static final GameContainer instance = new GameContainer();

    /**
     * States of a game when it ends. (game over)
     */
    private static final int WORD = 0;
    private static final int HINT = 1;
    private static final int WIN = 1;
    private static final int LOSE = -1;
    private static final int NONE = 0;
    private static final int SINGLE = 0;
    private static final int MULTI = 1;

    /**
     * The word and hint that is to be guessed.<p>
     * Example - [<b>"HELLO", "Hint: Used as a greeting"</b>]
     */
    private String[] wordAndHint;

    /**
     * Word that has to be guessed.
     * This will be displayed differently from a normal label.
     * It has gaps in between for visibility and the alphabets that have not guessed
     * will be displayed as underscores ( _ ).<p>
     * Example word <b>HELLO</b> will be displayed as - <b>_ _ L L _</b>.
     *
     */
    private static final CLabel guessWord = new CLabel();
    private static final CLabel wordHint = new CLabel();

    /**
     * Contains the displaying letters of the {@code guessWord}.<p>
     * Example - {@code {"_", "_", "L", "L", "_"}}
     */
    private String[] fillers;

    private int gameMode;

    /**
     * Text that displays the WIN or LOSE state of player.
     */
    private final CLabel endLabel = new CLabel();
    private final CButton retryButton = new CButton("Next Word");

    private AlphabetContainer alphabetContainer;
    private HangmanHandler hangmanHandler;

    /**
     * Sets Layout null by calling parent constructor - {@link JPanel#JPanel(LayoutManager)}.
     * Then Sets Opacity and Size of this panel.
     */
    public GameContainer() {
        super(null);
        setOpaque(false);
        setSize(Window.WIDTH, Window.HEIGHT);
    }

    /**
     * Initializes the variables of this class and adds to this panel.
     * Then adds this panel to the {@code Window.Pane}.
     * And sets the visibility to false.
     */
    public void init() {
        alphabetContainer = new AlphabetContainer();
        hangmanHandler = new HangmanHandler(this);

        retryButton.addActionListener(new MenuContainer.StateChangeButtonEvent(MenuState.GAME_START, null));
        retryButton.setSize(retryButton.getPreferredSize());
        retryButton.setLocation(alphabetContainer.getX() + (alphabetContainer.getWidth() - retryButton.getWidth())/2,
                Window.HEIGHT - retryButton.getHeight()*2);

        add(endLabel);
        add(retryButton);
        add(wordHint);
        add(guessWord);
        add(alphabetContainer);

        Window.PANE.add(this);

        setVisible(false);
    }

    /**
     * Finds a word through {@code WordGenerator} class and {@code WordSettings} class
     * and assigns it to {@code word} variable. Then {@code guessWord} label's text is updated.
     */
    public void startWordGuessing(WordSettings settings) {
        if(!isVisible()) return;
        retryButton.setVisible(false);
        wordAndHint = WordGenerator.getRandom(settings);
        fillers = null;
        updateText();
        System.out.println(Arrays.toString(wordAndHint));
    }

    /**
     * If {@code fillers} is null, it is initialized with same length of {@code word} variable and filled with underscores ("_").
     * Sets the {@code guessWord} label's text using {@code fillers} through the overridden method {@link CLabel#setText(String)}.
     * And updates the position of {@code guessWord} to center it.
     */
    private void updateText() {
        if(fillers == null) {
            // This will create an array which contains alphabets which
            // have been guessed and underscore for which have not been guessed.
            fillers = new String[wordAndHint[WORD].length()];
            Arrays.fill(fillers, "_");
        }

        // This will set the text of label adding space between the letters.
        guessWord.setText(String.join(" ", fillers));

        // Set hint for the word
        wordHint.setText(wordAndHint[HINT]);
        wordHint.setLocation(alphabetContainer.getX() + (alphabetContainer.getWidth() - wordHint.getWidth())/2 - 20, 160);

        // To center the position relative to Alphabet Container
        guessWord.setLocation(
                alphabetContainer.getX() + (alphabetContainer.getWidth() - guessWord.getWidth())/2,
                alphabetContainer.getY() - 100);
    }

    /**
     * If the {@code word} contains {@code alphabet} then {@code fillers} and label text is updated
     */
    public void guessAlphabet(String alphabet) {

        // index of the alphabet in the word variable
        int index = wordAndHint[WORD].indexOf(alphabet);

        // If the alphabet is not present the index will be -1 and
        // so the hangman will move to next stage and method is exited.
        if(index == -1) {

            // This moves the hangman to next stage and updates its face
            // if the player still has more chances.
            boolean lost = looseLive();
            if(!lost)
                hangmanHandler.setFace(HangmanHandler.HangmanFace.FROWN_LOOK);
            return;
        }

        // Loops and replaces underscores at the positions
        // of the same alphabet in word variable
        while (index != -1) {
            fillers[index] = alphabet;
            index = wordAndHint[WORD].indexOf(alphabet, index + 1);
        }

        // Updates the displayed text (guessWord label)
        updateText();

        // Checks if the player has won.
        // If not then Hangman's face is updated else
        // gameOver is called
        if(!guessWord.getText().contains("_")) {
            gameOver(WIN);
        } else
            hangmanHandler.setFace(HangmanHandler.HangmanFace.SMILE_LOOK);
    }

    /**
     * @return true if {@code guessWord} has an empty text
     */
    public boolean isEmpty() {
        return guessWord.getText().isEmpty();
    }

    /**
     * The Initial Hangman stage is set
     * @param lives stage index
     */
    public void setInitialLives(int lives) {
        if(!isVisible()) return;
        hangmanHandler.setStage(lives);
    }

    /**
     * Moves the hangman to next stage.
     * If hangman is already on last stage then the game is ended with {@code LOSE} state.
     * @return true if the game ends
     */
    private boolean looseLive() {

        int currentHangStage = hangmanHandler.nextStage();

        if(currentHangStage < 0) {
            gameOver(LOSE);
            return true;
        }

        return false;
    }

    /**
     * Reveals the word in {@code guessWord} label
     */
    private void showWord() {
        System.arraycopy(wordAndHint[WORD].split(""), 0, fillers, 0, wordAndHint[WORD].length());
        updateText();
    }

    /**
     * Overridden method that enables and disables {@code GameContainer}'s components
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if(!visible) return;
        alphabetContainer.reset();
        gameOver(NONE);
    }

    /**
     * This is called whenever the game is ended.
     * There are 3 ways in which game ends - {@code WIN}, {@code LOSE} or just exit ({@code NONE}).
     * Depending on these - {@code guessWord} label, {@code endText} label and
     * hangman's face is updated.
     */
    private void gameOver(int gameOverState) {

        String endText = "";

        switch (gameOverState) {
            case WIN -> {
                hangmanHandler.setFace(HangmanHandler.HangmanFace.LAUGH);
                guessWord.setForeground(CLabel.GREEN);
                endText = "GUESSED IT RIGHT!";
            }
            case LOSE -> {
                hangmanHandler.setFace(HangmanHandler.HangmanFace.DIE);
                guessWord.setForeground(CLabel.RED);
                showWord();
                endText = "BETTER LUCK NEXT TIME!";
            }
            case NONE -> {
                hangmanHandler.setFace(HangmanHandler.HangmanFace.NULL);
                guessWord.setForeground(Color.white);
            }
        }

        retryButton.setVisible(true);
        endLabel.setText(endText);
        endLabel.setLocation(alphabetContainer.getX() + (alphabetContainer.getWidth() - endLabel.getWidth())/2, 100);
        alphabetContainer.setEnabled(gameOverState == NONE);
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}
