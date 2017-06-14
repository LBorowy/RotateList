package pl.lborowy.rotatelist;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private String[] androidVersions;
    private ArrayList<String> stringList;
    private Random random;
    private ArrayAdapter adapter;
    private Button button;
    private TextView emptyListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        emptyListTextView = (TextView) findViewById(R.id.emptyListText);

        listView = (ListView) findViewById(R.id.listViev);
        androidVersions = getResources().getStringArray(R.array.wersje_androida);
//        String[] stringList = new String[] {"jeden", "dwa"};
        stringList = new ArrayList<>(); // bd trzymane wersje androida
        random = new Random();
        initAdapter();
        refreshList();
    }

    private void initAdapter() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        listView.setAdapter(adapter);
    }

    public void randomVersion(View view) {
        String randomString = getRandomVersion();
        stringList.add(randomString);
        refreshList();
    }

    private void refreshList() {
        adapter.notifyDataSetChanged();
        if (adapter.getCount() == 0) {
            showEmptyListText();
        } else {
            showListView();
        }
    }

    private void showEmptyListText() {
        emptyListTextView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    private void showListView() {
        emptyListTextView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    private String getRandomVersion() {
        int randomIndex = random.nextInt(androidVersions.length);
        return androidVersions[randomIndex];
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("lista", stringList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { // wczytywanie danych
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<String> savedList = savedInstanceState.getStringArrayList("lista");
        if (savedList != null) {
            stringList.addAll(savedList);
        }
        refreshList();
    }
}
