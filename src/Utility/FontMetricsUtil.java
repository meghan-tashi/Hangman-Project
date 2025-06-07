package Utility;

import java.awt.*;

/**
 * <u>Utility Class:</u><p>
 * Used to get calculated size of string in pixels
 */
public class FontMetricsUtil {

    private static FontMetrics metrics;

    public static int getScaledWidth(String text, float scale, float padding) {
        return (int) ((metrics.stringWidth(text) + padding) * scale);
    }

    public static int getScaledHeight(float scale, float padding) {
        return (int) ((metrics.getHeight() + padding) * scale);
    }

    public static void useFontMetrics(FontMetrics metrics) {
        FontMetricsUtil.metrics = metrics;
    }
}
