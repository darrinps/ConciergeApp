package com.aa.consierge;

import static com.aa.android.common.AAIntent.EXTRA_QUESTION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aa.android.common.WearActivity;
import com.aa.android.common.data.SharedDataEvent;
import com.aa.android.common.data.SharedQuestion;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;

public class MainActivity extends WearActivity {

    private SharedQuestion mQuestion;

    private TextView mQuestionText;
    private ImageButton mYesButton;
    private ImageButton mNoButton;

    public static void start(Context context, SharedQuestion question) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_QUESTION, question);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Bundle extras = savedInstanceState;
        if (extras == null) {
            extras = getIntent().getExtras();
        }
        initData(extras);
        refreshUi();
    }

    private void initData(Bundle extras) {
        if (extras != null) {
            mQuestion = extras.getParcelable(EXTRA_QUESTION);
        }
    }

    private void initView() {
        mQuestionText = (TextView) findViewById(R.id.question_text);
        mYesButton = (ImageButton) findViewById(R.id.yes_button);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onYesClick();
            }
        });
        mNoButton = (ImageButton) findViewById(R.id.no_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoClick();
            }
        });
    }

    private void refreshUi() {
        if (mQuestion != null) {
            mQuestionText.setText(mQuestion.getText());
        }
    }

    public void onYesClick() {
        // Send back
    }

    public void onNoClick() {
        // Send back
    }

    @Override
    public boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent) {
        return false;
    }

    @Override
    public boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent) {
        return false;
    }
}
