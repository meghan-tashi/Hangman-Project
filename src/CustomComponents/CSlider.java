package CustomComponents;

import Utility.AudioClip;
import Utility.FontMetricsUtil;
import WindowPackage.Window;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * <u>Custom Slider class:</u><br/>
 * Used to create a slider for custom graphical representation.
 */

public class CSlider extends JSlider {

    /**
     * Calls the parent constructor - {@link JSlider#JSlider(int, int, int)}.
     * Few settings like opacity and focusable are set including the child class
     *     {@code CSliderUI} as custom UI of this class.
     * @param min Minimum value of the slider
     * @param max Maximum value of the slider
     * @param value Default value of the slider
     */
    public CSlider(int min, int max, int value) {
        super(min, max, value);
        setOpaque(false);
        setFocusable(false);
        setPaintTicks(true);

        CSliderUI sliderUI = new CSliderUI(this);
        setUI(sliderUI);
        setSize(sliderUI.getThumbSize());
        setFont(UIManager.getFont("regular"));
        setMajorTickSpacing(1);
        addMouseMotionListener(sliderUI);

        // Offsets the preferred size height for custom rendering
        Dimension preferredSize = getPreferredSize();
        preferredSize.height += 20;
        setPreferredSize(preferredSize);

        CSliderAudioListener audioListener = new CSliderAudioListener();
        addMouseMotionListener(audioListener);
        addMouseListener(audioListener);
        setCursor(Window.PRESSED_CURSOR);
    }

    private static class CSliderAudioListener extends MouseAdapter {
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            AudioClip clip = AudioClip.getAudioClip("Press");
            if(!clip.isPlaying())
                clip.play();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            AudioClip.getAudioClip("Over").play();
        }
    }

    public static class CSliderUI extends BasicSliderUI implements MouseMotionListener {

        private static final Dimension THUMB_SIZE = new Dimension(50, 40);

        public CSliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            g.setColor(Color.white);
            FontMetricsUtil.useFontMetrics(slider.getFontMetrics(slider.getFont()));
            super.paint(g, c);
        }

        @Override
        public void paintTrack(Graphics g) {
            g.fillRoundRect(
                    trackRect.x,
                    trackRect.y + FontMetricsUtil.getScaledHeight(0.8f, 0),
                    trackRect.width, 2, 1, 1);
        }

        @Override
        public void paintTicks(Graphics g) {
            Font tickFont = UIManager.getFont("small");
            FontMetricsUtil.useFontMetrics(slider.getFontMetrics(tickFont));

            String startTick = String.valueOf(slider.getMinimum());
            String endTick = String.valueOf(slider.getMaximum());

            int startTickX = tickRect.x - FontMetricsUtil.getScaledWidth(startTick, 0.5f, 0);
            int endTickX = tickRect.x + tickRect.width - FontMetricsUtil.getScaledWidth(endTick, 0.5f, 0);
            int tickY = trackRect.y + FontMetricsUtil.getScaledHeight(2.5f, 0);

            int totalTicks = Math.abs(slider.getMaximum()-slider.getMinimum());
            for(int i = 0; i <= totalTicks; i += slider.getMajorTickSpacing()) {
                double tickUnit = tickRect.getWidth()/totalTicks;
                g.fillRect(
                        (int) (tickUnit * i) + tickRect.x,
                        tickRect.y - FontMetricsUtil.getScaledHeight(0.8f, 0), 1, 8);
            }

            g.setFont(tickFont);
            g.drawString(startTick, startTickX, tickY);
            g.drawString(endTick, endTickX, tickY);

            g.setFont(slider.getFont());
        }

        @Override
        public void paintThumb(Graphics g) {
            String thumbText = "[" + slider.getValue() + "]";
            int thumbX = (int) (thumbRect.x + thumbRect.getWidth()*0.5f);

            int thumbY = thumbRect.y + FontMetricsUtil.getScaledHeight(0.8f, 0);

            g.setColor(CLabel.YELLOW);
            g.drawString(thumbText, thumbX - FontMetricsUtil.getScaledWidth(thumbText, 0.5f, 10), thumbY);
            g.drawString("|", thumbX - FontMetricsUtil.getScaledWidth("|", 0.5f, 0), thumbY + 20);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            slider.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE.getSize();
        }
    }
}