package com.bluewhaleyt.codeeditor.fragments.preferences.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.SwitchPreferenceCompat;

import com.bluewhaleyt.codeeditor.App;
import com.bluewhaleyt.codeeditor.R;
import com.bluewhaleyt.codeeditor.activities.MainActivity;
import com.bluewhaleyt.codeeditor.utils.Constants;
import com.bluewhaleyt.codeeditor.utils.UriResolver;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.filemanagement.FileUtil;

public class EditorFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_editor, rootKey);
        init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionUtil.setPermanentAccess(requireActivity(), data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case Constants.REQ_CODE_CHOOSE_DEFAULT_CONTENT:
                        saveSelectedFile(data);
                        var prefEditorDefaultContent = findPreference("pref_editor_default_content");
                        prefEditorDefaultContent.setSummary(MainActivity.getFilePathFromUri(requireActivity(), Uri.parse(data.getData().toString())));
                        break;
                }
            }
        }
    }

    private void init() {

        try {

            SharedPreferences sh = requireActivity().getSharedPreferences("pref_editor_default_content", Context.MODE_PRIVATE);
            var file = sh.getString("pref_editor_default_content", "");

            var prefEditorDefaultContent = findPreference("pref_editor_default_content");
            prefEditorDefaultContent.setOnPreferenceClickListener(preference -> {
                callSelectFile();
                return true;
            });
            prefEditorDefaultContent.setSummary(MainActivity.getFilePathFromUri(requireActivity(), Uri.parse(file)));

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }

    private void callSelectFile() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        startActivityForResult(intent, Constants.REQ_CODE_CHOOSE_DEFAULT_CONTENT);

    }

    private void saveSelectedFile(Intent data) {
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("pref_editor_default_content", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("pref_editor_default_content", data.getData().toString());
        editor.commit();
    }
}