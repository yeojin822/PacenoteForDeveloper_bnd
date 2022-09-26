package com.example.portfoliopagebuilder_bnd.common.util.web;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class WebRequestUtil {

    public static String httpRequest(String targetURL, String param, String method){
        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();

            setting(method, connection);

            writeBody(param, connection);

            StringBuilder response = response(connection);

            return response.toString();
        } catch (Exception e) {
            //connection.getResponseMessage()
            e.printStackTrace();
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static StringBuilder response(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();

        return response;
    }

    private static void setting(String method, HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod(method);
        //Content-Type 또한 요청 받는 데이터 타입이 Json이면 json타입으로 선언 -> 추후 타입 변경
        //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setUseCaches(false);// 캐싱데이터를 받을지 말지 세팅
        connection.setDoOutput(method.equals("GET") ? false : true); // 쓰기모드를 지정할지 세팅 (GET인데 true이면 자동으로 POST변경)
    }

    private static void writeBody(String param, HttpURLConnection connection) throws IOException {
        if(connection.getDoOutput()){
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            //파라미터 정보를 보냅니다.
            wr.writeBytes(param);
            //요청 실행후 dataOutputStream을 close 합니다.
            wr.close();
        }
    }
}
