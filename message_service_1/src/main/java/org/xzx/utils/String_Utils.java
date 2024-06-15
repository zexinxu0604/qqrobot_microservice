package org.xzx.utils;

public class String_Utils {
    /**
     *
     * @param str https://gchat.qpic.cn/gchatpic_new/xx/xx-xx-2E6A4FDC0BEDC592FEC7494D72F0D3D5/0?term=2
     * @return 2E6A4FDC0BEDC592FEC7494D72F0D3D5
     * @description 获取标识图片的字符串
     */
    public static String getImageName(String str) {
        return str.substring(str.lastIndexOf("-") + 1, str.lastIndexOf("/"));
    }

    public static String getNewUrl(String str) {
        int lastIndex = str.lastIndexOf("/");
        int secondIndex = str.substring(0, lastIndex).lastIndexOf("/");
        int thirdIndex = str.substring(0, secondIndex).lastIndexOf("/");
        if(str.substring(thirdIndex + 1, secondIndex).equals("1")){
            str = str.substring(0, thirdIndex) + "/0/" + str.substring(secondIndex + 1);
        }
        return str;
    }
}
