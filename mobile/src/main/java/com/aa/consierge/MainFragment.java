package com.aa.consierge;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.android.common.WearActivity;
import com.aa.android.common.data.SharedQuestion;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;

import java.lang.ref.WeakReference;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private EditText mQuestion;
    private Button mSubmit;
    private TextView mAnswer;

    private WearActivity mActivity;

    public MainFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActivity = (WearActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("activity must extend from WearActiviy");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
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
                Toast.makeText(getActivity(), "sending...", Toast.LENGTH_SHORT).show();

                WatsonFragment fragment = new WatsonFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                fragment.show(ft, "Watson");
                
                sendQuestion(new WeakReference<>(MainFragment.this), mQuestion.getText().toString());
            }
        });

        return rootView;
    }

    private static void sendQuestion(final WeakReference<MainFragment> fragmentRef, String question) {
        WearActivity wearActivity = fragmentRef.get().mActivity;
        if (wearActivity != null) {
            SharedQuestion sharedQuestion = new SharedQuestion(question, null, null);
            wearActivity.getWearManager()
                    .putDataItem(sharedQuestion.asPutDataRequest())
                    .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            MainFragment fragment = fragmentRef.get();
                            WearActivity activity;
                            if (fragment != null && (activity = fragment.mActivity) != null) {
                                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
