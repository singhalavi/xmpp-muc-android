package com.avi.ejjabberd.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avi.ejjabberd.R;
import com.avi.ejjabberd.dto.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avinash on 4/10/14.
 */
public class ChatAdaptor extends BaseAdapter {
    private Context context;
    private List<Chat> ChatItems;

    public ChatAdaptor(Context context, List<Chat> ChatItems) {
        this.context = context;
        this.ChatItems = ChatItems;
    }

    @Override
    public int getCount() {
        return ChatItems.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < getCount())
        {
            return ChatItems.get(position);
        }
        return null;
    }

    public void clearEntries() {
        // Clear all the data points
        ChatItems.clear();
        notifyDataSetChanged();
    }

    public void addEntriesToBottom(List<Chat> entries) {
        if(ChatItems == null){
            ChatItems = new ArrayList<Chat>();
        }

        if(entries != null){
            // Add entries to the bottom of the list
            ChatItems.addAll(entries);
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static String parseFrom(String from){
        int index = from.lastIndexOf("/");
        if(index != -1){
            return from.substring(index+1);
        }
        index = from.indexOf("@");
        if(index != -1){
            return from.substring(0, index);
        }
        return from;
    }

    public static String parseTo(String input){
        int index = input.indexOf("@");
        if(index != -1){
            return input.substring(0, index);
        }
        return input;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.chat_list_item, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.from);

        txtTitle.setText(parseFrom(ChatItems.get(position).getFrom()));

        TextView chatText = (TextView) convertView.findViewById(R.id.text);

        chatText.setText(ChatItems.get(position).getMessage());

        return convertView;
    }

    public void addChatMessage(Chat chat){
        ChatItems.add(chat);
        notifyDataSetChanged();
    }

}
