package com.avi.ejjabberd.fragemnt;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.avi.ejjabberd.R;
import com.avi.ejjabberd.dto.Chat;
import com.avi.ejjabberd.adaptor.*;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private ListView feedsList;
    private View sendMessage;
    private ChatAdaptor chatAdaptor = null;
    private ArrayList<Chat> chats;
    private EditText chatText;

    public ChatFragment() {
        chats = new ArrayList<Chat>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chat_list, container, false);
        feedsList = (ListView) rootView.findViewById(R.id.ChatsList);

        sendMessage = LayoutInflater.from(getActivity()).inflate(
                R.layout.send_message
                , null);
        chatText =(EditText) sendMessage.findViewById(R.id.chat);

        feedsList.addFooterView(sendMessage);
        setAdaptor(chats);

        return rootView;
    }

    private void setAdaptor(List<Chat> chats){
        chatAdaptor = new ChatAdaptor(getActivity().getApplicationContext(), chats);
        feedsList.setAdapter(chatAdaptor);
    }

    public void addMessage(Chat chat){
        chats.add(chat);
        if(chatAdaptor != null)
        {
            chatAdaptor.notifyDataSetChanged();
            feedsList.invalidate();
        }
    }

    public void resetChatText(){
        if(chatText != null){
            chatText.setText("");
        }
    }
    public String getChatMessage(){
        return chatText.getText().toString();
    }

}
