package CustomComponents;

import java.util.ArrayList;

public class CRadioButtonContainer {

    private ArrayList<CButton> buttons;
    private int selectedButtonIndex;

    public CRadioButtonContainer(CButton... buttons) {
        this.buttons = new ArrayList<>();
        addRadioButtons(buttons);
    }

    public void addRadioButtons(CButton... newButtons) {
        for(CButton button : newButtons) {
            button.setRadio(true);
            buttons.add(button);
            CButton.CheckButtonEvent checkButtonEvent = (CButton.CheckButtonEvent) button.getActionListeners()[0];

            checkButtonEvent.setEnabledAction(() -> checkButton(button));
            checkButtonEvent.setDisabledAction(() -> button.setChecked(true));
        }

        checkButton(buttons.get(0));
        buttons.get(0).setChecked(true);
    }

    private void checkButton(CButton checkedButton) {
        for(CButton button : buttons) {
            if(!button.equals(checkedButton))
                button.setChecked(false);
        }

        selectedButtonIndex = buttons.indexOf(checkedButton);
    }

    public CButton getSelectedButton() {
        return buttons.get(selectedButtonIndex);
    }
}
