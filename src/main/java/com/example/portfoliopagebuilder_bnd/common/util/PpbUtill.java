package com.example.portfoliopagebuilder_bnd.common.util;

import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class PpbUtill {

    /**
     * read from stream
     * @throws IOException
     */
    public static String readStream(InputStream iStream) throws IOException {
        // char[] 대신 IOUtils 사용
        return IOUtils.toString(iStream);
    }


    /**
     * 에러가 났을 경우 로그를 남기는 함수이다.
     * @param logger
     * @param e
     */
    public static void errorLogging(Logger logger, Exception e) {
        logger.error(e.getMessage());
        for (StackTraceElement element : e.getStackTrace())
            logger.error("	" + element.toString());
    }

}
