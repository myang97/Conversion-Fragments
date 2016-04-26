package com.bwang.conversionfragments;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView drawer;
    private DrawerLayout drawerLayout;

    ArrayAdapter<String> drawerAdapter;
    String[] names = {
            "Length (ft)",
            "Weight (lb)",
            "Speed (mph)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (ListView) findViewById(R.id.drawer_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_container);

        drawerAdapter = new ArrayAdapter<>(this,
                R.layout.list_item, R.id.list_item_text, names);
        drawer.setAdapter(drawerAdapter);
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchFragment(names[position]);
            }
        });

        switchFragment(names[0]);
    }

    private void switchFragment(String name) {
        ConvertFragment fragment = new ConvertFragment();
        Bundle args = new Bundle();
        args.putString(ConvertFragment.KEY_TITLE, name);
        switch (name) {
            case "Length (ft)":
                args.putInt(ConvertFragment.KEY_NAMES, R.array.length_names);
                args.putInt(ConvertFragment.KEY_RATES, R.array.length_rates);
                args.putString(ConvertFragment.KEY_UNIT, "feet");
                break;
            case "Weight (lb)":
                args.putInt(ConvertFragment.KEY_NAMES, R.array.weight_names);
                args.putInt(ConvertFragment.KEY_RATES, R.array.weight_rates);
                args.putString(ConvertFragment.KEY_UNIT, "pounds");
                break;
            case "Speed (mph)":
                args.putInt(ConvertFragment.KEY_NAMES, R.array.speed_names);
                args.putInt(ConvertFragment.KEY_RATES, R.array.speed_rates);
                args.putString(ConvertFragment.KEY_UNIT, "miles per hour");
        }

        drawerLayout.closeDrawer(drawer);
        fragment.setArguments(args);

        // TODO 7. Set up another XML animation.
        // TODO 8. Set up the fragment manager to use this animation.
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .add(R.id.main_container, fragment).commit();
    }
}
