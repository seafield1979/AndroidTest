package com.example.shutaro.testlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandListActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_list);

//        ArrayList<HashMap<String, String>> hash1 =
//                new ArrayList<HashMap<String, String>>{{"1","2"},{"3","4"}};

        String[][] list1 = new String[][]{{"1","2"},{"3","4"}};

        // groupData グループのリスト
        ArrayList<HashMap<String, String>> groupData = new ArrayList<HashMap<String, String>>();
        // childData 全グループの子要素を追加するリスト
        ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<ArrayList<HashMap<String, String>>>();

        // グループ(トップレベルの項目）を作成
        HashMap<String, String> groupA = new HashMap<String, String>();
        groupA.put("group", "果物");
        HashMap<String, String> groupB = new HashMap<String, String>();
        groupB.put("group", "野菜");

        groupData.add(groupA);
        groupData.add(groupB);

        // HashMap childListA
        ArrayList<HashMap<String, String>> childListA = new ArrayList<HashMap<String, String>>();


        HashMap<String, String> childAA = new HashMap<String, String>();
        childAA.put("group", "果物");
        childAA.put("name", "りんご");
        HashMap<String, String> childAB = new HashMap<String, String>();
        childAB.put("group", "果物");
        childAB.put("name", "ぶどう");
        HashMap<String, String> childAC = new HashMap<String, String>();
        childAC.put("group", "果物");
        childAC.put("name", "みかん");

        childListA.add(childAA);
        childListA.add(childAB);
        childListA.add(childAC);

        childData.add(childListA);

        ArrayList<HashMap<String, String>> childListB = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> childBA = new HashMap<String, String>();
        childBA.put("group", "野菜");
        childBA.put("name", "ピーマン");
        HashMap<String, String> childBB = new HashMap<String, String>();
        childBB.put("group", "野菜");
        childBB.put("name", "だいこん");

        childListB.add(childBA);
        childListB.add(childBB);

        childData.add(childListB);

        //
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                getApplicationContext(),
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { "group" },
                new int[] { android.R.id.text1 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { "name", "group" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        //
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        listView.setAdapter(adapter);
    }
}
