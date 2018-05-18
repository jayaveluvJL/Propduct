package com.yarolegovich.slidingrootnav.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yarolegovich.slidingrootnav.sample.R;

/**
 * Created by jayavelu on 15/05/18.
 */

public class HelpFragment extends android.support.v4.app.Fragment {

    private static final String EXTRA_TEXT = "text";

    public static HelpFragment createFor(String text) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final String text = getArguments().getString(EXTRA_TEXT);
        TextView textView = view.findViewById(R.id.text);
        textView.setText(text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
