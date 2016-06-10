package org.tig.android.tigadmintoolbox;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nguye on 6/10/2016.
 */
public class AddlistItem {

    private LayoutInflater inflater;
    private Context context;

    public AddlistItem(Context context) {
        // TODO Auto-generated constructor stub
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public View addMemberListItem(String name, String id) {
        View v = inflater.inflate(R.layout.member_list_item, null);
        TextView tvName = (TextView) v.findViewById(R.id.tvmemlistitem_name);
        TextView tvID = (TextView) v.findViewById(R.id.tvMemlistItem_ID);
        tvName.setText(name);
        tvID.setText(id);
        return v;
    }
}
