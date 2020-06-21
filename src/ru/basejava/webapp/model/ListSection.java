package ru.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {

    private List<String> list = new ArrayList<>();

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                sb.append("* ").append(list.get(i)).append("\n");
            } else {
                sb.append("* ").append(list.get(i));
            }
        }
        return sb.toString();
    }
}
