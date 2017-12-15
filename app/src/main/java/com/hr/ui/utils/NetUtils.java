package com.hr.ui.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wdr on 2017/12/13.
 */

public class NetUtils {
    /**
     * get 请求
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static InputStream HttpGet(String path) throws IOException {
        // FakeX509TrustManager.allowAllSSL();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(3000);
        conn.connect();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = conn.getInputStream();
            return inputStream;
        }
        return null;
    }

    /**
     * inputstream to byte[]
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readAsByteArray(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            return outSteam.toByteArray();
        } finally {
            outSteam.close();
            inputStream.close();
        }
    }

    /**
     * @param inputStream
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String readAsString(InputStream inputStream, String encode)
            throws UnsupportedEncodingException, IOException {
        return new String(readAsByteArray(inputStream), encode);
    }

}
