package graphics.data;

public class NewGameWindowData {
    private static int fieldWidth;
    private static int fieldHeight;

    public static void setFieldWidth(int fieldWidth) {
        NewGameWindowData.fieldWidth = fieldWidth;
    }

    public static void setFieldHeight(int fieldHeight) {
        NewGameWindowData.fieldHeight = fieldHeight;
    }

    public static int getFieldWidth() {
        return fieldWidth;
    }

    public static int getFieldHeight() {
        return fieldHeight;
    }
}
