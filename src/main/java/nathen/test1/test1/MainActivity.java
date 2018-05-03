package nathen.test1.test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;

import java.io.IOException;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView=(TextView)findViewById(R.id.textView);
        final TextView text2=(TextView)findViewById(R.id.textView2);
        final EditText edit1=(EditText)findViewById(R.id.editText);

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        text2.setMovementMethod(ScrollingMovementMethod.getInstance());

        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);

        final FileHelper helper1;

        helper1 = new FileHelper(getApplicationContext());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "you click the button1", Toast.LENGTH_SHORT).show();
                textView.setText("APP Started Successfully！");
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a;
                a=edit1.getText().toString();
                text2.setText(a+"\nSD卡是否存在:" + helper1.hasSD()+"\nSD卡路径:" + helper1.getSDPATH()+"\n");

                boolean truth=helper1.isthereFile("data.txt");

                if (truth==false){
                    try {
                        String b=text2.getText().toString();
                        text2.setText(b+"创建文件："+ helper1.createSDFile("data.txt").getAbsolutePath()+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    helper1.writeSDFile("哈哈哈哈哈哈哈!"+"\n", "data.txt",truth);
                }
  //
                else{
                    String c=text2.getText().toString();
                    text2.setText(c+"续写文件："+"\n");
                    helper1.writeSDFile("123"+"\n", "data.txt",truth);
                }
  //                  Toast.makeText(MainActivity.this, "已经有文件了！", Toast.LENGTH_SHORT).show();}
            }

        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //               Toast.makeText(MainActivity.this, "you click the button3", Toast.LENGTH_SHORT).show();
                String a=text2.getText().toString();
                text2.setText(a+"删除文件是否成功:"+ helper1.deleteSDFile("data.txt")+"\n");
            }

        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,SecondActivity.class);
                MainActivity.this.startActivity(intent);
            }

        });


    }


}



