package com.smarthome.model;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.smarthome.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mdiallo on 28/12/2015.
 */
public class DeviceListAdapter extends BaseExpandableListAdapter {


        private Context context;

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
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);
            final boolean childBool=this.listSwitch.get(this.listSwitch.get(groupPosition))
                                     .get(childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_device, null);
            }

            TextView deviceName = (TextView) convertView
                    .findViewById(R.id.deviceName);
            Switch deviceLight=(Switch)convertView.findViewById(R.id.deviceLight);

            deviceName.setText(childText);
            deviceLight.setChecked(childBool);
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

}
