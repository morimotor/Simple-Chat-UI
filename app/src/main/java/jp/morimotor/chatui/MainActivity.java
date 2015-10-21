package jp.morimotor.chatui;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ListView mListView;
    private Button mButton;
    private EditText mEditText;
    private ArrayList list;
    CustomAdapter adapter;

    private class itemData{
        private int image_id;
        private String userName;
        private String text;
        private boolean isUser;

        itemData(int image_id, String userName, String text, boolean isUser){
            this.image_id = image_id;
            this.userName = userName;
            this.text = text;
            this.isUser = isUser;

        }
        int getImage_id(){
            return this.image_id;
        }
        String getUserName(){
            return this.userName;
        }
        String getText(){
            return this.text;
        }
        boolean getIsUser(){
            return this.isUser;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);
        mEditText = (EditText)findViewById(R.id.editText);
        mButton = (Button)findViewById(R.id.button);

        list = new ArrayList<>();
        adapter = new CustomAdapter(this);

        Log.d(TAG, "aaa");
        String json = getSharedPreferences("data", MODE_PRIVATE).getString("data", "null");
        if(!json.equals("null")){
            list = fromJSON(json);
            Log.d(TAG, list.toString());
        }
        Log.d(TAG, "bbb");

        adapter.setDataList(list);
        mListView.setAdapter(adapter);

        Log.d(TAG, "ccc");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemData data1 = new itemData(R.drawable.ic_account_circle_black_48dp, "User", mEditText.getText().toString(), true);
                itemData data2 = new itemData(R.drawable.ic_android_black_48dp, "Computer", "hogehoge", false);
                list.add(data1);
                list.add(data2);

                mEditText.setText("");

                adapter.notifyDataSetChanged();

                String json = toJSON(list);
                getSharedPreferences("data", MODE_PRIVATE).edit().putString("data", json).apply();
                Log.d(TAG, json);


            }
        });

    }

    private String toJSON(ArrayList<itemData> items){
        Gson gson = new Gson();

        return gson.toJson(items);
    }

    private ArrayList fromJSON(String json){
        Gson gson = new Gson();

        return gson.fromJson(json, new TypeToken<List<itemData>>(){}.getType());
    }


    public class CustomAdapter extends BaseAdapter {
        Context context;
        ArrayList<itemData> list;
        LayoutInflater layoutInflater = null;

        public CustomAdapter(Context context) {
            this.context = context;
            this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setDataList(ArrayList<itemData> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(list.get(position).getIsUser()){

                convertView = layoutInflater.inflate(R.layout.list_item1, parent, false);

                ((ImageView)convertView.findViewById(R.id.imageView1)).setImageResource(list.get(position).getImage_id());
                ((TextView)convertView.findViewById(R.id.text1)).setText(list.get(position).getText());
                ((TextView)convertView.findViewById(R.id.username1)).setText(list.get(position).getUserName());
            }
            else{

                convertView = layoutInflater.inflate(R.layout.list_item2, parent, false);

                ((ImageView)convertView.findViewById(R.id.imageView2)).setImageResource(list.get(position).getImage_id());
                ((TextView)convertView.findViewById(R.id.text2)).setText(list.get(position).getText());
                ((TextView)convertView.findViewById(R.id.username2)).setText(list.get(position).getUserName());
            }


            return convertView;
        }
    }

}
