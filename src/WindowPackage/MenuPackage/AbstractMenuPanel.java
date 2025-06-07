package WindowPackage.MenuPackage;

import CustomComponents.CLabel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractMenuPanel extends JPanel {

    /**
     * SubPanel Constraint Array Indices
     */
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int GRID_X = 4;
    private static final int GRID_Y = 5;
    private static final int GRID_W = 6;

    protected CLabel menuTitle;
    protected CLabel menuBackground;

    public AbstractMenuPanel() {
        super(new GridBagLayout());
        setSize(WindowPackage.Window.WIDTH, Window.HEIGHT);
        setOpaque(false);
    }

    protected void addBkgImage(String bkgImgPath, int... size) {
        menuBackground = new CLabel(Window.loadImage(bkgImgPath, Window.IMAGE_SCALE));
        if(size.length != 0)
            menuBackground.setSize(size[0], size.length == 1 ? size[0] : size[1]);
        Window.PANE.add(menuBackground);
    }

    protected void addTitleText(String text) {
        menuTitle = new CLabel();
        Window.PANE.add(menuTitle);

        menuTitle.setFont(UIManager.getFont("underlineLarge"));
        menuTitle.setText(text);
        menuTitle.setLocation(Window.WIDTH/2 - menuTitle.getPreferredSize().width/2, 50);
    }

    /**
     * Add Sub panels to the menu panel with constraints
     * @param constraintsArr <br/>[0]top, [1]bottom, [2]right, [3]left, [4]gridX, [5]gridY, [6]gridW
     */

    protected void addSubPanel(AbstractSubPanel subPanel, int... constraintsArr) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5f;
        constraints.weighty = 0.5f;
        constraints.insets.set(constraintsArr[TOP], constraintsArr[LEFT],
                constraintsArr[BOTTOM], constraintsArr[RIGHT]);

        constraints.gridx = constraintsArr[GRID_X];
        constraints.gridy = constraintsArr[GRID_Y];
        constraints.gridwidth = constraintsArr[GRID_W];
        add(subPanel, constraints);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if(menuBackground != null)
            menuBackground.setVisible(visible);

        if(menuTitle != null)
            menuTitle.setVisible(visible);
    }

}
