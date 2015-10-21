package jp.morimotor.chatui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButton;
    private EditText mEditText;
    private ArrayList list;
    CustomAdapter adapter;

    private class itemData{
        private int image_id;
        private String text;
        private boolean isUser;

        itemData(int image_id, String text){
            this.image_id = image_id;
            this.text = text;
        }
        int getImage_id(){
            return this.image_id;
        }
        String getText(){
            return this.text;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);
        mEditText = (EditText)findViewById(R.id.editText);
        mButton = (Button)findViewById(R.id.button);

        list = new ArrayList<itemData>();
        adapter = new CustomAdapter(this);
        adapter.setDataList(list);
        mListView.setAdapter(adapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemData data1 = new itemData(R.drawable.ic_account_circle_black_48dp, "User:" + mEditText.getText().toString());
                itemData data2 = new itemData(R.drawable.ic_android_black_48dp, "Computer:" + "hogehoge");
                list.add(data1);
                list.add(data2);

                mEditText.setText("");


            }
        });

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
            convertView = layoutInflater.inflate(R.layout.list_item1, parent, false);

            ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(list.get(position).getImage_id());
            ((TextView)convertView.findViewById(R.id.textView)).setText(list.get(position).getText());

            return convertView;
        }
    }

}
