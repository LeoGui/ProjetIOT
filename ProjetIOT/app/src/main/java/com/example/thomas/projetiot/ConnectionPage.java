package com.example.thomas.projetiot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConnectionPage extends AppCompatActivity {
    public WebServiceTask wst;
    private MainActivity Main;



    public void ConnectionPage (MainActivity Main){
        this.Main=Main;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_page);
        Button launch = (Button)findViewById(R.id.connection);
        launch.setOnClickListener(O);

    }


    public View.OnClickListener O = new View.OnClickListener(){


        @Override
        public void onClick(View v) {
            //Main.postConnection();
            startActivity();

        }
    };

    private void startActivity() {
        Intent intent = new Intent(ConnectionPage.this,MainActivity.class);
        startActivity(intent);
    }
}
