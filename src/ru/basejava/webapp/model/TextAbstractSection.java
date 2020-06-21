package ru.basejava.webapp.model;

public class TextAbstractSection extends AbstractSection {

    private String text;

    public TextAbstractSection(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return  text;
    }
}
