package WindowPackage.MenuPackage.SettingsPanels;

import CustomComponents.CLabel;
import CustomComponents.CSlider;
import Utility.WordGenerator;
import WindowPackage.MenuPackage.AbstractSubPanel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <u>Settings class</u><p>
 * This panel will have components that will take player input
 * and have the max length and min length of the next hangman word.
 */
public class WordLengthSubPanel extends AbstractSubPanel {

    /**
     * Calls Parent constructor and adds the necessary components.
     */
    public WordLengthSubPanel() {
        super();

        // Title
        CLabel title = new CLabel("Word Length");

        // Label Minimum
        CLabel minLabel = new CLabel("minimum:", CLabel.LIGHT_GRAY);

        // Slider Minimum
        CSlider minSlider = new CSlider(
                WordGenerator.MIN_WORD_LENGTH,
                WordGenerator.MAX_WORD_LENGTH,
                WordGenerator.MIN_WORD_LENGTH);

        // Slider Maximum
        CSlider maxSlider = new CSlider(
                WordGenerator.MIN_WORD_LENGTH,
                WordGenerator.MAX_WORD_LENGTH,
                WordGenerator.MAX_WORD_LENGTH);

        SliderClampEvent clampEvent = new SliderClampEvent(minSlider, maxSlider);
        minSlider.addChangeListener(clampEvent);
        maxSlider.addChangeListener(clampEvent);

        // Label Maximum
        CLabel maxLabel = new CLabel("maximum:", CLabel.LIGHT_GRAY);

        addComponent(minSlider, 1, 1, 1, 30, 10, 0, 0);
        addComponent(maxSlider, 1, 2, 1, 0, 10, 0, 0);
        addComponent(title, 0, 0, 2, 0, 0, 0, 0);
        addComponent(minLabel, 0, 1, 1, 30, 0, 0, 0);
        addComponent(maxLabel, 0, 2, 1, 10, 0, 0, 0);
    }


    /**
     * @param sliderIndex Index value for the slider component ({@code minSlider} and {@code maxSlider}).
     * @return the minimum and maximum length for the word.
     */
    public int getValue(int sliderIndex) {
        return ((CSlider) getComponent(sliderIndex)).getValue();
    }

    /**
     * Action Listener for the sliders to clamp values.
     */
    private record SliderClampEvent(CSlider minSlider, CSlider maxSlider)
            implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if (minSlider.getValue() > maxSlider.getValue()
                    && e.getSource().equals(minSlider)) {
                maxSlider.setValue(minSlider.getValue());
            }

            if (maxSlider.getValue() < minSlider.getValue()
                    && e.getSource().equals(maxSlider)) {
                minSlider.setValue(maxSlider.getValue());
            }
        }
    }

}
