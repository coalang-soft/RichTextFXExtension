package test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cpa.subos.datastore.ParseException;
import cpa.subos.datastore.WriteException;
import cpa.subos.io.IO;
import cpa.subos.xml.XmlParser;
import cpa.subos.xml.XmlWriter;
import io.github.coalangsoft.richtextfxextension.PropertyBasedCodeArea;
import io.github.coalangsoft.richtextfxextension.StyleHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

public class KeywordsDemo extends Application {

    private static final String sampleCode = String.join("\n", new String[] {
            "package com.example;",
            "",
            "import java.util.*;",
            "",
            "public class Foo extends Bar implements Baz {",
            "",
            "    /*",
            "     * multi-line comment",
            "     */",
            "    public static void main(String[] args) {",
            "        // single-line comment",
            "        for(String arg: args) {",
            "            if(arg.length() != 0)",
            "                System.out.println(arg);",
            "            else",
            "                System.err.println(\"Warning: empty string as argument\");",
            "        }",
            "    }",
            "",
            "}"
    });


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws WriteException, MalformedURLException, ParseException {
        PropertyBasedCodeArea codeArea = new PropertyBasedCodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setFont(Font.font(20));

        XmlStyleHelper h = new XmlParser().parse(XmlStyleHelper.class, IO.file("coalang-keywords.xml"));

//        XmlStyleHelper h = new XmlStyleHelper();
//        h.add("KEYWORD", KEYWORD_PATTERN, "keyword");
//        h.add("PAREN", PAREN_PATTERN, "paren");
//        h.add("BRACE", BRACE_PATTERN, "brace");
//        h.add("BRACKET", BRACKET_PATTERN, "bracket");
//        h.add("SEMICOLON", SEMICOLON_PATTERN, "semicolon");
//        h.add("STRING", STRING_PATTERN, "string");
//        h.add("COMMENT", COMMENT_PATTERN, "comment");
        h.attach(codeArea);
//
//        new XmlWriter().write(h, IO.file("java-keywords.xml"));

        codeArea.replaceText(0, 0, sampleCode);

        Scene scene = new Scene(new StackPane(new VirtualizedScrollPane<>(codeArea)));
        scene.getStylesheets().add(new File("coalang-keywords.css").toURI().toURL().toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Coalang Keywords Demo");
        primaryStage.show();
    }

}