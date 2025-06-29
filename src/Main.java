import Utility.AudioClip;
import WindowPackage.GamePackage.GameContainer;
import Utility.WordGenerator;
import WindowPackage.MenuPackage.MenuContainer;
import WindowPackage.Window;

public class Main {

    public static void main(String[] args) {

        // Reads and Stores a list of English words
        WordGenerator.init();
        AudioClip.init();

        // This creates a JFrame
        Window.init("Hangman in javax.swing (Advaitya01, Meghan02, Sagar03 , Vinayak04)");
        GameContainer.instance.init();
        MenuContainer.instance.init();
        Window.create("Backgrounds/board");
    }
}
