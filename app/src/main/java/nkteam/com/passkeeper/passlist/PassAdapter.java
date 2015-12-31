package nkteam.com.passkeeper.passlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nkteam.com.passkeeper.database.PKDataModel;

public class PassAdapter extends ArrayAdapter<PKDataModel> {

    public PassAdapter(Context context, List passList) {
        super(context, android.R.layout.simple_list_item_1, passList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PKDataModel pkDataModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(android.R.layout.simple_list_item_1, null);
        }

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(pkDataModel.getUrl());

        return convertView;
    }
}