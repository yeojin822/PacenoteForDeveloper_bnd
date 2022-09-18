package com.example.portfoliopagebuilder_bnd.common.util.web;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
            connection.setRequestMethod(method);
            //Content-Type 또한 요청 받는 데이터 타입이 Json이면 json타입으로 선언해주시면 되고 -> 추후 타입 변경
            // 개발 환경에 따라 설정해주시면 됩니다.
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setUseCaches(false);// 캐싱데이터를 받을지 말지 세팅합니다.
            // 쓰기모드를 지정할지 세팅 (GET인데 true이면 자동으로 POST변경)
            connection.setDoOutput(method.equals("GET") ? false : true);

            //Send request
            //위에서 세팅한 정보값을 바탕으로 요청을 보냅니다.
            if(connection.getDoOutput()){
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream());
                //파라미터 정보를 보냅니다.
                wr.writeBytes(param);
                //요청 실행후 dataOutputStream을 close 합니다.
                wr.close();
            }

            //Get Response
            InputStream is = connection.getInputStream();
            //요청 결과 (response)를 BufferedReader로 받습니다.
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            // 자바 5 이상은 StringBuffer 를 이용해서 결과 값을 읽습니다.
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
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
}
