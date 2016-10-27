package com.sunsunsoft.shutaro.testrealm;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnTouchListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import java.util.LinkedList;
import java.util.Random;


public class MyFragment extends Fragment implements OnTouchListener, OnClickListener, OnItemClickListener {
    enum TestMode {
        Select,
        SelectAll,
        Add1,
        Add2,
        Update,
        Update2,
        Delete,
        DeleteAll
    }

    private final static String BACKGROUND_COLOR = "background_color";
    public static final String[] modeItems = new String[]{
            "Select",
            "Select All",
            "Add1",
            "Add2",
            "Update",
            "Update2",
            "Delete",
            "DeleteAll"
    };

    TextView textView;
    Button button;
    ListView listView;

    UserDAO mModel;

    Random mRand = new Random();

    public static MyFragment newInstance(@ColorRes int IdRes) {
        MyFragment frag = new MyFragment();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOR, IdRes);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page1_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(this);

        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        // Adapterの作成
        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, modeItems);
        // Adapterの設定
        listView.setAdapter(adapter);

        textView = (TextView)view.findViewById(R.id.textView);

        mModel = new UserDAO(getActivity());

        return view;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                textView.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView) parent;
        Log.v("myLog", (String) lv.getItemAtPosition(position));

        TestMode[] values = TestMode.values();
        TestMode mode = values[position];

        testQuery(mode);
    }


    private void testQuery(TestMode mode) {

        switch (mode) {
            case Select:
                mModel.select1(10);
                break;
            case SelectAll: {
                Log.d("page1", "select all");
                textView.setText("");
                String[] results = mModel.selectAll();
                if (results != null) {
                    for (String result : results) {
                        textView.append(result + "\n");
                    }
                }
            }
                break;
            case Add1:
                mModel.add1("hoge", 1);
                break;
            case Add2: {
                LinkedList<User> userList = new LinkedList();
                User user;
                for (int i=0; i<10; i++) {
                    int rand = mRand.nextInt(100);
                    user = new User();
                    user.setName("hoge" + rand);
                    user.setAge(rand);
                    userList.add(user);
                }
                mModel.add2(userList);
            }
                break;
            case Update: {
                User user = mModel.selectOne();
                if (user != null) {
                    mModel.updateOne(user.getId(), user.getName(), 101);
                }
            }
                break;
            case Update2: {
                User user = mModel.selectOne();
                if (user != null) {
                    mModel.updateAll(user.getAge(), user.getName(), 102);
                }
            }
                break;
            case Delete: {
                User user = mModel.selectOne();
                if (user != null) {
                    mModel.deleteOne(user.getId());
                }
            }
                break;
            case DeleteAll: {
                mModel.deleteAll();
            }
                break;
        }
    }

    /**
     * タッチイベント
     * @param v
     * @param e
     * @return
     */
    public boolean onTouch(View v, MotionEvent e) {
        return true;
    }

}
