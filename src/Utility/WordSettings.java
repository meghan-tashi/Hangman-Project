package Utility;

/**
 * This record is used to store and access the filters for words like,
 * the minimum length, maximum length of the word used when
 * searching for words and options like are hints to be shown or not.
 */
public record WordSettings(int minLength, int maxLength, String wordFilter, boolean isHintsEnabled) {

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getWordFilter() {
        return wordFilter;
    }

    public boolean isHintsEnabled() {
        return isHintsEnabled;
    }
}
