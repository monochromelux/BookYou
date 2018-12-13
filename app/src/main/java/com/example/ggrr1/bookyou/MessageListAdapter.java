package com.example.ggrr1.bookyou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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
        TextView book_name = (TextView) convertView.findViewById(R.id.userBookNameText);
        TextView user_name = (TextView) convertView.findViewById(R.id.userNameText) ;
        TextView user_tel = (TextView) convertView.findViewById(R.id.userTelText);
        TextView user_message = (TextView) convertView.findViewById(R.id.messageText) ;
        TextView created = (TextView) convertView.findViewById(R.id.createdText);

        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 messageListItem 재활용 */
        MessageListItem messageItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        book_name.setText(messageItem.getBook_name());
        user_name.setText(messageItem.getUser_name());
        user_tel.setText(messageItem.getTel());
        user_message.setText(messageItem.getMessage());
        created.setText(messageItem.getCreated());
        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(int message_id, String book_name, String user_name, String user_tel, String message, String created) {
        MessageListItem messageListItem = new MessageListItem();

        /* MyItem에 아이템을 setting한다. */
        messageListItem.setMessage_id(message_id);
        messageListItem.setBook_name(book_name);
        messageListItem.setUser_name(user_name);
        messageListItem.setTel(user_tel);
        messageListItem.setMessage(message);
        messageListItem.setCreated(created);

        /* mItems에 MyItem을 추가한다. */
        messageListItems.add(messageListItem);
    }

    public int deleteItem(int position) {
        int pos = position;
        int count = getCount();
        int message_id = 0;
        for(int i = count-1; i >= 0; i--) {
            if(i == pos) {
                message_id = messageListItems.get(i).getMessage_id();
                messageListItems.remove(i);
            }
        }
        return message_id;
    }
}
