package com.soneca.poc.qrcode.webapp.view;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 18/10/16.
 */
@RestController
public class RestCodeEndpoint {

    @RequestMapping("/json/{deviceId}/{passwd}")
    public String json(
            @PathVariable("deviceId") String deviceId,
            @PathVariable("passwd") String passwd,
            Model model) {
        return getJsonObject(deviceId, passwd);
    }

    private String getJsonObject(String deviceId, String passwd){
        return "{\n" +
                "\t\"user\": \""+ deviceId +"\",\n" +
                "\t\"password\": \""+ passwd +"\",\n" +
                "\t\"http.pub\": \"http://api.hackathon.konkerlabs.net:[80|443]/pub/"+ deviceId +"/{canal}\",\n" +
                "\t\"http.sub\": \"http://api.hackathon.konkerlabs.net:[80|443]/sub/"+ deviceId +"/{canal}\",\n" +
                "\t\"mqtt.pub\": \"mqtt://mqtt.hackathon.konkerlabs.net:[1883|8883]/pub/"+ deviceId +"/{canal}\",\n" +
                "\t\"mqtt.sub\": \"mqtt://mqtt.hackathon.konkerlabs.net:[1883|8883]/sub/"+ deviceId +"/{canal}\"\n" +
                "}";



    }

}
