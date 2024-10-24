package org.xzx.utils;

import com.aliyun.oss.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CQ码相关工具类
 */
public class CQ_String_Utils {

    /**
     *
     * @param str [CQ:xxx]sadas[CQ:xxx]sadas[CQ:xxx]
     * @return [CQ:xxx]
     * @description 将字符串按照[CQ:xxx]分割，提取CQ码
     */
    public static List<String> getCQStrings(String str) {
        String patternString = "\\[CQ:[^\\]]*\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        List<String> string_list=new ArrayList<String>();
        while (matcher.find()) {
            string_list.add(matcher.group(0));
        }

        return string_list;
    }


    /**
     *
     * @param str [CQ:image,file=2e6a4fdc0bedc592fec7494d72f0d3d5.image,subType=1,url=https://gchat.qpic.cn/gchatpic_new/xx/xx-xx-2E6A4FDC0BEDC592FEC7494D72F0D3D5/0?term=2&amp;is_origin=0]
     * @return https://gchat.qpic.cn/gchatpic_new/xx/xx-xx-2E6A4FDC0BEDC592FEC7494D72F0D3D5/0?term=2
     * @description 获取图片的url，分割出&amp;前的第一个元素
     */
    public static String getImageURL(String str) {
        String url = str.substring(str.indexOf("url=") + 4, str.indexOf(",file_size"));
        url = url.replace("amp;", "");
        return url;
    }

    /**
     *
     * @param str [CQ:image,summary=&#91;动画表情&#93;,file=000001444e61704361744f6e65426f747c4d736746696c657c327c37313034373832347c373432393238373333363637373930313738357c373432393238373333363637373930313738347c4568516e52415958653338665150437a3158476e666963373934507767686a67334145675f776f6f363579747965656d69514d794248427962325251674c326a41566f514d4745654f5f716e6d305a68727a73344b7133575677.85DE901B1047568B0D198DC03C50AD06.jpg,sub_type=1,file_id=000001444e61704361744f6e65426f747c4d736746696c657c327c37313034373832347c373432393238373333363637373930313738357c373432393238373333363637373930313738347c4568516e52415958653338665150437a3158476e666963373934507767686a67334145675f776f6f363579747965656d69514d794248427962325251674c326a41566f514d4745654f5f716e6d305a68727a73344b7133575677.85DE901B1047568B0D198DC03C50AD06.jpg,url=https://multimedia.nt.qq.com.cn/download?appid=1407&amp;fileid=EhQnRAYXe38fQPCz1XGnfic794Pwghjg3AEg_woo65ytyeemiQMyBHByb2RQgL2jAVoQMGEeO_qnm0Zhrzs4Kq3WVw&amp;spec=0&amp;rkey=CAMSKMa3OFokB_TlXle3aEQvRLKJ2t4MDE-haXDl_2LFDml6RenfaD6oq_M,file_size=28256,file_unique=85DE901B1047568B0D198DC03C50AD06.jpg]
     * @return 2e6a4fdc0bedc592fec7494d72f0d3d5.image
     * @description 获取图片的文件名
     */
    public static String getImageFileName(String str){
        String s = str.replace("[", "");
        s = s.replace("]", "");
        String[] pieces = s.split(",");
        for (String piece : pieces) {
            if (piece.contains("file_unique=")) {
                return piece.substring(piece.indexOf("file_unique=") + 12);
            }
        }
        return "0";
    }

    /**
     *
     * @param str https://gchat.qpic.cn/gchatpic_new/xx/xx-xx-2E6A4FDC0BEDC592FEC7494D72F0D3D5/0?term=2
     * @return 2E6A4FDC0BEDC592FEC7494D72F0D3D5
     * @description 获取标识图片的字符串
     */
    public static String getImageName(String str) {
        return str.substring(str.lastIndexOf("-") + 1, str.lastIndexOf("/"));
    }

    /**
     *
     * @param str [CQ:image,file=2e6a4fdc0bedc592fec7494d72f0d3d5.image,subType=1,url=https://gchat.qpic.cn/gchatpic_new/xx/xx-xx-2E6A4FDC0BEDC592FEC7494D72F0D3D5/0?term=2&amp;is_origin=0]
     * @return 1
     * @description 获取图片的子类型，1为表情包，0为正常图片
     */
    public static String getImageSubType(String str) {
        String s = str.replace("[", "");
        s = s.replace("]", "");
        String[] pieces = s.split(",");
        for (String piece : pieces) {
            if (piece.contains("sub_type=")) {
                return piece.substring(piece.indexOf("sub_type=") + 9);
            }
        }
        return "0";
    }

    /**
     * 判断是否为收藏图片
     * @param str CQ码
     * @return true 是收藏表情
     */
    public static boolean ifStaredImage(String str) {
        return getImageSubType(str).equals("1");
    }

    public static String getIdFromReply(String str) {
        return str.substring(str.indexOf("id=") + 3, str.indexOf("]"));
    }

    public static String getQQFromAt(String str) {
        return str.substring(str.indexOf("qq=") + 3, str.indexOf("]"));
    }

    public static long getReplyAtQQ(String str) {
        List<String> cqStrings = CQ_String_Utils.getCQStrings(str);
        String atPersonCQ = cqStrings.get(1);
        return Long.parseLong(CQ_String_Utils.getQQFromAt(atPersonCQ));
    }

    public static int getMessageId(String str){
        List<String> cqStrings = CQ_String_Utils.getCQStrings(str);
        String replycq = cqStrings.get(0);
        return Integer.parseInt(CQ_String_Utils.getIdFromReply(replycq));
    }

    public static int getImageFileSize(String str){
        return Integer.parseInt(str.substring(str.indexOf("file_size=") + 10, str.indexOf("]")));
    }

    public static String getGroupServiceCloseMessage(String ServiceName){
        return "已关闭"+ServiceName+"服务";
    }

}
