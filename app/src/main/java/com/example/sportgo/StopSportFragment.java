package com.example.sportgo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;


public class StopSportFragment extends Fragment {
    private fragment_stop_sport sListener; //Edit listener to sListener //[2019.05.10 by ANGUS]
    private Button buttonStoptSport;

    public interface fragment_stop_sport{
        void onInputASent(CharSequence input);
    }

    //[2019.05.10 by ANGUS]
    public static final int MAX_SENSOR_VALUES = 6;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    private Sensor[] sensorArray;
    private int sensorIndex;
    private DecimalFormat decimalFormat;
    private boolean stopHandler;
    private Handler uiThreadHandler;
    private Object lockSensorRate;
    private Runnable uiRunnableUpdate;
    private int samplesCount;
    private int samplesSeconds;

//    private Write write = new Write(StopSportFragment.this); //[2019.04.9 by CTW]

    private String outtemp = "";// temp record of sensor raw data [2019.04.10 by CTW]
    private String filename = "";// file name [2019.04.11 by CTW]
    private Boolean ifoutput = false;// if start output raw data [2019.04.16 by CTW]

    private long startTime = 0; //[2019.05.01 by ANGUS]
    private long startSecond = 0; //[2019.05.01 by ANGUS]
    private long startMinute = 0; //[2019.05.11 by ANGUS]
    private long startHour = 0; //[2019.05.11 by ANGUS]
    private long currentTime = 0; //[2019.05.11 by ANGUS]
    private long currentSecond = 0; //[2019.05.11 by ANGUS]
    private long currentMinute = 0; //[2019.05.11 by ANGUS]
    private long currentHour = 0; //[2019.05.11 by ANGUS]
    int sampleNum = 0;//[2019.05.01 by ANGUS]

    private Context mContext;//[2019.05.10 by ANGUS]

    WekaUse wekaUse = new WekaUse();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop_sport, container, false);
        Button btnStopSportFragment = (Button)view.findViewById(R.id.button_stop_sport);

        btnStopSportFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction frTwo= getFragmentManager().beginTransaction();
                frTwo.replace(R.id.fragment_container,new SportFragment());
                frTwo.commit();
            }
        });

        return view;
    }

    //[2019.05.10 by ANGUS]
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();//[2019.05.10 by ANGUS]
        sensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);

        List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);
        if (list.size() < 1) {
//            Toast.makeText(this, "No sensors returned from getSensorList", Toast.LENGTH_SHORT);
            Logging.fatal("No sensors returned from getSensorList");
        }
        sensorArray = list.toArray(new Sensor[list.size()]);
        for (int i = 0; i < sensorArray.length; i++) {
            Logging.debug("Found sensor " + i + " " + sensorArray[i].toString());
            outtemp = outtemp + "Found sensor " + i + " " + sensorArray[i].toString() + "\n";//save sensor list in outtemp [2019.04.16 by CTW]
        }
//        write.WriteFileExample(outtemp, "SensorList.txt");// out put sensor list [2019.04.16 by CTW]
        outtemp = "";// clear outtemp [2019.04.16 by CTW]

        sensorIndex = 0;
        sensor = sensorArray[sensorIndex];

        //the start of difference from CTW's program //[2019.05.10 by ANGUS]
