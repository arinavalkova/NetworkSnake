package graphics.drawers;

import game.field.CellRole;

import java.util.Map;

public class DrawerColor {
    public final static String FOOD = "#ef5350";
    public final static String SNAKE = "#64b5f6";

    private static final Map<CellRole, String> drawerColorMap = Map.of(
            CellRole.FOOD, FOOD,
            CellRole.SNAKE, SNAKE
    );

    public static String getColor(CellRole cellRole) {
        return drawerColorMap.get(cellRole);
    }
}
