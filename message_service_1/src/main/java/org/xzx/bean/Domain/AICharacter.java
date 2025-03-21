package org.xzx.bean.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xzx.bean.enums.AICharacters;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@TableName("ai_character")
public class AICharacter {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("character_desc")
    private String characterDesc;

    @TableField("character_prompt")
    private String characterPrompt;

    public AICharacter(String characterDesc, String characterPrompt) {
        this.characterDesc = characterDesc;
        this.characterPrompt = characterPrompt;
    }

    public AICharacter(String characterDesc) {
        this.characterDesc = characterDesc;
    }

    public static AICharacter createDefaultAICharacter() {
        return new AICharacter(AICharacters.GroupAssistant.getName(), AICharacters.GroupAssistant.getCharacter());
    }
}