//        buttonStoptSport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                write.WriteFileExample(outtemp, filename); // 寫入輸入文字 [2019.04.11 by CTW]
//                outtemp = "";//clear outtemp//[2019.04.10 by CTW]
//                filename = "";//clear filename//[2019.04.12 by CTW]
//                ifoutput = false;
//            }
//        });

        //auto start output
        filename = sensorIndex + "_" + sensor.getName() + "_" + Calendar.getInstance().getTime() +"_Log.csv";//set new file name [2019.04.12 by CTW]
        filename = "0" + "_" + "1" + "_" + "Accelerometer" + "_" + "Gyroscope" + "_" + Calendar.getInstance().getTime() +"_Log.csv";//set new file name [2019.05.02 by ANGUS]
        ifoutput = true;

        startTime = System.currentTimeMillis();//setTimer //[2019.05.01 by ANGUS]
        startSecond = (startTime / 1000) % 60;//setTimer //[2019.05.01 by ANGUS]
        startMinute = (startTime / (1000*60)) % 60;//[2019.05.11 by ANGUS]
        startHour = (startTime / (1000*60*60)) % 24;//[2019.05.11 by ANGUS]

        Log.i("sensor", "startSecond = " + startSecond);//[2019.05.01 by ANGUS]
        //the end of difference from CTW's program //[2019.05.10 by ANGUS]

        // Implement a runnable that updates the rate statistics once per second. Note
        // that if we change sensors, it will take 1 second to adjust to the new speed.
        uiThreadHandler = new Handler();
        lockSensorRate = new Object();
        samplesCount = -1;
        samplesSeconds = 0;
        stopHandler = false;
        uiRunnableUpdate = new Runnable() {
            @Override
            public void run() {
                Logging.debug("Updating the UI every second, count is " + samplesCount);
                if (samplesCount == -1)
                    ;
                else if (samplesCount == 0) {
                    samplesSeconds ++;
                } else {
                    samplesSeconds = 0;
                    samplesCount = 0;
                }

                if (!stopHandler) {
                    uiThreadHandler.postDelayed(this, 1000);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        startSensor();
        uiThreadHandler.post(uiRunnableUpdate);
    }

    public void changeSensor(int ofs) {
        sensorIndex += ofs;
        if (sensorIndex >= sensorArray.length)
            sensorIndex = 0;
        else if (sensorIndex < 0)
            sensorIndex = sensorArray.length-1;
        sensor = sensorArray[sensorIndex];
        stopSensor();
        startSensor();
    }

    public String getStrFromFloat(float in) {
        if ((in > -0.00001) && (in < 0.00001))
            in = 0;
        if (in == Math.rint(in))
            return Integer.toString((int)in);
        else
            return decimalFormat.format(in);
    }
    private void startSensor() {
        String type = "#" + (sensorIndex+1) + ", type " + sensor.getType();
        if (Build.VERSION.SDK_INT >= 20)
            type = type + "=" + sensor.getStringType();
        Logging.debug("Opened up " + type + " - " + sensor.toString());

//        outtemp = outtemp + android.os.Build.DEVICE + " " + android.os.Build.ID + "\n"; //device type [2019.04.12 by CTW]
//        outtemp = outtemp + type + "\n"; //sensor type [2019.04.12 by CTW]
//        outtemp = outtemp + sensor.toString().replace("{Sensor ", "").replace("}", "") + "\n";//sensor detail [2019.04.12 by CTW]

        samplesCount = 0;

        listener = new SensorEventListener() {

            public String getStrFromFloat(float in) {
                if ((in > -0.00001) && (in < 0.00001))
                    in = 0;
                if (in == Math.rint(in))
                    return Integer.toString((int)in);
                else
                    return decimalFormat.format(in);
            }

            public int min(int a, int b) { if (a < b) { return a; } else { return b; } }



            public void onSensorChanged(SensorEvent sensorEvent) {
//                if(sensorIndex == 1 && sampleNum == 11){ //每10筆資料換一次偵測器//[2019.05.02 by ANGUS]
//                    changeSensor(-1);
//                    sampleNum = 0;
//                } else if (sensorIndex == 0 && sampleNum == 11){
//                    changeSensor(+1);
//                    sampleNum = 0;
//                }
//                sampleNum++;
//                Log.i("sensor", "sampleNum = " + sampleNum);

                if (sensorEvent.sensor.getType() == sensor.getType()) {
                    Logging.detailed("Sensor update: " + Arrays.toString(sensorEvent.values));
                    samplesCount++;

                    String raw = "";

                    for (int i = 0; i < sensorEvent.values.length; i++) {
//                        String str = getStrFromFloat(sensorEvent.values[i]); // 刪除把資料寫成raw檔的功能 //[2019.05.10 by ANGUS]
//                        if (raw.length() != 0)
//                            raw = raw + "\n";
//                        raw = raw + str;
                        Log.i("sensor", "sensorValue" + i + " = " + sensorEvent.values[i]);
                    }

                    if(ifoutput) {
                        outtemp = outtemp + raw.replace("\n", ",") + "," + Calendar.getInstance().getTime() + "," + sensorEvent.timestamp +"," + sensorIndex +"\n";//save sensors in outtemp  [2019.04.10 by CTW] Add "," + Calendar.getInstance().getTime()[2019.04.27 by ANGUS]
//                        if(outtemp.getBytes().length >= 10240){
//                            write.WriteFileExample(outtemp, filename); // 寫入輸入文字 [2019.04.11 by CTW] // 刪除寫入文件功能 //[2019.05.10 by ANGUS]
//                            outtemp = "";//clear outtemp//[2019.04.10 by CTW]
//                        }

                        currentTime = System.currentTimeMillis();//計算時間差// [2019.05.01 by ANGUS]
                        currentSecond = (currentTime / 1000) % 60;// [2019.05.01 by ANGUS]
                        currentMinute = (currentTime / (1000*60)) % 60;//[2019.05.11 by ANGUS]
                        currentHour = (currentTime / (1000*60*60)) % 24;//[2019.05.11 by ANGUS]
//                        Log.i("sensor", "currentSecond = " + currentSecond);
                        if(currentSecond < startSecond){
                            currentSecond += 60;
                        }
                        if(currentSecond - startSecond == 11){ //10秒後停止
//                            write.WriteFileExample(outtemp, filename); // [2019.05.01 by ANGUS]
                            outtemp = "";//clear outtemp // [2019.05.01 by ANGUS]
                            ifoutput = false;// [2019.05.01 by ANGUS]
                        }

                    }
                }
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Logging.detailed("Accuracy update: " + accuracy);
            }
        };
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        super.onStop();
        stopSensor();
        uiThreadHandler.removeCallbacks(uiRunnableUpdate);
    }

    private void stopSensor() {
        sensorManager.unregisterListener(listener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sListener = null; //Edit listener to sListener //[2019.05.10 by ANGUS]
    }
}
