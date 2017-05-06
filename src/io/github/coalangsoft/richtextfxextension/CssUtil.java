package io.github.coalangsoft.richtextfxextension;

import javafx.scene.text.Font;

/**
 * Created by Matthias on 05.05.2017.
 */
public class CssUtil {

    public static String font(Font f){
        return "-fx-font-family: \"" + f.getFamily() + "\";" +
                "-fx-font-size: " + f.getSize() + "pt;";
    }

}
