package com.example.ggrr1.bookyou;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageListAdapter extends BaseAdapter {
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MessageListItem> messageListItems = new ArrayList<>();

    public MessageListAdapter() {
    }

    @Override
    public int getCount() {
        return messageListItems.size();
    }

    @Override
    public MessageListItem getItem(int position) {
        return messageListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        Context context = parent.getContext();

        /* 'message_list_item' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_list_item, parent, false);
        }
        /* 'message_list_item'에 정의된 위젯에 대한 참조 획득 */
        TextView user_name = (TextView) convertView.findViewById(R.id.userNameText) ;
        TextView user_message = (TextView) convertView.findViewById(R.id.messageText) ;
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 messageListItem 재활용 */
        MessageListItem messageItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        user_name.setText(messageItem.getUser_name());
        user_message.setText(messageItem.getMessage());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String user_name, String message) {
        MessageListItem messageListItem = new MessageListItem();

        /* MyItem에 아이템을 setting한다. */
        messageListItem.setUser_name(user_name);
        messageListItem.setMessage(message);

        /* mItems에 MyItem을 추가한다. */
        messageListItems.add(messageListItem);
    }

    public void deleteItem(int position) {
        int pos = position;
        int count = getCount();
        for(int i = count-1; i >= 0; i--) {
            if(i == pos) {
                messageListItems.remove(i);
            }
        }

    }
}
