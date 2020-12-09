package game.multi;

import dto.NodeRole;

public class RoleChanger {

    public void change(GamePlay gamePlay, NodeRole nodeRoleForChange) {
        if (gamePlay.getNodeRole() == NodeRole.MASTER && nodeRoleForChange == NodeRole.VIEWER) {

        }
        if (gamePlay.getNodeRole() == NodeRole.NORMAL && nodeRoleForChange == NodeRole.DEPUTY) {

        }
        if (gamePlay.getNodeRole() == NodeRole.NORMAL && nodeRoleForChange == NodeRole.VIEWER) {

        }
        if (gamePlay.getNodeRole() == NodeRole.DEPUTY && nodeRoleForChange == NodeRole.VIEWER) {

        }
        if (gamePlay.getNodeRole() == NodeRole.DEPUTY && nodeRoleForChange == NodeRole.MASTER) {

        }
        if (gamePlay.getNodeRole() == NodeRole.VIEWER && nodeRoleForChange == NodeRole.MASTER) {

        }
    }

    //будет использоваться:
    //   когда мастер делает ход
    //   в слушателе кнопки CHANGE_MODE
    //   в confirm message

    //прочитать задание
    //confirmSander
    //найти заместителя
    //дописать этот класс
}
