package elsaghier.com.sendbird_learn;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.com.sendbird_learn.Adapter.ChatRoomsAdapter;
import elsaghier.com.sendbird_learn.Models.ChatRoomModel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chat_room_recycler)
    RecyclerView chatRoomRecycler;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    ChatRoomsAdapter adapter ;
    ArrayList<ChatRoomModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new ChatRoomsAdapter(this, list);

        chatRoomRecycler.setLayoutManager(new LinearLayoutManager(this));
        chatRoomRecycler.setHasFixedSize(true);
        chatRoomRecycler.setItemAnimator(new DefaultItemAnimator());
        chatRoomRecycler.setAdapter(adapter);
    }
}
