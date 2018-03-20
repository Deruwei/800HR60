package com.hr.ui.utils.datautils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.hr.ui.app.HRApplication;
import com.hr.ui.constants.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wdr on 2017/12/14.
 */

public class SaveFile {
    public static void updateCJ(Context context, String filename,String json)
            throws IOException, FileNotFoundException {
        // 要写入的文本
        try {
            FileOutputStream fos = context.openFileOutput(filename,
                    MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(json);
            //osw.write("hello Wrold !");
            osw.flush();
            fos.flush();
            osw.close();
            fos.close();
           System.out.println("往data/包名/files/ 路径下创建文本文件成功，注意查收");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //判断文件是否存在
    public static boolean fileIsExists(String strFile)
    {
        try
        {
            FileInputStream fin = HRApplication.getAppContext().openFileInput(strFile);
            if(fin.available()==0)
            {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static String getDataFromInternalStorage(Context context,String fileName){
        String s="";
        try {

            FileInputStream fps = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fps, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sBuilder.append(line);
            }
           s=sBuilder.toString();
           System.out.println("读取成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;

    }
}
