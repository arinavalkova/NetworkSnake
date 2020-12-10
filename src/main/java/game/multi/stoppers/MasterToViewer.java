package game.multi.stoppers;

import game.multi.GamePlay;

public class MasterToViewer implements ToViewer {
    @Override
    public void start(GamePlay gamePlay) {
        System.out.println("Master to viewer");
    }
}
