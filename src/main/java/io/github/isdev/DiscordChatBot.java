package io.github.isdev;

import io.github.singlerr.DiscordBot;
import io.github.singlerr.api.command.CommandContext;
import io.github.singlerr.api.command.CommandExecutor;
import io.github.singlerr.api.command.CommandManager;
import io.github.singlerr.api.event.EventManager;
import io.github.singlerr.api.module.Module;

public class DiscordChatBot extends Module {
    //On this application started.
    @Override
    public void onEnable() {
        //디스코드 명령어는 다음과 같이 등록합니다.
        CommandManager.getManager().registerCommand(ExampleCommand.class);
        //디스코드 이벤트는 다음과 같이 등록합니다.
        //챗봇과 같은 경우 명령어를 쓰는 경우는 거의 없고, 날 그대로의 메시지를 받아와야하는 경우가 대다수기에 이 이벤트 매니저를 사용하게 될 것입니다.
        EventManager.getManager().registerEventListener(new ExampleEvent());
    }

    @Override
    public void onDisable() {

    }
}
