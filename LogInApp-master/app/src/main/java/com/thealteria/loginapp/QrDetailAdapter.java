package com.thealteria.loginapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class QrDetailAdapter extends ArrayAdapter<QrBeanModel> {
    List<QrBeanModel> QrObject;
    //    private ArrayList<QrBeanModel> privatearray;
    public QrDetailAdapter(Context context, int resource, List<QrBeanModel> objects) {
        super(context, resource, objects);
        this.QrObject = objects;
    }

    public void remove(int position) {
        QrBeanModel qrBeanModel = getItem(position);
        qrBeanModel.getQrText();
        QrObject.remove(position);
        //Toast.makeText(getContext(),"Clicked  " + qrBeanModel.getSpecDate(),Toast.LENGTH_LONG).show();
        QrcodeActivity.sendUniqueKey(qrBeanModel.getSpecDate());
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.qr_list_items,parent,false);
        TextView qrText = convertView.findViewById(R.id.qrText);
        TextView qrDate = convertView.findViewById(R.id.qrDate);
        QrBeanModel qrBeanModel = getItem(position);
        qrText.setText(qrBeanModel.getQrText());
        qrDate.setText(qrBeanModel.getDate());
        return convertView;
    }

//    // Filter Class
//    public void filter(String charText) {
//
//        charText = charText.toLowerCase(Locale.getDefault());
//        QrObject.clear();
//        if(charText.length()==0){
//            privatearray.addAll(QrObject);
//        }
//        else{
//            for (QrBeanModel c : privatearray) {
//                if (c.getQrText().toLowerCase(Locale.getDefault())
//                        .contains(charText)) {
//                    QrObject.add(c);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
}


