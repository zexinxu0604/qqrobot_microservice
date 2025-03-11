package org.xzx.bean.enums;

public enum AiModels {
    GPT_4o_MINI("gpt-4o-mini"),
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    DEEPSEEK_CHAT("deepseek-chat"),
    DEEPSEEK_REASONER("deepseek-reasoner");

    private final String model;

    AiModels(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public static boolean validateModelName(String model_name) {
        for (AiModels aiModel : AiModels.values()) {
            if (aiModel.getModel().equals(model_name)) {
                return true;
            }
        }
        return false;
    }
}
