package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import jdk.nashorn.internal.runtime.Context;
import sun.applet.resources.MsgAppletViewer;

import java.io.*;
import java.util.*;

public class jsonUtils {
    static String objectPath = jsonUtils.class.getClassLoader().getResource("counter.json").getPath();
   public static String sss = readJsonFile(objectPath);
   public static JSONObject  jsonObject = JSON.parseObject(sss);


   public  static  String frePaath =jsonUtils.class.getClassLoader().getResource("frequent.json").getPath();
    public static String fff = readJsonFile(frePaath);
    public static Map freObject = (Map) JSON.parse(fff);
   // public static   JSONArray jsonarray = JSONArray.parseArray(fff);
  //  public static ArrayList<String> freObject =  (ArrayList) JSON.parse(fff);

   // public static JSONObject  freqObject = JSON.parseObject(fff);
   // public static JSONArray freqarray = freqObject.getJSONArray("counter");
  //  public static JSON freqObject =JSON.parse(jsonUtils.readJsonFile(fff));

    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //写入json
  public static void writeJsonFile(Object obj,String file){
      //  String counter = gson.toString(gson);
//      Date date = new Date();
//      long nowTime = date.getTime()/1000;
  Gson gson = new Gson();
   String json =gson.toJson(obj);
try {
    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(file))));
    writer.write(json);
    writer.close();
} catch (IOException e) {
    e.printStackTrace();
}

  }




}
