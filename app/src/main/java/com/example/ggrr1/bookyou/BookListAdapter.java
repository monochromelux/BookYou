package com.example.ggrr1.bookyou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookListAdapter extends BaseAdapter{
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList <BookListItem> bookListItems = new ArrayList<>();

    @Override
    public int getCount() {
        return bookListItems.size();
    }

    @Override
    public BookListItem getItem(int position) {
        return bookListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 'book_list_item' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_list_item, parent, false);
        }
        /* 'book_list_item'에 정의된 위젯에 대한 참조 획득 */
//        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookImage) ;
        TextView name = (TextView) convertView.findViewById(R.id.name) ;
        TextView author = (TextView) convertView.findViewById(R.id.author) ;
        TextView price = (TextView) convertView.findViewById(R.id.price) ;
        TextView sale_price = (TextView) convertView.findViewById(R.id.sale_price) ;
        TextView date = (TextView) convertView.findViewById(R.id.date) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 bookListItem 재활용 */
        BookListItem bookItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
//        bookImage.setImageDrawable(myItem.getBookImage());
        name.setText(bookItem.getBookName());
        author.setText(bookItem.getAuthor());
        price.setText(bookItem.getPrice());
        sale_price.setText(bookItem.getSalePrice());
        date.setText(bookItem.getDate());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String book_id, String name, String author ,String price, String sale_price, String created) {
        BookListItem bookListItem = new BookListItem();

        /* bookListItem 아이템을 setting한다. */
        bookListItem.setBook_id(book_id);
        bookListItem.setBookName(name);
        bookListItem.setAuthor(author);
        bookListItem.setPrice(price);
        bookListItem.setSalePrice(sale_price);
        bookListItem.setDate(created);

        /* mItems에 MyItem을 추가한다. */
        bookListItems.add(bookListItem);
    }
}