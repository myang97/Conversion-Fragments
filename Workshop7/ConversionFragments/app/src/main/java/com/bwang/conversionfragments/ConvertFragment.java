package com.bwang.conversionfragments;

import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by brian on 2/23/16.
 */
public class ConvertFragment extends Fragment implements View.OnClickListener, Animation.AnimationListener {

    public static final String KEY_TITLE = "title";
    public static final String KEY_RATES = "rates";
    public static final String KEY_NAMES = "names";
    public static final String KEY_UNIT = "unit";

    private EditText amountEditText;
    private TextView titleTextView;
    private Button convertButton;
    private ListView conversionListView;
    private TextSwitcher amountTextSwitcher;

    ArrayAdapter<String> adapter;
    ArrayList<String> conversions;

    String currentUnit;

    String[] names;
    float[] rates;
    double baseAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convert, container, false);

        amountEditText = (EditText) view.findViewById(R.id.amount_edit_text);
        titleTextView = (TextView) view.findViewById(R.id.title);
        convertButton = (Button) view.findViewById(R.id.convert_button);
        conversionListView = (ListView) view.findViewById(R.id.conversion_list);
        amountTextSwitcher = (TextSwitcher) view.findViewById(R.id.amount_text_switcher);

        // TODO 4. Set up our XML animation.
        // TODO 5. Set up the TextSwitcher to use our new animation and set an animation listener.
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        animation.setAnimationListener(this);
        amountTextSwitcher.setInAnimation(animation);
        amountTextSwitcher.setOutAnimation(animation);



        Bundle args = getArguments();

        titleTextView.setText(args.getString(KEY_TITLE));
        names = getResources().getStringArray(args.getInt(KEY_NAMES));
        rates = getRates(args.getInt(KEY_RATES));
        currentUnit = args.getString(KEY_UNIT);

        conversions = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item, R.id.list_item_text, conversions);
        conversionListView.setAdapter(adapter);

        convertButton.setOnClickListener(this);

        return view;
    }

    private float[] getRates(int ratesId) {
        TypedArray typedRates = getResources().obtainTypedArray(ratesId);

        float[] lrates = new float[typedRates.length()];
        for (int k = 0; k < lrates.length; k++)
            lrates[k] = typedRates.getFloat(k, 0);

        typedRates.recycle();

        return lrates;
    }

    @Override
    public void onClick(View v) {
        String amountString = amountEditText.getText().toString();
        if (amountString.isEmpty())
            return;

        amountEditText.setText(null);
        baseAmount = Double.parseDouble(amountString);
        amountTextSwitcher.setText(String.format("%.2f %s equals:", baseAmount, currentUnit));
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    // TODO 6. Shunt some code from onClick into this method to make our animation make a little more sense.
    @Override
    public void onAnimationEnd(Animation animation) {
        conversions.clear();
        for (int k = 0; k < names.length; k++)
            conversions.add(String.format("%.2f %s", baseAmount * rates[k], names[k]));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
