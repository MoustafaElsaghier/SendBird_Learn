package elsaghier.com.sendbird_learn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sendbird.android.GroupChannel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import elsaghier.com.sendbird_learn.ChatActivity;
import elsaghier.com.sendbird_learn.Models.ChatRoomModel;
import elsaghier.com.sendbird_learn.R;

public class ChatRoomsAdapter extends
        RecyclerView.Adapter<ChatRoomsAdapter.ViewHolder> {

    private Context context;
    private List<GroupChannel> list;

    public ChatRoomsAdapter(Context context, List<GroupChannel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.chat_item_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GroupChannel item = list.get(position);

        Glide.with(context).load(R.mipmap.ic_launcher_round).into(holder.imageView);

        holder.chatRoomName.setText(item.getName());
        holder.lastMessage.setText(item.getLastMessage().getData());
        //Todo: Setup viewholder for item 
        holder.bind(item);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        CircleImageView imageView;
        TextView chatRoomName, lastMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.chat_room_image);
            chatRoomName = itemView.findViewById(R.id.chat_room_name);
            lastMessage = itemView.findViewById(R.id.last_message);
        }

        void bind(final GroupChannel model) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("room_name", model.getName());
//                    intent.putExtra("room_id", model.getChannel());
//                    intent.putExtra("room_image", model.getImageUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

}