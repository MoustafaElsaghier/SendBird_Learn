package elsaghier.com.sendbird_learn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_login_user_id)
    TextInputEditText mUserIdEditText;
    @BindView(R.id.edit_text_login_user_nickname)
    TextInputEditText mUserNicknameEditText;
    @BindView(R.id.button_login)
    Button mConnectButton;
    private SharedPreferences mPrefs;

    final static String APP_ID = "9DA1B1F4-0BE6-4DA8-82C5-2E81DAB56F23"; // Sample App

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPrefs = getSharedPreferences("label", 0);

        String savedUserID = mPrefs.getString("userId", "");
        mUserIdEditText.setText(savedUserID);

        SendBird.init(APP_ID, this.getApplicationContext());

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mUserIdEditText.getText().toString();
                userId = userId.replaceAll("\\s", "");

                String userNickname = mUserNicknameEditText.getText().toString();
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("userId", userId);
                mEditor.putString("userNickName", userNickname);
                mEditor.apply();
                connectToSendBird(userId, userNickname);
            }
        });
    }

    private void connectToSendBird(final String userId, final String userNickname) {
        mConnectButton.setEnabled(false);

        SendBird.connect(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    mConnectButton.setEnabled(true);
                    return;
                }

                // Update the user's nickname
                updateCurrentUserInfo(userNickname);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("userID", userId);
                intent.putExtra("channelType", Constants.groupChannelType);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Updates the user's nickname.
     *
     * @param userNickname The new nickname of the user.
     */
    private void updateCurrentUserInfo(String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(LoginActivity.this, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
