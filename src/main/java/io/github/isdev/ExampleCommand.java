package io.github.isdev;

import io.github.singlerr.api.command.CommandContext;
import io.github.singlerr.api.command.CommandExecutor;

//명령어 클래스는 반드시 CommandExecutor interface를 상속받아야 합니다.
public class ExampleCommand implements CommandExecutor {
    //명령어 레이블을 정해주어야 합니다. 명령어 이름입니다. 실제 디스코드 사용시 !명령어 형태로 명령어를 사용하게 됩니다.
    @Override
    public String getLabel() {
        return "examplecommand";
    }
    //명령어가 입력되었을 때 발동되는 함수입니다.
    //CommandContext를 통해 디스코드 클라이언트 객체를 가져오는 등 다양한 작업이 가능합니다.
    //args는 명령어의 argument들을 모아놓은 것입니다. 예를 들어 !examplecommand test1 test2 를 입력했을 경우
    //args[0] = "test1", args[1] = "test2"가 됩니다.
    @Override
    public void execute(CommandContext ctx, String[] args) {

    }
}
