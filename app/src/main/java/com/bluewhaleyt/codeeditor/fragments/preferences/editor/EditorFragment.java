package com.bluewhaleyt.codeeditor.fragments.preferences.editor;

import android.os.Bundle;

import com.bluewhaleyt.codeeditor.R;
import com.bluewhaleyt.codeeditor.fragments.preferences.application.ApplicationFragment;
import com.bluewhaleyt.common.IntentUtil;
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

            var componentBtnEditorTheme = findPreference("component_btn_editor_theme");

            componentBtnEditorTheme.setOnPreferenceClickListener(preference -> {
                IntentUtil.intentFragment(requireActivity(), new ThemeFragment());
                return true;
            });

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }

}