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
import java.util.List;
import java.util.Random;


public class MyFragment extends Fragment implements OnTouchListener, OnClickListener, OnItemClickListener {
    enum TestMode {
        Select,
        SelectAll,
        SelectTest,
        SelectSorted,
        Add1,
        Add2,
        Insert1,
        Update,
        Update2,
        Delete,
        DeleteAll
    }
    private LinkedList<String> modeItems = new LinkedList<>();

    private final static String BACKGROUND_COLOR = "background_color";

    TextView textView;
    Button button;
    ListView listView;

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

        for (TestMode mode : TestMode.values()) {
            modeItems.add(mode.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page1_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(this);

        // ListView
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, modeItems);
        listView.setAdapter(adapter);

        textView = (TextView)view.findViewById(R.id.textView);

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
            case Select: {
                User user = RealmManager.getUserDao().selectOne(10);
                if (user != null) {
                    textView.setText("");
                    textView.append("name:" + user.getName() + " age:" + user.getAge() + "\n");
                }
            }
                break;
            case SelectAll: {
                selectAll();
            }
                break;
            case SelectTest: {
                List<User> users = RealmManager.getUserDao().selectTest();
                showUsers(users);
            }
                break;
            case SelectSorted: {
                textView.setText("");
                List<User> results = RealmManager.getUserDao().selectSorted();
                if (results != null) {
                    for (User user : results) {
                        textView.append("name:" + user.getName() + " age:" + user.getAge() + "\n");
                    }
                }
            }
                break;
            case Add1:
                // １つ追加
                int value = mRand.nextInt(1000);
                RealmManager.getUserDao().addOne("hoge" + value, value);
                selectAll();
                break;
            case Add2: {
                // 複数追加
                LinkedList<User> userList = new LinkedList();
                User user;
                for (int i=0; i<10; i++) {
                    int rand = mRand.nextInt(100);
                    user = new User();
                    user.setName("hoge" + rand);
                    user.setAge(rand);
                    userList.add(user);
                }
                RealmManager.getUserDao().addList(userList);
                selectAll();
            }
                break;
            case Insert1: {
                // １つ挿入
                int rand = mRand.nextInt(100);
                RealmManager.getUserDao().insertOne(0, "insert" + rand, rand);
            }
                break;
            case Update: {
                // １つ更新
                User user = RealmManager.getUserDao().selectOne();
                if (user != null) {
                    RealmManager.getUserDao().updateOne(user.getId(), user.getName(), 101);
                }
                selectAll();
            }
                break;
            case Update2: {
                // 複数更新
                User user = RealmManager.getUserDao().selectOne();
                if (user != null) {
                    RealmManager.getUserDao().updateAll(user.getAge(), user.getName(), 102);
                }
                selectAll();
            }
                break;
            case Delete: {
                User user = RealmManager.getUserDao().selectOne();
                if (user != null) {
                    RealmManager.getUserDao().deleteOne(user.getId());
                }
                selectAll();
            }
                break;
            case DeleteAll: {
                RealmManager.getUserDao().deleteAll();
                selectAll();
            }
                break;
        }
    }


    private void selectAll() {
        textView.setText("");
        List<User> results = RealmManager.getUserDao().selectAll();
        if (results != null) {
            for (User user : results) {
                textView.append("name:" + user.getName() + " age:" + user.getAge() + "\n");
            }
        }
    }

    private void showUsers( List<User> users) {
        textView.setText("");
        if (users == null) return;

        for (User user : users) {
            textView.append("name:" + user.getName() + " age:" + user.getAge() + "\n");
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
