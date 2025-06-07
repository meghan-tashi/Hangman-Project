package WindowPackage.GamePackage;

import CustomComponents.CLabel;
import WindowPackage.MenuPackage.SettingsPanels.LivesSubPanel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

/**
 * <u>Handler Class</u><p>
 * This class handles the Hangman Stages and Hangman's face
 */
public class HangmanHandler {

    /**
     * Panel which displays the images of all hangman stages.
     */
    private final JPanel stageContainer;

    /**
     * Panel which displays the images of all hangman faces.
     */
    private final JPanel faceContainer;

    /**
     * Enum variable for Current Face displayed by {@code faceContainer}.
     */
    private HangmanFace currentFace;

    /**
     * The index of Current Stage displayed by {@code stageContainer}.
     */
    public int currentStageIndex;

    /**
     * Initializes the panels and loads all the images for stages and faces.
     * It also schedules a timer for changing face of the hangman.
     */
    public HangmanHandler(JPanel panel) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new FACE_CHANGE(), 0, 3000);

        stageContainer = addImageContainer("Hangman/stages/stage", 12);
        faceContainer = addImageContainer("Hangman/faces/face", 9);

        panel.add(stageContainer);
        panel.add(faceContainer);
    }

    /**
     * Simply adds all images as labels to a panel with {@code CardLayout}.
     * Its opacity is set to false.
     * @return the panel created with all the images.
     */
    private JPanel addImageContainer(String images, int count) {
        JPanel container = new JPanel(new CardLayout());
        container.setOpaque(false);

        for(int i = 0; i < count; i++) {
            CLabel imageLabel = new CLabel(Window.loadImage(images + i, Window.IMAGE_SCALE));
            container.add(imageLabel, Integer.toString(i));
        }

        return container;
    }

    /**
     * Assigns {@code currentFace} a new value.
     * And shows the card of {@code faceContainer} using the value
     * of {@code currentFace}.
     * @param face The enum containing the index value of {@code faceContainer}'s card
     */
    public void setFace(HangmanFace face) {
        if(currentStageIndex < 6) return;

        currentFace = face;

        int currentFaceIndex = face.getValue();

        showImage(faceContainer, currentFaceIndex);
    }

    /**
     * Moves Hangman to the next stage by incrementing {@code currentStageIndex}.
     * @return -1 if hangman is at last stage otherwise returns {@code currentStageIndex}.
     */
    public int nextStage() {
        setStage(++currentStageIndex);
        return currentStageIndex >= LivesSubPanel.TOTAL_LIVES ? -1 : currentStageIndex;
    }

    /**
     * Assigns {@code currentStageIndex} a new index.
     * And shows the card of {@code stageContainer} using the index value
     * of {@code currentStageIndex}.
     * @param stageIndex The index value of {@code stageContainer}'s card
     */
    public void setStage(int stageIndex) {
        currentStageIndex = stageIndex;

        // Showing the face of hangman only once at stage 6,
        // the first time when the circle is drawn
        if(stageIndex == 6)
            setFace(HangmanFace.FROWN);

        showImage(stageContainer, currentStageIndex);
    }

    /**
     * Displays the card at specified {@code index} of specified {@code container}.
     * And then resize the {@code container} to fit the image.
     * @param container The panel containing the image labels.
     * @param index The index at which the image label is stored.
     */
    private void showImage(JPanel container, int index) {
        Component label = container.getComponent(index);
        container.setSize(label.getPreferredSize());

        CardLayout layout = ((CardLayout)container.getLayout());
        layout.show(container, Integer.toString(index));
    }

    /**
     * <u>Timer Task class</u><p>
     * This is used to change the face of hangman periodically
     * to make it look alive.
     * It uses {@link Thread#sleep(long)} to add delay and {@link Random#nextInt(int)}
     * to randomize the delay.
     */
    private class FACE_CHANGE extends TimerTask {

        /**
         * This tells the amount of times the face has changed to Idle position.
         */
        private int longIdle = 0;

        /**
         * Changes the face of hangman depending on the {@code currentFace}
         * and {@code longIdle}.
         */
        @Override
        public void run() {
            if(currentFace == null || currentFace.equals(HangmanFace.DIE) || currentFace.equals(HangmanFace.LAUGH)) {
                return;
            }

            switch (currentFace) {
                case NEUTRAL -> setFace(HangmanFace.NEUTRAL_LOOK);
                case NEUTRAL_LOOK -> setFace(HangmanFace.NEUTRAL);

                case SMILE -> {
                    longIdle++;
                    setFace(HangmanFace.SMILE_LOOK);
                }

                case FROWN -> {
                    longIdle++;
                    setFace(HangmanFace.FROWN_LOOK);
                }

                case SMILE_LOOK -> setFace(HangmanFace.SMILE);
                case FROWN_LOOK -> setFace(HangmanFace.FROWN);
            }

            if(longIdle > 2) {
                setFace(HangmanFace.NEUTRAL);
                longIdle = 0;
            }

            try {
                Thread.sleep(new Random().nextInt(6)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This only helps to use the index values with names.
     * It makes it easy to set {@code faceContainer}'s current card
     * using the index values set in these enums.
     * The names make it readable to as which index value is to be used,
     * rather than using the index value directly.
     */
    public enum HangmanFace {
        NULL(0),
        FROWN(1), FROWN_LOOK(2),
        SMILE(3), SMILE_LOOK(4),
        NEUTRAL(5), NEUTRAL_LOOK(6),
        DIE(7), LAUGH(8);

        private final int value;

        HangmanFace(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
