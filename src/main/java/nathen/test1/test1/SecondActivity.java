package nathen.test1.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button button10 = (Button) findViewById(R.id.button10);

        final WifiHelper test1= new WifiHelper();

        final TextView textView3=(TextView)findViewById(R.id.textView3);
        final ListView listView1=(ListView)findViewById(R.id.listview1);

        textView3.setMovementMethod(ScrollingMovementMethod.getInstance());

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondActivity.this.finish();
            }

        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1.WifiAdmin(SecondActivity.this);
                test1.openWifi(SecondActivity.this);
//                NetworkInfo info= getIntent().getParcelableArrayListExtra(test1.mWifiManager.EXTRA_NETWORK_INFO)
                String a=test1.getSSID();
                if (a.equals(test1.SSID))
                    textView3.setText("已经连接上SSID为"+a+"的热点！");
                else
                    if (a.equals("<unknown ssid>"))
                        textView3.setText("正在搜索连接，请稍等！");
                    else
                        textView3.setText("连接失败！请手动连接SSID为"+test1.SSID+"的热点！");
            }

        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1.WifiAdmin(SecondActivity.this);
                test1.closeWifi(SecondActivity.this);
            }

        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1.WifiAdmin(SecondActivity.this);
                test1.startScan(SecondActivity.this);
                test1.mWifiList=test1.getWifiList();

                ArrayList<String> list = new ArrayList<>();
                list=test1.wholeoutput();
                ArrayAdapter adapter = new ArrayAdapter(SecondActivity.this, android.R.layout.simple_list_item_activated_1, list);
                listView1.setAdapter(adapter);
                String c="暂未开发此功能！";
                textView3.setText(c);
            }

        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SecondActivity.this,ThirdActivity.class);
                SecondActivity.this.startActivity(intent);
            }

        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1.WifiAdmin(SecondActivity.this);
                String a=intToIp(test1.getIPAddress());
                textView3.setText(a);
            }

        });


    }
    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }
}
