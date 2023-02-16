package com.bluewhaleyt.codeeditor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bluewhaleyt.codeeditor.R;
import com.bluewhaleyt.codeeditor.databinding.ActivityMainBinding;
import com.bluewhaleyt.codeeditor.textmate.syntaxhighlight.SyntaxHighlightUtil;
import com.bluewhaleyt.codeeditor.tools.PreferencesManager;
import com.bluewhaleyt.codeeditor.utils.Constants;
import com.bluewhaleyt.codeeditor.utils.EditorUtil;
import com.bluewhaleyt.codeeditor.utils.UriResolver;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.filemanagement.FileUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private SyntaxHighlightUtil syntaxHighlight;

    private SharedPreferences sharedPrefs;
    private String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                IntentUtil.intent(this, SettingsActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        try {
            getDefaultContent();
            getSupportActionBar().setSubtitle(getFilePathFromUri(this, Uri.parse(file)));

            setupEditor();
            setupSyntaxHighlighting();
            setupSettings();
            setupDefaultContent();

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(this, e.getMessage(), e.toString());
        }
    }

    private void setupEditor() {
        var font = Typeface.createFromAsset(getAssets(), Constants.CODE_FONT);
        binding.editor.setTypefaceText(font);
        binding.editor.setTypefaceLineNumber(font);
    }

    private void setupSyntaxHighlighting() {
        String themeDark = "material_palenight.json";
        String themeLight = "material_lighter.json";

        String[] themes = {themeLight, themeDark};

        try {
            syntaxHighlight = new SyntaxHighlightUtil();
            syntaxHighlight.setLanguageBase("languages.json");
            syntaxHighlight.setLanguageDirectory(Constants.LANGUAGE_DIR);
            syntaxHighlight.setThemeDirectory(Constants.THEME_DIR);
            syntaxHighlight.setThemes(themes);

            var theme = CommonUtil.isInDarkMode(this) ? themeDark : themeLight;
            syntaxHighlight.setTheme(theme);

            getDefaultContent();
            syntaxHighlight.setup(this, binding.editor, getFilePathFromUri(this, Uri.parse(file)));
        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(this, e.getMessage(), e.toString());
        }

    }

    private void setupSettings() {
        binding.editor.setTextSize(Float.parseFloat(PreferencesManager.getEditorFontSize()));
        binding.editor.setLigatureEnabled(PreferencesManager.getEditorFontLigaturesEnable());

        binding.editor.setScalable(PreferencesManager.getEditorPinchZoomEnable());
        binding.editor.setWordwrap(PreferencesManager.getEditorWordWrapEnable());
    }

    private void getDefaultContent() {
        sharedPrefs = getSharedPreferences("pref_editor_default_content", Context.MODE_PRIVATE);
        file = sharedPrefs.getString("pref_editor_default_content", "");
    }

    private void setupDefaultContent() {
        getDefaultContent();
        binding.editor.setText(FileUtil.readFile(getFilePathFromUri(this, Uri.parse(file))));
    }

//    private String readFile(Uri uri) throws IOException {
//        InputStream in = getContentResolver().openInputStream(uri);
//        BufferedReader r = new BufferedReader(new InputStreamReader(in));
//        StringBuilder total = new StringBuilder();
//        for (String line; (line = r.readLine()) != null; ) {
//            total.append(line).append('\n');
//        }
//        return total.toString();
//    }

    public static String getFilePathFromUri(Context context, Uri uri) {
//        return UriResolver.getPathFromUri(context, getDocumentUriFromUri(uri));
        return UriResolver.getPathFromUri(context, uri);
    }

    private static Uri getDocumentUriFromUri(Uri uri) {
        return DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
    }
}