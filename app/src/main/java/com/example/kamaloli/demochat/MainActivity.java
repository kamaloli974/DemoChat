package com.example.kamaloli.demochat;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ChatMessageListener {
    Button send,dm;
    public TextView receivedMessage,sentMessage;
    EditText messageInput;
    AbstractXMPPConnection connection;
    Chat newChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variableInitializer();
        AsyncronousNetworkThread thread=new AsyncronousNetworkThread();
        thread.execute();
        send.setOnClickListener(this);
        dm.setOnClickListener(this);



    }


    //Variable initialisation
    public void variableInitializer(){
        send=(Button)findViewById(R.id.send);
        receivedMessage=(TextView)findViewById(R.id.received_messages);
        sentMessage=(TextView)findViewById(R.id.sent_message);
        messageInput=(EditText)findViewById(R.id.message_input);
        dm=(Button)findViewById(R.id.dm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
//                ChatManager managerger=ChatManager.getInstanceFor(connection);
//                Chat newChat= managerger.createChat("admin", new ChatMessageListener() {
//                    @Override
//                    public void processMessage(Chat chat, Message message) {
//                        chat.addMessageListener(this);
//                        receivedMessage.setText(message.getBody());
//                    }
//                });
//                try {
//                    newChat.sendMessage(messageInput.getText().toString());
//                } catch (SmackException.NotConnectedException e) {
//                    Log.e("smack Exception",e+"");
//                }
                break;
            case R.id.dm:
                receivedMessage.setText("Hi");
                break;
            default:
                sentMessage.setText("Something is wrong");
        }
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        //receivedMessage.setText(message.getBody());
        try {
            if(message.getBody()!=null){
                chat.sendMessage("hello");
                Log.e("chat",""+chat);
                Log.e("Message",""+message.getBody());
            }
            else{
                //
            }

        } catch (SmackException.NotConnectedException e) {
            Log.e("SmackError",e+"");
        }

    }

//    @Override
//    public void processMessage(Message message) {
//        if(connection.isAuthenticated()){
//            ChatManager manager= ChatManager.getInstanceFor(connection);
//
//        }
//    }
//

    public class AsyncronousNetworkThread extends AsyncTask<Void,Void,AbstractXMPPConnection> {
        AbstractXMPPConnection con;
        @Override
        protected AbstractXMPPConnection doInBackground(Void... params) {
            XMPPTCPConnectionConfiguration.Builder connection=XMPPTCPConnectionConfiguration.builder()
                    .setHost("192.168.43.35")
                    .setServiceName("desktop-23aatnm")
                    .setUsernameAndPassword("kamaloli752","kamal")
                    .setResource("Smack")
                    .setPort(5222)
                    .setDebuggerEnabled(true);
            AbstractXMPPConnection nconnection=new XMPPTCPConnection(connection.build());
            try {
                nconnection.connect();
                nconnection.login();
                ChatManager manager =ChatManager.getInstanceFor(nconnection);
                manager.addChatListener(new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean createdLocally) {
                        if(!createdLocally){
                            chat.addMessageListener(new MainActivity());
                        }
                    }
                });
            } catch (SmackException e) {
                Log.e("SmackException"," "+e);
            } catch (IOException e) {
                Log.e("IOException",""+e);

            } catch (XMPPException e) {
                Log.e("XMPPException",""+e);
            }

            return nconnection;
        }

        @Override
        protected void onPostExecute(AbstractXMPPConnection abstractXMPPConnection) {
            if(abstractXMPPConnection.isConnected()){
                connection=abstractXMPPConnection;
                Log.e("Classname :",abstractXMPPConnection+""+abstractXMPPConnection.getHost());
            }
            else{
                Log.e("Uninitialize","Varible abstractXmpp is not initialized");
            }
        }
    }
}


//if(nconnection.isAuthenticated()){
//        ChatManager man=ChatManager.getInstanceFor(nconnection);
//        man.addChatListener(new ChatManagerListener() {
//@Override
//public void chatCreated(Chat chat, boolean createdLocally) {
//        chat.addMessageListener(new ChatMessageListener() {
//@Override
//public void processMessage(Chat chat, Message message) {
//        //System.out.println("message is"+(message!=null?message.getBody():null));
//        Log.e("Message",message.getBody());
//        }
//        });
//        Log.w("m",chat.toString());
//        }
//        });
//        }
//sentMessage.setText("Hello");
//sentMessage.setText(""+
//        connection.getHost()+
//        connection.getUser()+
//        connection.getPort()+
//        connection.isAuthenticated()+
//        connection.getFromMode()+
//        connection.getConnectionCounter()+
//        connection.isAnonymous()
//        );