package com.bluewhaleyt.codeeditor.fragments.preferences.application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bluewhaleyt.codeeditor.R;
import com.bluewhaleyt.codeeditor.activities.MainActivity;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;

public class ApplicationFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_application, rootKey);
        init();
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
        resultLauncherChooseFile.launch(intent);
    }

    private void saveSelectedFile(Intent data) {
        requireActivity().setResult(1000);
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("pref_editor_default_content", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("pref_editor_default_content", data.getData().toString());
        editor.commit();
    }

    ActivityResultLauncher<Intent> resultLauncherChooseFile = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                    var data = result.getData();
                    var prefEditorDefaultContent = findPreference("pref_editor_default_content");
                    prefEditorDefaultContent.setSummary(MainActivity.getFilePathFromUri(requireActivity(), Uri.parse(data.getData().toString())));
                    saveSelectedFile(data);
                }
            }
    );


}