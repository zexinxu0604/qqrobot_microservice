package org.xzx.bean.enums;

import lombok.Getter;
import org.xzx.configs.Constants;

@Getter
public enum AICharacters {
    GroupTalker(Constants.GROUP_TALKER_PROMPT, "聊天型人格"),
    GroupDenyer(Constants.GROUP_DENYER_PROMPT, "反驳型人格"),
    GroupAttacker(Constants.GROUP_ATTACK_PROMPT, "攻击型人格");

    private final String character;

    private final String name;

    AICharacters(String character, String name) {
        this.character = character;
        this.name = name;
    }

    public static boolean validateCharacterName(String character_name) {
        for (AICharacters aiCharacter : AICharacters.values()) {
            if (aiCharacter.getName().equals(character_name)) {
                return true;
            }
        }
        return false;
    }

    public static String listCharacters() {
        StringBuilder sb = new StringBuilder();
        for (AICharacters aiCharacter : AICharacters.values()) {
            sb.append(aiCharacter.getName()).append("、");
        }
        return sb.toString();
    }

    public static AICharacters getCharacterByName(String character_name) {
        for (AICharacters aiCharacter : AICharacters.values()) {
            if (aiCharacter.getName().equals(character_name)) {
                return aiCharacter;
            }
        }
        return null;
    }

}
