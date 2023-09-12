package org.xzx.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class CQ_Utils {

    public static String getAtString(long id) {
        return "[CQ:at,qq=" + id + "]";
    }

    public static String getFaceString(int id) {
        return "[CQ:face,id=" + id + "]";
    }

    public static String getEmojiString(int id) {
        return "[CQ:emoji,id=" + id + "]";
    }

    public static String getPokeString(int id) {
        return "[CQ:poke,id=" + id + "]";
    }

    public static String getImageString(String url) {
        return "[CQ:image,file=" + url + "]";
    }

    public static String getlocalImageUrl(String url) {
        return "group_image/" + url + ".png";
    }
}
