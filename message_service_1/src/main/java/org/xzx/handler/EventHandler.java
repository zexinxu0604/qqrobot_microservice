package org.xzx.handler;

import org.xzx.annotation.RobotListenerHandler;
import org.xzx.pojo.messageBean.Message;

import java.util.function.Consumer;

public record EventHandler(RobotListenerHandler annotation, Consumer<Message> consumer) {
    public void accept(Message message) {
        consumer.accept(message);
    }

    public void acceptIfContainsId(long id, Message message) {
        if(annotation.contactId().length == 0) {
            consumer.accept(message);
        } else {
            boolean contains = false;
            for (long l : annotation.contactId()) {
                if(l == id) {
                    contains = true;
                    break;
                }
            }
            if(contains) consumer.accept(message);
        }
    }

    public int compareOrder(EventHandler another){
        return this.annotation.order() - another.annotation.order();
    }
}
