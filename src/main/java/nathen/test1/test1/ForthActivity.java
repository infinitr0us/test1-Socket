package nathen.test1.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ForthActivity extends AppCompatActivity {
    public Button sendBtn,sendMessageBtn,back;
    public TextView view2;
    public Socket socket;
    public PrintStream output;
    public BufferedInputStream bufferedInputStream;
    public ReadThread readThread;
    public EditText text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);

        sendBtn = (Button) findViewById(R.id.button14);
        sendMessageBtn = (Button) findViewById(R.id.button15);
        back=(Button)findViewById(R.id.button16);
        view2=(TextView)findViewById(R.id.textView5);
        view2.setMovementMethod(ScrollingMovementMethod.getInstance());
        text2=(EditText)findViewById(R.id.editText2);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ForthActivity.this.finish();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Thread(runnable).start();//开启线程
                view2.setText("已经开启线程！\n");
                String a=text2.getText().toString();
                view2.setText(view2.getText()+a);
            }
        });
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                closeSocket();
  //              sendMessage("hello,i am from client message");
  //              view2.setText("已经发送的消息为：hello,i am from client message");
            }
        });
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            initClientSocket();
         //   readThread = new ReadThread();
         //   readThread.start();
        }
    };

    public void initClientSocket() {
        try {
            socket = new Socket("192.168.43.33", 5000); //43.171
            output = new PrintStream(socket.getOutputStream(), true, "gbk");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            System.out.println("请检查端口号是否为服务器IP");
            view2.setText("请检查端口号是否为服务器IP!");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("服务器未开启");
            view2.setText("服务器未开启!");
            e.printStackTrace();
        }
        String a=text2.getText().toString();
        output.println("你收到了主机的信息！  "+a);
        view2.setText("你已经已发送的消息为：你收到了主机的信息！  "+a);
        closeSocket();
        output.close();
    }

    public byte[] receiveData() {
        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket("192.168.43.33", 5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        byte[] data = null;
        if (socket.isConnected()) {
            try {
                bufferedInputStream = new BufferedInputStream(socket.getInputStream());
                data = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            data = new byte[1];
        }
        return data;
    }

    private void sendMessage(String str) {
        output.println(str);
    }

    public void closeSocket() {
        try {
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("error"+e);
            view2.setText("error"+e);
        }
    }
    private class ReadThread extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while (true) {
                byte[] data = receiveData();
                if (data.length > 1) {
                    System.out.println(data.toString());
                    //view2.setText(data.toString());
                }
            }
        }

    }
}