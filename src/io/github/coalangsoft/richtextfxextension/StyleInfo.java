package io.github.coalangsoft.richtextfxextension;

/**
 * Created by Matthias on 05.05.2017.
 */
public class StyleInfo {

    private String category;
    private String pattern;
    private String styleClass;

    public StyleInfo(String category, String pattern, String styleClass) {
        this.category = category;
        this.pattern = pattern;
        this.styleClass = styleClass;
    }
    public StyleInfo(){}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}
