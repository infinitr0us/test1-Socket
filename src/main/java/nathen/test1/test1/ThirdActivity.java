package nathen.test1.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnBack;
    private Button btnReceive;
    private TextView view1;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;

    private Handler hander = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            String s = (String)msg.obj;
 //           view1.setText(view1.getText()+"\n"+s); 此位置也可以在textview显示出收到的消息！！！
  //          Toast.makeText(ThirdActivity.this, s, Toast.LENGTH_LONG).show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        btnStart = (Button)findViewById(R.id.button11);
        view1=(TextView)findViewById(R.id.textView4);
        view1.setMovementMethod(ScrollingMovementMethod.getInstance());
        btnBack=(Button)findViewById(R.id.button12);
        btnReceive=(Button)findViewById(R.id.button13);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new ServerThread().start();//在新线程中启动SocketServer...
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdActivity.this.finish();
            }

        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ThirdActivity.this,ForthActivity.class);
                ThirdActivity.this.startActivity(intent);
            }

        });
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

    }
    private class ServerThread extends Thread {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
//                serverSocket = new ServerSocket(50005);//默认的路由器地址为Address: 192.168.43.33
                serverSocket = new ServerSocket(5000);
                while (true) {
                    Socket clientSocket = serverSocket.accept();//阻塞等待处理...
                    String remoteIP = clientSocket.getInetAddress().getHostAddress();
                    int remotePort = clientSocket.getLocalPort();

                    String output;
                    System.out.println("A client connected. IP:" + remoteIP + ", Port: " + remotePort);
                    System.out.println("server: receiving.............");

                    output="A client connected. IP:" + remoteIP + ", Port: " + remotePort+"\nserver: receiving.............";
                    // 获得 client 端的输入输出流，为进行交互做准备
                    in = new BufferedReader(new InputStreamReader(
                            clientSocket.getInputStream()));
                   // out = new PrintWriter(clientSocket.getOutputStream(), false);

                    // 获得 client 端发送的数据
                    String tmp = in.readLine();
                    // String content = new String(tmp.getBytes("utf-8"));
                  //  System.out.println("Client message is: " + tmp);

                    output=output+"\nClient message is:   " + tmp;

                    // 向 client 端发送响应数据
                 //   out.println("Your message has been received successfully！.");

                    output=output+"\nYour message has been received successfully！";
                    view1.setText(output);

                    // 关闭各个流
                 //   out.close();
                    in.close();
                    Message message = hander.obtainMessage();
                    message.obj = tmp;
//                    hander.sendMessage(message);
 //                   try {
 //                       Thread.sleep(100);
 //                   } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
//                        e.printStackTrace();
  //                  }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
