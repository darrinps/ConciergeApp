package com.aa.consierge;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by layne on 10/24/14.
 */
public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private EditText mQuestion;
    private Button mSubmit;
    private TextView mAnswer;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mQuestion = (EditText) rootView.findViewById(R.id.question);
        mSubmit = (Button) rootView.findViewById(R.id.submit);
        mAnswer = (TextView) rootView.findViewById(R.id.answer);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: send question to wearable
                Log.d(TAG, "sending...");
                Toast.makeText(getActivity(), "sending...", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
