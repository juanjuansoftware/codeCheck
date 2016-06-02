package com.example.w22ee.codecheck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

    private void checkNum(String code) {

        checkButton.setEnabled(false);
        codeResultView.setText("查询中，请稍后...");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://211.141.223.109:3988/search.php?phone=" + code, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                checkButton.setEnabled(true);
                if (response != null) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response);
                        if (jsonObject != null) {
                            String msg = jsonObject.getString("msg");
                            if (msg != null) {
                                codeResultView.setText(msg);
                            }
                        }
                    } catch (JSONException e) {
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkButton.setEnabled(true);
                codeResultView.setText("请求失败");
            }
        });

        Volley.newRequestQueue(MainActivity.this).add(stringRequest);


    }
}
