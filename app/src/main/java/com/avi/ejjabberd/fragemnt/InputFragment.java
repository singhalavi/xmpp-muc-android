package com.avi.ejjabberd.fragemnt;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.avi.ejjabberd.MainActivity;
import com.avi.ejjabberd.R;
import com.avi.ejjabberd.chat.ChatListener;
import com.avi.ejjabberd.utils.XmppChatManager;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class InputFragment extends Fragment {
    ListeningScheduledExecutorService backgroundExecutor;
    EditText username;
    EditText password;
    EditText group;
    EditText chatHost;
    EditText groupChatHost;
    Button connectButton;
    private XmppChatManager chatManager;
    private ChatListener chatListener;

    public InputFragment(){

    }
    public InputFragment( XmppChatManager chatManager, ChatListener chatListener) {
        this.chatManager = chatManager;
        this.chatListener = chatListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        backgroundExecutor = MoreExecutors.listeningDecorator(MoreExecutors.getExitingScheduledExecutorService(new ScheduledThreadPoolExecutor(1)));
        View rootView = inflater.inflate(R.layout.input_fragment, container, false);
        username = (EditText)rootView.findViewById(R.id.username);
        password = (EditText)rootView.findViewById(R.id.password);
        group  = (EditText)rootView.findViewById(R.id.group);
        chatHost  = (EditText)rootView.findViewById(R.id.chathost);
        groupChatHost  = (EditText)rootView.findViewById(R.id.groupchathost);
        connectButton = (Button)rootView.findViewById(R.id.connect);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        chatManager.setConnectionParams(chatListener, chatHost.getText().toString(), groupChatHost.getText().toString(), username.getText().toString(), password.getText().toString());
                        if(chatManager.join(group.getText().toString())){
                            MainActivity.getInstance().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity.getInstance().openChatFragment(group.getText().toString());
                                }
                            });
                        }
                    }
                });
            }
        });
        return rootView;
    }


}
