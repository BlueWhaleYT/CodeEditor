package com.bluewhaleyt.codeeditor.fragments.preferences.editor;

import android.os.Bundle;

import com.bluewhaleyt.codeeditor.R;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;

public class EditorFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_editor, rootKey);
        init();
    }

    private void init() {

        try {

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }

}