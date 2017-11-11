package com.fentomproject.smarthouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    MqttHelper mqttHelper;

    TextView dataReceived;
    Button button3,button4;
    CheckBox box;
    ToggleButton button1,button2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataReceived = (TextView) findViewById(R.id.dataReceived);
        box = (CheckBox) findViewById(R.id.checkBox);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckBox bo = (CheckBox) v;
                if(((CheckBox) v).isChecked()){
                    mqttHelper.subscribeToTopicMode("on");
                }else{
                    mqttHelper.subscribeToTopicMode("off");
                }
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mqttHelper.subscribeToTopic1("toggle");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mqttHelper.subscribeToTopic2("toggle");
            }
        });

        startMqtt();
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                dataReceived.setText("Connection");
                //mqttHelper.subscribeToTopic();
            }

            @Override
            public void connectionLost(Throwable throwable) {
                dataReceived.setText("Disconnection");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());
                dataReceived.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

}


