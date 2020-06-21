package ru.basejava.webapp.model;

import java.util.List;

public class InstitutionSection extends AbstractSection {

    private List<Institution> institutions;

    public InstitutionSection(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <institutions.size(); i++) {
            if (i < institutions.size() - 1) {
                sb.append(institutions.get(i)).append("\n");
            } else {
                sb.append(institutions.get(i));
            }
        }
        return sb.toString();
    }
}
