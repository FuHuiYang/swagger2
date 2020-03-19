package com.yang.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.util
 * @Description: TODO
 * @date Date : 2020年03月19日 13:52
 */
public class JsoupUtil {
    public static String readHtml(String fileName){
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(fileName);
            byte[] bytes = new byte[1024];
            while (-1 != fis.read(bytes)) {
                sb.append(new String(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }
}
