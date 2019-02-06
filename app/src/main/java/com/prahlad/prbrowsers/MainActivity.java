package com.prahlad.prbrowsers;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView brow;
    EditText urledit;
    Button go,forward,back,clear,reload;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        brow=(WebView)findViewById(R.id.wv_brow);
        urledit = (EditText)findViewById(R.id.et_url);
        go = (Button)findViewById(R.id.btn_go);
        forward = (Button)findViewById(R.id.btn_fwd);
        back = (Button)findViewById(R.id.btn_bck);
        clear = (Button)findViewById(R.id.btn_clear);
        reload = (Button)findViewById(R.id.btn_reload);


        brow.setWebViewClient(new ourViewClient());
        brow.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);

                if(newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        WebSettings websettings = brow.getSettings();
        websettings.setJavaScriptEnabled(true);


        brow.loadUrl("http://weblover.000webhostapp.com/browser");

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edittextvalue = urledit.getText().toString();

                if (!edittextvalue.startsWith("http://"))
                    edittextvalue = "http://" + edittextvalue;

                String url = edittextvalue;
                brow.loadUrl(url);

                //Hiding the keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urledit.getWindowToken(),0);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(brow.canGoForward())
                    brow.goForward();

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(brow.canGoBack())
                    brow.goBack();
            }
        });


        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brow.reload();

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brow.clearHistory();

            }
        });



    }
    int backButtonCount = 0;
    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     */
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 4)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}
