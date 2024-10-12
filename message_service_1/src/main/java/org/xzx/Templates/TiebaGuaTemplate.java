package org.xzx.Templates;

import org.xzx.Templates.interfaces.StringTemplate;
import org.xzx.bean.Jx3.Jx3Response.Jx3TiebaGuaResponse;
import org.xzx.bean.Jx3.Jx3Response.Jx3TiebaItemResponse;

public class TiebaGuaTemplate implements StringTemplate<Jx3TiebaGuaResponse> {
    private final String basic_tieba_url = "https://tieba.baidu.com/p/%s";
    @Override
    public String getResultWithTemplate(Jx3TiebaGuaResponse jx3TiebaGuaResponse) {
        String result = jx3TiebaGuaResponse.getTitle() + "\n";
        result += String.format(basic_tieba_url, jx3TiebaGuaResponse.getUrl());
        return result;
    }
}
