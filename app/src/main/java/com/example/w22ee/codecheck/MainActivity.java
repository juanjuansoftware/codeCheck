package com.example.w22ee.codecheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

public class MainActivity extends AppCompatActivity {
    private EditText codeInputText;
    private TextView codeResultView;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeInputText = (EditText) findViewById(R.id.codeInputText);
        codeInputText.setInputType(InputType.TYPE_CLASS_PHONE);
        codeInputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        codeResultView = (TextView) findViewById(R.id.codeResultView);
        checkButton = (Button) findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNum(codeInputText.getText().toString());
            }
        });
    }

    private void checkNum(String code){
        AsyncHttpClient client = new AsyncHttpClient();
        checkButton.setEnabled(false);
        codeResultView.setText("查询中，请稍后...");
        client.get(MainActivity.this, "http://211.141.223.109:3988/search.php?phone="+code, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        checkButton.setEnabled(true);
                        if (responseBody!=null){
                            String result = new String(responseBody);

                            JSONObject jsonObject ;
                            try {
                                jsonObject= new JSONObject(result);
                                if (jsonObject!=null){
                                    String msg = jsonObject.getString("msg");
                                    if (msg!=null){
                                        codeResultView.setText(msg);
                                    }
                                }
                            }catch (JSONException e){
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        checkButton.setEnabled(true);
                        codeResultView.setText("请求失败");
                    }
                }
        );
    }
}
