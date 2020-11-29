package game.multi.proto.decorators;

import dto.GameState;

public class GameStateDecorator {
    private final GameState gameState;

    public GameStateDecorator(GameState gameState) {
        this.gameState = gameState;
    }

}
