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
@NoArgsConstructor
@AllArgsConstructor
@Builder

@TableName("ai_character")
public class AICharacter {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("character_desc")
    private String character_desc;

    @TableField("character_prompt")
    private String character_prompt;

    public AICharacter(String character_desc, String character_prompt) {
        this.character_desc = character_desc;
        this.character_prompt = character_prompt;
    }

    public AICharacter(String character_desc) {
        this.character_desc = character_desc;
    }

    public static AICharacter createDefaultAICharacter() {
        return new AICharacter(AICharacters.GroupTalker.getName(), AICharacters.GroupTalker.getCharacter());
    }
}
