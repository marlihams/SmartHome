package com.smarthome.model;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.R;
import com.smarthome.android.DevicesActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mdiallo on 28/12/2015.
 */
public class DeviceListAdapter extends BaseExpandableListAdapter {


        private Context context;
    private DevicesModelI devicesModel;

    public HashMap<String, List<Boolean>> getListSwitch() {
        return listSwitch;
    }

    public HashMap<String, List<String>> getListDevices() {
        return listDevices;
    }

    public List<String> getListPieces() {
        return listPieces;
    }

    private HashMap<String, List<Boolean>>  listSwitch;
        private List<String> listPieces; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> listDevices;

    public DevicesModelI getDevicesModel() {
        return devicesModel;
    }

    public void setDevicesModel(DevicesModelI devicesModel) {
        this.devicesModel = devicesModel;
    }

    public DeviceListAdapter(Context context, List<String> listPieces,HashMap<String, List<Boolean>>  listSwitch,
                                     HashMap<String, List<String>> listDevices) {
            this.context = context;
            this.listPieces = listPieces;
            this.listDevices = listDevices;
            this.listSwitch=listSwitch;

        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this.listDevices.get(this.listPieces.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent){

            final String childText = (String) getChild(groupPosition, childPosition);
             final boolean childBool=this.listSwitch.get(this.listPieces.get(groupPosition))
                                     .get(childPosition);


            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_device,null,false);
            }

            TextView deviceName = (TextView) convertView
                    .findViewById(R.id.deviceName);
            final Switch deviceLight=(Switch)convertView.findViewById(R.id.deviceLight);
          //  deviceLight.setFocusable(false);
            deviceLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (childBool != isChecked) {
                        try {
                            devicesModel.notifySwitchObserver(groupPosition, childPosition,isChecked);
                        } catch (Exception e) {
                            Toast.makeText(DevicesActivity.getlContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });


            deviceName.setText(childText);
            deviceLight.setChecked(childBool);
            convertView.setClickable(false);

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.listDevices.get(this.listPieces.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.listPieces.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this.listPieces.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group_device, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.listPiece);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
            convertView.setClickable(false);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
        }

    public void addItem(String piece,String name,boolean etat) {

        if (!listPieces.contains(piece)) {
            listPieces.add(piece);
        }
        listDevices.get(piece).add(name);
        listSwitch.get(piece).add(etat);
        notifyDataSetChanged();
    }
    public void addItem(int piecePosition,String name,boolean etat) {

        String piece=listPieces.get(piecePosition);
        listDevices.get(piece).add(name);
        listSwitch.get(piece).add(etat);
        notifyDataSetChanged();
    }
    public void addItem(String piece){
        listPieces.add(piece);
        listDevices.put(piece, new ArrayList<String>());
        listSwitch.put(piece,new ArrayList<Boolean>());
        notifyDataSetChanged();
    }
    public void removeItem(String piece){

        if (listPieces.contains(piece)) {
            listDevices.get(piece).clear();
            listSwitch.get(piece).clear();
            listPieces.remove(piece);
            notifyDataSetChanged();

        }
    }
    public void removeItem(String piece,int positionChild){

            listDevices.get(piece).remove(positionChild);
            listSwitch.get(piece).remove(positionChild);

            notifyDataSetChanged();
        }

    public String getPieceName(int parentPosition) {
        return  listPieces.get(parentPosition);
    }
    public String getDeviceName(String p, int childPosition) {

        return listDevices.get(p).get(childPosition);
    }

    

    public void updateState(int parent, int child) {

        boolean bool = listSwitch.get(listPieces.get(parent)).get(child);

        listSwitch.get(listPieces.get(parent)).remove(child);
        listSwitch.get(listPieces.get(parent)).add(child,!bool);
        notifyDataSetChanged();
    }
}
