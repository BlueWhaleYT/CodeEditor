package com.bluewhaleyt.codeeditor.fragments.preferences;

import android.os.Bundle;

import com.bluewhaleyt.codeeditor.R;
import com.bluewhaleyt.codeeditor.fragments.preferences.application.ApplicationFragment;
import com.bluewhaleyt.codeeditor.fragments.preferences.editor.EditorFragment;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;

public class SettingsFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_main, rootKey);
        init();
    }

    private void init() {
        try {

            var componentBtnApplication = findPreference("component_btn_application");
            var componentBtnEditor = findPreference("component_btn_editor");

            componentBtnApplication.setOnPreferenceClickListener(preference -> {
                IntentUtil.intentFragment(requireActivity(), new ApplicationFragment());
                return true;
            });

            componentBtnEditor.setOnPreferenceClickListener(preference -> {
                IntentUtil.intentFragment(requireActivity(), new EditorFragment());
                return true;
            });

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(requireActivity(), e.getMessage(), e.toString());
        }

    }
}