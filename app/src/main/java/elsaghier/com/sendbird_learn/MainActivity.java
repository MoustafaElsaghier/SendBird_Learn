package elsaghier.com.sendbird_learn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.OpenChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.com.sendbird_learn.Adapter.ChatRoomsAdapter;
import elsaghier.com.sendbird_learn.Models.ChatRoomModel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chat_room_recycler)
    RecyclerView chatRoomRecycler;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    ChatRoomsAdapter adapter;
    ArrayList<ChatRoomModel> list;
    private String USER_ID;
    private String CHANNEL_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        chatRoomRecycler.setLayoutManager(new LinearLayoutManager(this));
        chatRoomRecycler.setHasFixedSize(true);
        chatRoomRecycler.setItemAnimator(new DefaultItemAnimator());
        chatRoomRecycler.setAdapter(adapter);


        USER_ID = getIntent().getStringExtra("userID");
        CHANNEL_TYPE = getIntent().getStringExtra("channelType");
        init_sendbird();

    }

    @Override
    protected void onResume() {
        super.onResume();
        connect_sendbird();
        if (Constants.groupChannelType.equals(CHANNEL_TYPE)) {
            get_group_channels();
        } else {
            finish();
        }
    }

    protected void get_group_channels() {
        GroupChannelListQuery channelListQuery = GroupChannel.createMyGroupChannelListQuery();
        channelListQuery.setIncludeEmpty(true);
        channelListQuery.setLimit(100);
        channelListQuery.next(new GroupChannelListQuery.GroupChannelListQueryResultHandler() {
            @Override
            public void onResult(List<GroupChannel> list, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
                populate_group_channel_list(list);
                progressBar
                        .setVisibility(View.GONE);
            }
        });
    }

    protected void populate_group_channel_list(List<GroupChannel> list) {

        adapter = new ChatRoomsAdapter(this, list);
        chatRoomRecycler.setAdapter(adapter);
        chatRoomRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void connect_sendbird() {
        SendBird.connect(USER_ID, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });
    }

    protected void init_sendbird() {
        SendBird.init(LoginActivity.APP_ID, this);
    }
}