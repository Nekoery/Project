package pl.hypeapp.endoscope.util;

import android.content.SharedPreferences;

import net.majorkernelpanic.streaming.rtsp.RtspServer;

public class SettingsPreferencesUtil {
    private static final String SHARED_PREF_DEFAULT_PORT = "8086";
    private static final String SHARED_PREF_RESOLUTION = "resolution";
    private static final String SHARED_PREF_VIDEO_ENCODER = "video_encoder";
    private static final String SHARED_PREF_IS_AUDIO_STREAM = "is_audio_stream";
    private static final String SHARED_PREF_IS_FIRST_RUN = "isFirstRun";
    private static final String SHARED_PREF_HAVE_PASSWORD = "havePassword";
    private static final String SHARED_PREF_ACTIVE_ENGINE = "activeEngine";
    private static final String SHARED_PREF_DEFAULT_PASSWORD = "password";
    private static final int SHARED_PREF_DEFAULT_RESOLUTION = 3;
    private static final int SHARED_PREF_DEFAULT_VIDEO_ENCODER = 0;
    private SharedPreferences settingsPreferences;

    public SettingsPreferencesUtil(SharedPreferences sharedPreferences) {
        this.settingsPreferences = sharedPreferences;
    }

    public String loadPortPreference() {
        return settingsPreferences.getString(RtspServer.KEY_PORT, SHARED_PREF_DEFAULT_PORT);
    }

    public int loadResolutionPreference() {
        return settingsPreferences.getInt(SHARED_PREF_RESOLUTION, SHARED_PREF_DEFAULT_RESOLUTION);
    }

    public int loadVideoEncoderPreference() {
        return settingsPreferences.getInt(SHARED_PREF_VIDEO_ENCODER, SHARED_PREF_DEFAULT_VIDEO_ENCODER);
    }

    public boolean loadAudioPreference() {
        return settingsPreferences.getBoolean(SHARED_PREF_IS_AUDIO_STREAM, false);
    }

    public boolean loadIsFirstRunPreference() {
        return settingsPreferences.getBoolean(SHARED_PREF_IS_FIRST_RUN, true);
    }

    public Boolean loadHavePasswordPreference(){
        return settingsPreferences.getBoolean(SHARED_PREF_HAVE_PASSWORD,false);
    }

    public Boolean loadactiveEngine(){
        return settingsPreferences.getBoolean(SHARED_PREF_ACTIVE_ENGINE,false);
    }

    public String loadPassword(){
        return settingsPreferences.getString("SHARED_PREF_PASSWORD",SHARED_PREF_DEFAULT_PASSWORD);
    }

    public void savePortPreferencePreference(String port) {
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putString(RtspServer.KEY_PORT, String.valueOf(port));
        editor.apply();
    }

    public void saveAudioStreamPreference(boolean isAudioStream) {
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putBoolean(SHARED_PREF_IS_AUDIO_STREAM, isAudioStream);
        editor.apply();
    }

    public void saveVideoEncoderPreference(int selectedVideoEncoderIndex) {
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putInt(SHARED_PREF_VIDEO_ENCODER, selectedVideoEncoderIndex);
        editor.apply();
    }

    public void saveResolutionPreference(int selectedResolutionIndex) {
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putInt(SHARED_PREF_RESOLUTION, selectedResolutionIndex);
        editor.apply();
    }

    public void saveIsFirstRunPreference(boolean isFirstRun) {
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putBoolean(SHARED_PREF_IS_FIRST_RUN, isFirstRun);
        editor.apply();
    }

    public void saveHavePasswordPreference(boolean havePassword){
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putBoolean(SHARED_PREF_HAVE_PASSWORD,havePassword);
        editor.apply();
    }

    public void saveActiveEnginePreference(Boolean activeEngine){
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putBoolean(SHARED_PREF_ACTIVE_ENGINE,activeEngine);
        editor.apply();
    }

    public void savePasswordPreference(String password){
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putString("SHARED_PREF_PASSWORD",password);
        editor.apply();
    }
}
