package com.example.getwebpagesourcecode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    //spinner
    private Spinner spinner;
    private String protocol_result;
    private TextView scrolltext;
    //edittext
    private EditText input_text;
    private String url_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize
        spinner = findViewById(R.id.protocol);
        input_text=findViewById(R.id.input_text);
        scrolltext=findViewById(R.id.scrollText);

        //spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.protocol_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //响应用户-Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                protocol_result=(String) parent.getItemAtPosition(position);
                System.out.println(protocol_result);
            }

            //没有设定空项，所以只要打开应用就选中第一个
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("mainActivity","onNothingSelected");
            }
        });
    }

    public void getEditText(View view){
        //edittext
        //隐藏键盘
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
//        System.out.println(url_text);


    }
    //start button的操作
    public void getwholeurl(View view) {

        //获取EditText内容
        String text_input = input_text.getText().toString();
        //合并
        String urlstring=protocol_result+"://"+text_input;

        //检查网络连接
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()
                && urlstring.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("urlstring", urlstring);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            scrolltext.setText(R.string.loading);
        }else {
            urlstring.length();
            scrolltext.setText(R.string.no_network);
        }
    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String urlstring = "";

        if (args != null) {
            urlstring = args.getString("urlstring");
        }
        return new PageLoader(this, urlstring);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
//        super.onPostExecute(s);
        //parse  website
//        System.out.println("data"+data);
        scrolltext.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}