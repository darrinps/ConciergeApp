package com.aa.consierge;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class WatsonFragment extends DialogFragment {

//    TODO: Add this code to wherever this dialog will display
//    WatsonFragment fragment = new WatsonFragment();
//    FragmentTransaction ft = getFragmentManager().beginTransaction();
//    fragment.show(ft, "Watson");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.watson_result_card, container);
    }

}

