package io.github.isdev;

import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.singlerr.api.event.EventListener;

//이벤트 클래스는 반드시 디스코드 API의 이벤트를 box로 하는 EventListener 클래스를 상속받아야 합니다.
//이는 이 이벤트가 발동되었을 때 이 클래스의 이벤트 발동 함수를 실행시키겠다는 의미입니다.
public class ExampleEvent extends EventListener<MessageCreateEvent> {
    //이벤트가 발생했을 때 이 함수가 작동합니다. event 객체에서 이벤트와 관련된 정보를 받아올 수 있습니다.
    //이것에 대한 자세한 내용은 Discord4J 문서를 참고하세요
    @Override
    public void on(MessageCreateEvent event) {
    }

    //이 함수로 어떤 이벤트의 클래스를 등록할 것인지 알려주어야 합니다.
    @Override
    public Class<MessageCreateEvent> getEventClass() {
        return MessageCreateEvent.class;
    }
}
