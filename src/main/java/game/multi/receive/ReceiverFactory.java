package game.multi.receive;

import game.multi.Game;
import game.multi.Network;

import java.util.ArrayList;

public class ReceiverFactory {
    private final Network network;
    private Game game;

    private final ArrayList<String> currentGames; //maybe CopyOnWrite List
    //очередь для сообщений с желанием повернуть, работает если я мастер

    public ReceiverFactory(Network network, Game game) {
        this.network = network;
        this.game = game;
        this.currentGames = new ArrayList<>();
    }

    public void start() {
        //прием всего и обработка
    }

    public void stop() {
        //если я мастер отправить deputy очередь с сообщениями с желанием повернуть
        //все остановить здесь
    }

    public ArrayList<String> getCurrentGames() { // <-- TO DO
        return null;
    }

    //синхронизированный метод для получения последних сообщений и удаления их
}
