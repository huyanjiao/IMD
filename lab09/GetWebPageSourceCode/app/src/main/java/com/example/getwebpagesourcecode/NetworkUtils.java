package com.example.getwebpagesourcecode;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    //log
    private static final String LOG_TAG = com.example.getwebpagesourcecode.NetworkUtils.class.getSimpleName();
    static String getPageInfo(String urlstring){
        //connect internet
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String webpage = null;
        try{
            URL requestURL=new URL(urlstring);

            //open url
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //response-ready
            InputStream inputStream=urlConnection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder=new StringBuilder();
            //response-resolve:read line-by-line
            String line;
            while ((line=reader.readLine())!=null){
//                System.out.println("line"+line+"\n");
                builder.append((line));
                builder.append("\n");//only easy for debug
            }
            //check
            if (builder.length()==0){
                return null;
            }
            //builder->string
            webpage=builder.toString();

        }
        catch (IOException e) {
            System.out.println("catch");
            e.printStackTrace();
        }
        finally {
            System.out.println("finally");
            //connection close
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            //reader close
            if(reader!=null){
                try {
                    reader.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
        Log.d(LOG_TAG,webpage);

        //return json
        return webpage;


    }

}