package com.example.ggrr1.bookyou;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList <ItemData> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ItemData getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }
        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
//        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookImage) ;
        TextView name = (TextView) convertView.findViewById(R.id.name) ;
        TextView author = (TextView) convertView.findViewById(R.id.author) ;
        TextView price = (TextView) convertView.findViewById(R.id.price) ;
        TextView sale_price = (TextView) convertView.findViewById(R.id.sale_price) ;
        TextView date = (TextView) convertView.findViewById(R.id.date) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        ItemData myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
//        bookImage.setImageDrawable(myItem.getBookImage());
        name.setText(myItem.getBookName());
        author.setText(myItem.getAuthor());
        price.setText(myItem.getPrice());
        sale_price.setText(myItem.getSalePrice());
        date.setText(myItem.getDate());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String book_id, String name, String author ,String price, String sale_price, String created) {
        ItemData mItem = new ItemData();

        /* MyItem에 아이템을 setting한다. */
        mItem.setBook_id(book_id);
        mItem.setBookName(name);
        mItem.setAuthor(author);
        mItem.setPrice(price);
        mItem.setSalePrice(sale_price);
        mItem.setDate(created);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);
    }
}