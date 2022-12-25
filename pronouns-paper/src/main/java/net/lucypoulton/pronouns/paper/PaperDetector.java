package net.lucypoulton.pronouns.paper;

public class PaperDetector {
    public static final boolean IS_PAPER;

    static {
        boolean isPaper;
        try {
            Class.forName("io.papermc.paper.text.PaperComponents");
            isPaper = true;
        } catch (ClassNotFoundException e) {
            isPaper = false;
        }
        IS_PAPER = isPaper;
    }
}
