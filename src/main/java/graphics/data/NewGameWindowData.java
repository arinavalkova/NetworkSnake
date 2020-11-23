package graphics.data;

public class NewGameWindowData {

    private static int      fieldWidth;
    private static int      fieldHeight;
    private static int      foodStatic;
    private static int      foodPerPlayer;
    private static int      stateDelay;
    private static double   deadFoodProb;
    private static int      pingDelay;
    private static int      nodeTimeOut;
    private static boolean  point;


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

    public static int getFoodStatic() {
        return foodStatic;
    }

    public static void setFoodStatic(int foodStatic) {
        NewGameWindowData.foodStatic = foodStatic;
    }

    public static int getFoodPerPlayer() {
        return foodPerPlayer;
    }

    public static void setFoodPerPlayer(int foodPerPlayer) {
        NewGameWindowData.foodPerPlayer = foodPerPlayer;
    }

    public static int getStateDelay() {
        return stateDelay;
    }

    public static void setStateDelay(int stateDelay) {
        NewGameWindowData.stateDelay = stateDelay;
    }

    public static double getDeadFoodProb() {
        return deadFoodProb;
    }

    public static void setDeadFoodProb(double deadFoodProb) {
        NewGameWindowData.deadFoodProb = deadFoodProb;
    }

    public static int getPingDelay() {
        return pingDelay;
    }

    public static void setPingDelay(int pingDelay) {
        NewGameWindowData.pingDelay = pingDelay;
    }

    public static int getNodeTimeOut() {
        return nodeTimeOut;
    }

    public static void setNodeTimeOut(int nodeTimeOut) {
        NewGameWindowData.nodeTimeOut = nodeTimeOut;
    }

    public static boolean isPoint() {
        return point;
    }

    public static void setPoint(boolean point) {
        NewGameWindowData.point = point;
    }
}
