package ru.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class Institution {

    private Link homePage;
    List<Position> positions;


    public Institution(String name, String url, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Institution that = (Institution) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }


        @Override
    public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <positions.size(); i++) {
                if (i < positions.size() - 1) {
                    sb.append(positions.get(i)).append("\n");
                } else {
                    sb.append(positions.get(i));
                }
            }
        return  homePage.toString() + "\n" + sb.toString();
    }
}
