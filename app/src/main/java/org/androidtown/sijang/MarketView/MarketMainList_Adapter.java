package org.androidtown.sijang.MarketView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Review_Data;
import org.androidtown.sijang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2017-07-06.
 */

public class MarketMainList_Adapter extends BaseAdapter {
    private Context mContext = null;
    private List<Review_Data> datas = new ArrayList <>();
    LayoutInflater inflater;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;
    //알람 아이템들의 정보값들을 배열로 저장 리스트 포지션 하나당 들어갈 것들

    public MarketMainList_Adapter(List<Review_Data> datas, Context mContext){
        super();
        this.mContext = mContext;
        this.datas = datas;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }//생성자

    @Override
    public int getCount() {
        return datas.size();
    } //리스트뷰 포지션의 개수가 배열의 수만큼 나오게한다

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    } //각 리스트 포지션마다 배열의 저장된 데이터 반환

    @Override
    public long getItemId(int position) {
        return position;
    } //해당 포지션 반환

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.mainreviewlist_item, null);
        }

        TextView market_name = (TextView) convertView.findViewById(R.id.mainreviewlist_item_text_market);

        Review_Data review_data = datas.get(position);
        market_name.setText(review_data.getAll_key());


        return convertView;
    }


}
