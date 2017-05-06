package io.github.coalangsoft.richtextfxextension;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import org.fxmisc.richtext.CodeArea;

/**
 * Created by Matthias on 05.05.2017.
 */
public class PropertyBasedCodeArea extends CodeArea {

    private ObjectProperty<Font> font;

    private boolean fontChanging;

    {
        font = new SimpleObjectProperty<>();

        font.addListener(new ChangeListener<Font>() {
            @Override
            public void changed(ObservableValue<? extends Font> observable, Font oldValue, Font newValue) {
                fontChanging = true;
                setStyle(getStyle() + CssUtil.font(newValue));
                fontChanging = false;
            }
        });

        styleProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!fontChanging){
                    fontChanging = true;
                    setStyle(newValue + CssUtil.font(getFont()));
                    fontChanging = false;
                }
            }
        });
    }

    public ObjectProperty<Font> fontProperty(){
        return font;
    }
    public Font getFont(){
        return font.get();
    }
    public void setFont(Font f){
        font.set(f);
    }

}
