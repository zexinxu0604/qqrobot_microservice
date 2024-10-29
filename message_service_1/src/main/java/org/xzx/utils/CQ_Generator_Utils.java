package org.xzx.utils;

public class CQ_Generator_Utils {

    public static String getAtString(long id) {
        return "[CQ:at,qq=" + id + "]";
    }

    public static String getFaceString(long id) {
        return "[CQ:face,id=" + id + "]";
    }

    public static String getEmojiString(long id) {
        return "[CQ:emoji,id=" + id + "]";
    }

    public static String getPokeString(long id) {
        return "[CQ:poke,id=" + id + "]";
    }

    public static String getImageString(String url) {
        return "[CQ:image,file=" + url + "]";
    }

    public static String getlocalImageUrl(String url) {
        return "group_image/" + url + ".png";
    }

    public static String getMarkDownString(String content) {
        return "[CQ:markdown,content=" + content + "]";
    }
}
