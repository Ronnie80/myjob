/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package main.java.com.google.myjob.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Pa {
    private static final String URL = "https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2020-01-02"
            + "&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=DTV&purpose_codes=ADULT";
    public static String pa12306() {
        try {
            java.net.URL realUrl = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Cookie", "JSESSIONID=4B8810CF93B44FB730D56742B0B40ECF;"
                    + " RAIL_EXPIRATION=1577327175519;"
                    + " RAIL_DEVICEID=bgwDW7RsP9DVar0Jl5vckugFDxSQobTQe5BBMtDFG6tnnWbvkPmeGKmHhvPqrCfPiVq65Zb_MryIurohGCP_xR_5CUv72VMLyrv63uHTzBln5f7od0yKOpnOwMkr-KdRIVQ4KPpqbxs9gsDp0Ya7cHE1JgPGmu1U;"
                    + " _jc_save_fromStation=%u5317%u4EAC%2CBJP;"
                    + " _jc_save_toStation=%u5927%u540C%2CDTV; "
                    + "_jc_save_fromDate=2019-12-28;"
                    + " _jc_save_toDate=2019-12-22; _jc_save_wfdc_flag=dc;"
                    + " route=6f50b51faa11b987e576cdb301e545c4; "
                    + "BIGipServerotn=116392458.38945.0000");
            connection.connect();
            //请求成功
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //10MB的缓存
                byte[] buffer = new byte[10485760];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                String jsonString = baos.toString();
                baos.close();
                is.close();
                System.out.println(jsonString.substring(0, 200));
                return jsonString;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }
}
