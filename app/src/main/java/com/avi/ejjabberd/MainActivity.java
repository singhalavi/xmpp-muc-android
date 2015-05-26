package com.avi.ejjabberd;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.avi.ejjabberd.chat.ChatListener;
import com.avi.ejjabberd.fragemnt.ChatFragment;
import com.avi.ejjabberd.fragemnt.InputFragment;
import com.avi.ejjabberd.utils.UiThreadExecutor;
import com.avi.ejjabberd.utils.XmppChatManager;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class MainActivity extends Activity implements ChatListener{
    ListeningScheduledExecutorService backgroundExecutor;

    private ChatFragment chatFragment;
    ListeningExecutorService uiExecutor;
    private XmppChatManager chatManager;
    public static final int OPEN_CHAT_FRAGMENT = 43252;
    private static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        backgroundExecutor = MoreExecutors.listeningDecorator(MoreExecutors.getExitingScheduledExecutorService(new ScheduledThreadPoolExecutor(1)));
        uiExecutor = new UiThreadExecutor(getApplication());
        super.onCreate(savedInstanceState);
        chatManager = new XmppChatManager();
        chatFragment = new ChatFragment();
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new InputFragment(chatManager, this)).addToBackStack("input")
                    .commit();
        }
    }


    public void sendMessage(View view) {
        try {
            chatManager.sendMessage(chatFragment.getChatMessage());
            chatFragment.resetChatText();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processMessage(final com.avi.ejjabberd.dto.Chat chat) {
        uiExecutor.execute(new Runnable() {
            @Override
            public void run() {
                chatFragment.addMessage(chat);
            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void openChatFragment(String group){
        try{
            if (chatFragment != null) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction()
                        .replace(R.id.container, chatFragment, "ff");
                //.replace(R.id.content_frame, fragment);

                transaction = transaction.addToBackStack(group);
                transaction.commit();

            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static MainActivity getInstance() {
        return instance;
    }
}
