package elsaghier.com.sendbird_learn;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_login_user_id)
    TextInputEditText editTextLoginUserId;
    @BindView(R.id.text_input_login_user_id)
    TextInputLayout textInputLoginUserId;
    @BindView(R.id.edit_text_login_user_nickname)
    TextInputEditText editTextLoginUserNickname;
    @BindView(R.id.text_input_login_user_nickname)
    TextInputLayout textInputLoginUserNickname;
    @BindView(R.id.button_login)
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }
}
