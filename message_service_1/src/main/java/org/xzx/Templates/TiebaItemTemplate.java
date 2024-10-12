package org.xzx.Templates;

import org.xzx.bean.Jx3.Jx3Response.Jx3TiebaItemResponse;
import org.xzx.Templates.interfaces.StringTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TiebaItemTemplate implements StringTemplate<Jx3TiebaItemResponse> {

    private final String basic_tieba_url = "https://tieba.baidu.com/p/%s?pid=%s";
    private final String response_content = "查询到的物品贴子如下：\n";
    private final String item_content = "贴子时间：%s\n贴子内容：%s\n楼层：%s\n";

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public String getResultWithTemplate(Jx3TiebaItemResponse jx3TiebaItemResponse) {
        String result = response_content;
        result += String.format(item_content, simpleDateFormat.format(new Date(jx3TiebaItemResponse.getTime() * 1000)), jx3TiebaItemResponse.getContext(), jx3TiebaItemResponse.getFloor());
        result += String.format(basic_tieba_url, jx3TiebaItemResponse.getUrl(), jx3TiebaItemResponse.getReply());
        return result;
    }
}
