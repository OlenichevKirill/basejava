package ru.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InstitutionSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Institution> institutions;

    public InstitutionSection() {
    }

    public InstitutionSection(Institution... institutions) {
        this(Arrays.asList(institutions));
    }

    public InstitutionSection(List<Institution> institutions) {
        Objects.requireNonNull(institutions, "institutions must not be null");
        this.institutions = institutions;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < institutions.size(); i++) {
            if (i < institutions.size() - 1) {
                sb.append(institutions.get(i)).append("\n");
            } else {
                sb.append(institutions.get(i));
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionSection that = (InstitutionSection) o;
        return institutions.equals(that.institutions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(institutions);
    }
}
