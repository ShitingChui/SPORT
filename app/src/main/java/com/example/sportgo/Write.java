package com.example.sportgo;

////add output to file [2019.04.10 by CTW]
//package net.waynepiekarski.wearsensors;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



public class Write {
    private static Context context;

    public Write(StopSportFragment stopSportFragment) {
        this.context = stopSportFragment.getContext();
    }

    public static void WriteFileExample(String message, String filename) {
        FileOutputStream fop = null;
        File file;
        String content = message;

        try {
//            File sdcard = Environment.getExternalStorageDirectory();
//            File sdcard = context.getFilesDir();
            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS); //寫入檔案至外部公用資料夾 //[2019.05.04 by ANGUS]

            file = new File(sdcard, filename); //輸出檔案位置 //now date time [2019.04.10 by CTW]
            Log.i("Write File:", file + "");
            if("SensorList.txt".equals(filename))
                fop = new FileOutputStream(file);
            else
                fop = new FileOutputStream(file,true);

            if (!file.exists()) { // 如果檔案不存在，建立檔案
                file.createNewFile();
            }

            byte[] contentInBytes = content.getBytes();// 取的字串內容bytes

            fop.write(contentInBytes); //輸出
            fop.flush();
            fop.close();

            Toast.makeText(context, filename , Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Log.i("Write E:", e + "");
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                Log.i("Write IOException", e + "");
                e.printStackTrace();
            }
        }
    }
}
