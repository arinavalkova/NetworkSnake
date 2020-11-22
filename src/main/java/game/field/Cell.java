package game.field;

import java.util.ArrayList;
import java.util.Objects;

public class Cell {
    private final int x;
    private final int y;
    private final ArrayList<CellRole> roles;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.roles = new ArrayList<>();
    }

    public void setRole(CellRole role) {
        roles.add(role);
    }

    public void removeRole(CellRole role) {
        roles.remove(role);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<CellRole> getCellRole() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean findRole(CellRole role) {
        return roles.contains(role);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", roles=" + roles +
                '}';
    }
}
