package org.xzx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xzx.handler.HandlerResolver;
import org.xzx.handler.RobotEventPostProcessor;
import org.xzx.pojo.messageBean.Received_Group_Message;
import org.xzx.pojo.messageBean.Received_Private_Message;

@Controller
public class messageController {

    @RequestMapping("/message")
    public String test(@RequestBody JsonNode jsonNode, HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(jsonNode);
        if (jsonNode.get("post_type").asText().equals("message")) {
            if (jsonNode.get("message_type").asText().equals("group")) {
                Received_Group_Message groupMessage = mapper.treeToValue(jsonNode, Received_Group_Message.class);
                request.setAttribute("message", groupMessage);
                return "forward:/groupMessage";
            }

            if (jsonNode.get("message_type").asText().equals("private")) {
                Received_Private_Message privateMessage = mapper.treeToValue(jsonNode, Received_Private_Message.class);
                request.setAttribute("message", privateMessage);
                return "forward:/privateMessage";
            }

        }
        return "";
    }

    @RequestMapping("/groupMessage")
    @ResponseBody
    public void handler_group_message(HttpServletRequest request) {
        Received_Group_Message groupMessage = (Received_Group_Message) request.getAttribute("message");
        HandlerResolver.handleEvent(groupMessage);
        System.out.println(groupMessage.getMessage());
    }

    @RequestMapping("/privateMessage")
    @ResponseBody
    public void handler_private_message(HttpServletRequest request) {
        Received_Private_Message receivedPrivateMessage = (Received_Private_Message) request.getAttribute("message");
        HandlerResolver.handleEvent(receivedPrivateMessage);
        System.out.println(request.getAttribute("message"));
    }

    @RequestMapping("/null")
    @ResponseBody
    public void other(HttpServletRequest request) {

    }

//    @RequestMapping("/sendGroupMessage")
//    @ResponseBody
//    public String send_group_message(String message, int group_id) {
//        gocqUtils.send_group_message(group_id, message);
//        return "ok";
//    }
}
