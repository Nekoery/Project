package pl.hypeapp.endoscope.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.dialog.myDialog.MyPwdInputDialog;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.presenter.AESPresenter;
import pl.hypeapp.endoscope.presenter.SettingsPresenter;
import pl.hypeapp.endoscope.util.SettingsPreferencesUtil;
import pl.hypeapp.endoscope.view.SettingsView;

public class SettingsActivity extends TiActivity<SettingsPresenter, SettingsView> implements SettingsView {
    private static final String OPTION_RESOLUTION = "resolution";
    private static final String OPTION_VIDEO_ENCODER = "video_encoder";
    @BindView(R.id.port_value) TextView portValue;
    @BindView(R.id.vide_encoder_option) TextView videoEncoder;
    @BindView(R.id.resolution_option) TextView resolution;
    @BindView(R.id.stream_sound_option) TextView audioStream;
    @BindView(R.id.password_text) TextView passwordText;

    @NonNull
    @Override
    public SettingsPresenter providePresenter() {
        final SettingsPreferencesUtil settingsPreferencesUtil = new SettingsPreferencesUtil(PreferenceManager.getDefaultSharedPreferences(this));
        return new SettingsPresenter(settingsPreferencesUtil);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @Override
    public void showSelectItemDialog(CharSequence[] items, String title, int selectedItem, final String option) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setSingleChoiceItems(items, selectedItem, null)
                .setPositiveButton(R.string.button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedOption) {
                        int selectedPosition = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
                        Log.e("Option", " " + selectedPosition);
                        if (option.equals(OPTION_VIDEO_ENCODER)) {
                            getPresenter().onChangeVideoEncoder(selectedPosition);
                        } else if (option.equals(OPTION_RESOLUTION)) {
                            getPresenter().onChangeResolution(selectedPosition);
                        }
                    }
                })
                .show();
    }

    @Override
    @OnClick(R.id.resolution_layout)
    public void onChangeResolution() {
        CharSequence[] resolutions = getResources().getStringArray(R.array.resolutions);
        String dialogTitle = getString(R.string.resolution_dialog_title);
        getPresenter().showChangeResolutionDialog(resolutions, dialogTitle);
    }

    @Override
    @OnClick(R.id.video_encoder_layout)
    public void onChangeVideoEncoder() {
        CharSequence[] videoEncoders = getResources().getStringArray(R.array.video_encoders);
        String dialogTitle = getString(R.string.set_video_encoder_title);
        getPresenter().showChangeVideoEncoderDialog(videoEncoders, dialogTitle);
    }

    @Override
    @OnClick(R.id.stream_sound_layout)
    public void onChangeAudioStream() {
        getPresenter().onChangeAudioStream();
    }

    @Override
    public void setResolution(int item) {
        resolution.setText(getResources().getTextArray(R.array.resolutions)[item]);
    }

    @Override
    public void setVideoEncoder(int item) {
        videoEncoder.setText(getResources().getTextArray(R.array.video_encoders)[item]);
    }

    @Override
    public void setAudioStream(boolean isAudioStream) {
        if (isAudioStream) {
            audioStream.setText(R.string.enabled);
        } else {
            audioStream.setText(R.string.disabled);
        }
    }

    @Override
    @OnClick(R.id.port_layout)
    public void onChangePortClick() {
        showChangePortDialog();
    }

    private void showChangePortDialog() {
        final EditText editTextPort = new EditText(this);
        editTextPort.setInputType(InputType.TYPE_CLASS_NUMBER);
        int maxLength = 4;
        editTextPort.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.set_port))
                .setView(editTextPort)
                .setPositiveButton(getString(R.string.dialog_set), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String port = editTextPort.getText().toString();
                        getPresenter().onChangePort(port);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    @Override
    public void changePortError() {
        Toast.makeText(SettingsActivity.this, R.string.port_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changePortSuccessful() {
        Toast.makeText(SettingsActivity.this, R.string.port_changed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPort(String port) {
        portValue.setText(port);
    }

    @Override
    @OnClick(R.id.password_layout)
    public void onChangePassword(){
        final MyPwdInputDialog pwdDialog = new MyPwdInputDialog(this)
                .builder()
                .setTitle("请输入原密码");
        final MyPwdInputDialog pwdDialog2 = new MyPwdInputDialog(this)
                .builder()
                .setTitle("请输入新密码");
        pwdDialog2.setPasswordListener(new MyPwdInputDialog.OnPasswordResultListener() {
            @Override
            public void onPasswordResult(String password) {
                    getPresenter().ChangePassword(password);
                    pwdDialog2.dismiss();
            }
        });
        pwdDialog.setPasswordListener(new MyPwdInputDialog.OnPasswordResultListener() {
            @Override
            public void onPasswordResult(String password) {
                if(getPresenter().VerifyPassword(password)){
                        pwdDialog.dismiss();
                        pwdDialog2.show();
                }else {
                    Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_LONG).show();
                    pwdDialog.dismiss();
                }

            }
        });
        pwdDialog.show();
    }

    public void changePasswordDone(){
        Toast.makeText(getApplicationContext(),"修改密码成功",Toast.LENGTH_LONG).show();
    }

    @Override
    @OnClick(R.id.album_layout)
    public void OpenAlbum() {
        Intent intent = new Intent(SettingsActivity.this,AlbumActivity.class);
        startActivity(intent);
    }

}
