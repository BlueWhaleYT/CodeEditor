package com.bluewhaleyt.codeeditor.tools;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.bluewhaleyt.codeeditor.App;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.filemanagement.FileUtil;

public class PreferencesManager {

    public static boolean isEditorSyntaxHighlightingEnable() {
        return getPrefs().getBoolean("pref_editor_syntax_highlighting_enable", true);
    }

    public static String getEditorFontSize() {
        return getPrefs().getString("pref_editor_font_size", "18");
    }

    public static boolean isEditorFontLigaturesEnable() {
        return getPrefs().getBoolean("pref_editor_font_ligatures_enable", false);
    }

    public static boolean isEditorPinchZoomEnable() {
        return getPrefs().getBoolean("pref_editor_pinch_zoom_enable", true);
    }

    public static boolean isEditorWordWrapEnable() {
        return getPrefs().getBoolean("pref_editor_word_wrap_enable", false);
    }

    public static boolean isEditorAutoCompletionEnable() {
        return getPrefs().getBoolean("pref_editor_auto_completion_enable", false);
    }

    public static SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }

}
