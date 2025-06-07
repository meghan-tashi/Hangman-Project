package WindowPackage;

import CustomComponents.CLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * <u>Custom Window class:</u><p>
 * Used to create a Window using JFrame with background image.
 */
public class Window extends JFrame {

    public static final Cursor PRESSED_CURSOR = Window.getCustomCursor("Icons/rolloverCursor");
    public static final Cursor DEFAULT_CURSOR = Window.getCustomCursor("Icons/cursor");

    /**
     * Width of the JFrame (current main window)
     */
    public static final int WIDTH = 1248;

    /**
     * Height of the JFrame
     */
    public static final int HEIGHT = 702;

    /**
     * Universal Scale for all loaded images.
     */
    public static final float IMAGE_SCALE = 0.65f;

    /**
     * Content Pane of JFrame
     */
    public static JPanel PANE;

    /**
     * Private Singleton of this class.
     */
    private final static Window MAIN = new Window();

    /**
     * {@code PANE} is initialized with layout, opacity and preferred size.
     * {@code JFrame} is created by setting its contentPane to {@code PANE}.
     */
    public static void init(String title) {
        PANE = new JPanel(null);
        PANE.setOpaque(false);
        PANE.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        MAIN.setTitle(title);
        MAIN.setResizable(false);
        MAIN.setDefaultCloseOperation(EXIT_ON_CLOSE);
        MAIN.setLayout(null);
        MAIN.setContentPane(PANE);

        MAIN.pack();
        MAIN.setLocationRelativeTo(null);

//        MAIN.setCursor(DEFAULT_CURSOR);
    }

    /**
     * Finalizing {@code JFrame} with a background image added to {@code PANE}<br/>
     * Note: Called at the end, when all the components
     *       are added to the Window
     */
    public static void create(String bkgImageFile) {
        CLabel backgroundImage = new CLabel(loadImage(bkgImageFile, IMAGE_SCALE));
        backgroundImage.setSize(PANE.getPreferredSize());
        PANE.add(backgroundImage);

        MAIN.setVisible(true);
        MAIN.repaint();
    }

    /**
     * Loads an image and scales it to {@code IMAGE_SCALE} = {@value IMAGE_SCALE}
     * @return The scaled image as ImageIcon
     */
    public static ImageIcon loadImage(String file, float scale) {
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(Window.class.getResource("/" + file + ".png")));
            return new ImageIcon(img.getScaledInstance((int)(img.getWidth()*scale), (int)(img.getHeight()*scale),
                    BufferedImage.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cursor getCustomCursor(String file) {
        ImageIcon imageIcon = loadImage(file, 1);

        assert imageIcon != null;
        Image cursorImg = imageIcon.getImage();
        return Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), file);
    }
}