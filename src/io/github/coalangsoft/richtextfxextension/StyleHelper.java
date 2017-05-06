package io.github.coalangsoft.richtextfxextension;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matthias on 05.05.2017.
 */
public class StyleHelper {

    public static String keywordsPattern(String... keywords){
        return "\\b(" + String.join("|", keywords) + ")\\b";
    }

    private ArrayList<StyleInfo> infos;

    {
        infos = new ArrayList<>();
    }

    public void add(String category, String pattern, String styleClass){
        infos.add(new StyleInfo(category,pattern,styleClass));
    }

    public Pattern createPattern(){
        String[] patterns = new String[infos.size()];
        for(int i = 0; i < infos.size(); i++){
            StyleInfo s = infos.get(i);
            patterns[i] = "(?<" + s.getCategory() + ">" + s.getPattern() + ")";
        }
        return Pattern.compile(String.join("|", patterns));
    }

    public void attach(CodeArea area){
        Pattern p = createPattern();
        area.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    area.setStyleSpans(0, computeHighlighting(area.getText(), p));
                });
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text, Pattern p) {
        Matcher matcher = p.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {

            String styleClass = null;

            styleFindLoop:
            for(int i = 0; i < infos.size(); i++){
                StyleInfo s = infos.get(i);
                if(matcher.group(s.getCategory()) != null){
                    styleClass = s.getStyleClass();
                    break styleFindLoop;
                }
            }

            assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public ArrayList<StyleInfo> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<StyleInfo> infos) {
        this.infos = infos;
    }
}
