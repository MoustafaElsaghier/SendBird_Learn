package elsaghier.com.sendbird_learn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.UserDictionary.Words.APP_ID;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_login_user_id)
    TextInputEditText mUserIdEditText;
    @BindView(R.id.edit_text_login_user_nickname)
    TextInputEditText mUserNicknameEditText;
    @BindView(R.id.text_input_login_user_nickname)
    Button mConnectButton;
    private SharedPreferences mPrefs;

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
                mEditor.putString("userId", userId).commit();
                mEditor.putString("userNickName", userNickname).commit();
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

                    // Show login failure snackbar
                    mConnectButton.setEnabled(true);
                    return;
                }

                // Update the user's nickname
                updateCurrentUserInfo(userNickname);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("userID", userId);
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
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

            }
        });
    }
}
