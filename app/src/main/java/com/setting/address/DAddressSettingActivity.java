package com.setting.address;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liveangel.test.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class DAddressSettingActivity extends ActionBarActivity {

    private EditText edit_street;
    private EditText edit_building;
    private EditText edit_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daddress_setting);
        edit_street = (EditText) this.findViewById(R.id.edit_street);
        edit_building = (EditText) this.findViewById(R.id.edit_building);
        edit_room = (EditText) this.findViewById(R.id.edit_room);
        Button btn = (Button) this.findViewById(R.id.button_submit_DAddress);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get 用户id
                new SubmitAddressTask().execute();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daddress_setting, menu);
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

    private class SubmitAddressTask extends AsyncTask<String,Void,Object>{
        @Override
        protected Object doInBackground(String... params) {
            String id = "mg1546256548";
            String street = edit_street.getText().toString();
            String building = edit_building.getText().toString();
            String room = edit_room.getText().toString();

            submitUserAddr(id, street, building, room);
            return null;
        }

        private void submitUserAddr(String id, String street,String building,String room){
            //text.setText(id + ": " + addr);
            String requestIP = "http://10.0.3.2:8080/LazyGift/MyAddrSet";
            HttpClient client = new DefaultHttpClient();
            HttpPost httpRequest = new HttpPost(requestIP);
            //String url = requestIP + "?ID="+ id + "&ADDRESS="+addr;
            //HttpGet getRequest = new HttpGet(url);
            ArrayList<NameValuePair> params;
            params = new ArrayList<>();
            params.add(new BasicNameValuePair("ID",id));
            params.add(new BasicNameValuePair("STREET",street));
            params.add(new BasicNameValuePair("BUILDING", building));
            params.add(new BasicNameValuePair("ROOM", room));
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

                HttpResponse response = client.execute(httpRequest);

                if(response.getStatusLine().getStatusCode() == 200)
                {
            /*取出响应字符串*/

                    String strResult = EntityUtils.toString(response.getEntity());
                    //show strResult?
                    System.out.println(strResult);
                }
                else
                {
                    //处理错误。。。。
                    System.out.println("Error Response: " + response.getStatusLine().toString());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
