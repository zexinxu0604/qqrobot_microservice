package org.xzx.bean.messageUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xzx.bean.enums.MessageBreakCode;

@Data
@AllArgsConstructor
public class MessageBreaker {
    MessageBreakCode messageBreakCode;

    public boolean isBreak() {
        return messageBreakCode == MessageBreakCode.BREAK;
    }

    public boolean isContinue() {
        return messageBreakCode == MessageBreakCode.CONTINUE;
    }
}
