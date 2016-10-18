package com.soneca.poc.qrcode.webapp.view;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * Created by andre on 18/10/16.
 */
@Controller
public class QrCodeController {

    @RequestMapping("/qrcode/{deviceId}/{passwd}")
    public String generate(
            @PathVariable("deviceId") String deviceId,
            @PathVariable("passwd") String passwd,
            Model model) {
        model.addAttribute("image", "data:image/png;base64," +
                writeQrCode(200, 200,
                        getShortUrl(deviceId, passwd)));
        return "qrcode";
    }

    @RequestMapping("/qrcodeJson/{deviceId}/{passwd}")
    public String generateJson(
            @PathVariable("deviceId") String deviceId,
            @PathVariable("passwd") String passwd,
            Model model) {
        model.addAttribute("image", "data:image/png;base64," +
                writeQrCode(200, 200,
                        getJsonObject(deviceId, passwd)));
        return "qrcode";
    }


    private String getShortUrl(String deviceId, String passwd){
        return "10.10.4.23:8080/json/" + deviceId + "/" + passwd;
    }

    private String getJsonObject(String deviceId, String passwd){
        return "{\n" +
                "\t\"user\": \""+ deviceId +"\",\n" +
                "\t\"password\": \""+ passwd +"\",\n" +
                "\t\"uri-http\": \"http://api.hackathon.konkerlabs.net/\",\n" +
                "\t\"uri-mqtt\": \"http://mqtt.hackathon.konkerlabs.net\",\n" +
                "\t\"http-port\": \"80 | 443\",\n" +
                "\t\"mqtt-port\": \"1883 | 8883\",\n" +
                "\t\"pub\": \"pub/"+ deviceId +"/{canal}\",\n" +
                "\t\"sub\": \"sub/"+ deviceId +"/{canal}\",\n" +
                "}";



    }

    private String writeQrCode(int width, int height, String content){
        BitMatrix bitMatrix = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Base64OutputStream b64 = new Base64OutputStream(out);
            bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", b64);
            return new String(out.toByteArray(), 0, out.size(), "UTF-8");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

}
