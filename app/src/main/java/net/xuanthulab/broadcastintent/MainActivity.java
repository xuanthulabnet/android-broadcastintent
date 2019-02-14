package net.xuanthulab.broadcastintent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyBroadcastReceiver myBroadcastReceiver;

    class MyBroadcastReceiver extends BroadcastReceiver {
        public static final String ACTION_FIRST_ACTION  = "my-first-broadcastintent";

        MyBroadcastReceiver() {

        }
        //Code thi hành khi Receiver nhận được Intent
        @Override
        public void onReceive(Context context, Intent intent) {

            //Kiểm tra Action của Intent nhận được có tên irst-broadcastintent
            if (intent.getAction().equals(MyBroadcastReceiver.ACTION_FIRST_ACTION)) {
                //Đọc dữ liệu trong Intent
                String d = intent.getStringExtra("dataname");
                Toast.makeText(context, d, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //CREATE AND SEND BROADCAST INTENT
                String actionName = "my-first-broadcastintent";
                Intent intent = new Intent(actionName);
                //Thiết lập tên để cho Receiver nhận được thì biết đó là loại Intent
                intent.setAction(actionName);
                //Dữ liệu gắn vào Intent thiết lập bằng putExtra với (tên, dữ liệu), dữ liệu là
                //các kiểu cơ bản Int, String ... hoặc các loại đối tượng lớp kế thừa từ Serializable
                intent.putExtra("dataname", "Hello, How are you?");
                //Thực hiện lan truyền Intent trong hệ thống
                sendBroadcast(intent);
            }
        });

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyBroadcastReceiver.ACTION_FIRST_ACTION);

        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
