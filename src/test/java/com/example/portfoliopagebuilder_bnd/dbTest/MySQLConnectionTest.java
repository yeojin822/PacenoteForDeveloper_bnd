package com.example.portfoliopagebuilder_bnd.dbTest;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
public class MySQLConnectionTest {

    // MySQL Connector 의 클래스. DB 연결 드라이버 정의
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // DB 경로
    private static final String URL = "jdbc:mysql://localhost:3306/oauth2?serverTimezone=Asia/Seoul&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    private static final String USER = "user";
    private static final String PASSWORD = "0000";

    @Test
    public void testConnection() throws Exception {
        // DBMS에게 DB 연결 드라이버의 위치를 알려주기 위한 메소드
        Class.forName(DRIVER);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.close();
            }
        }

    }
}
