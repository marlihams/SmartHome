package com.smarthome.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.android.HouseDetailActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.beans.Historique;
import com.smarthome.beans.House;
import com.smarthome.controller.HouseDetailControllerI;
import com.smarthome.model.HouseDetailModelI;
import com.smarthome.vo.HouseConsoVO;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class HouseDetailView implements SmartView,HouseObserver {

    public static String SELECTEDHOUSE="selectedHouse";
    private ListView listeHouses;
    private HouseDetailControllerI houseDetailController;
    private HouseDetailModelI houseDetailModel;
    private List<String>consommation;
    private EditText houseName;
    private  EditText houseAddress;
    private TextView nbDevice;
    private TextView nbTurnOn;
    private  TextView nbTurnOff;
    private  TextView nbBroke;
    private Spinner historiqueDate;
    private TextView  consoPeriode;
    private ImageButton submit;
    private LinearLayout chart;

    public HouseDetailView(HouseDetailControllerI houseDetailController,HouseDetailModelI houseDetailModel) {
        this.houseDetailController = houseDetailController;
        this.houseDetailModel=houseDetailModel;
        consommation=new ArrayList<String>();
        subscribeObserver();
    }


    @Override
    public void initializeWidget(View... views) {
        houseName=(EditText)views[0];
        houseAddress=(EditText)views[1];
        nbDevice=(TextView)views[2];
        nbBroke=(TextView)views[3];
        nbTurnOff=(TextView)views[4];
        nbTurnOn=(TextView)views[5];
        historiqueDate=(Spinner)views[6];
        consoPeriode=(TextView)views[7];
        submit=(ImageButton)views[8];
        chart=(LinearLayout)views[9];
        displayWidgetContent();
    }



    @Override
    public void setListener() {
        SmartAnimation.init(HousesActivity.getlContext());
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(SmartAnimation.fad_in);
            House house = houseDetailController.getHouseDetailModel().getHouse();
            String name = houseName.getText().toString();
            String address = houseAddress.getText().toString();
            if (name.equals(house.getName()) && address.equals(house.getAddress())) {
                Toast.makeText(HouseDetailActivity.getlContext(), "nothing to submit", Toast.LENGTH_SHORT).show();
            } else {
                houseDetailController.getHouseDetailModel().updateHouse(name, address);
            }
        }
    });
        historiqueDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                consoPeriode.setText(addSymbole(consommation.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do Nothing
            }
        });
    }



    private  void displayWidgetContent(){
     nbDevice.setText(houseDetailController.getHouseDetailModel().getDevices().size()+"");
        Map houseDetail=houseDetailController.getHouseDetailModel().getHouseDetail();

        nbBroke.setText(houseDetail.get("broke")+"");
        nbTurnOff.setText(houseDetail.get("turnoff")+"");
        nbTurnOn.setText(houseDetail.get("turnon") + "");
        updateSpinner(); // fill spinner and select the first element
        consoPeriode.setText(addSymbole(consommation.get(0)));
        House house=houseDetailController.getHouseDetailModel().getHouse();
        houseAddress.setText(house.getAddress());
        houseName.setText(house.getName());
        displayChartContent();
    }

    private void displayChartContent() {
        XYMultipleSeriesRenderer renderer = getMonthlyConsumptionRenderer();
        GraphicalView chartView = ChartFactory.getBarChartView(HouseDetailActivity.getlContext(),getMonthlyConsumptionDataset(), renderer, BarChart.Type.DEFAULT);
        chart.addView(chartView);
    }

    @Override
    public void subscribeObserver() {
    // no a subscriber
        houseDetailModel.subscribeHouseObserver((HouseObserver) this);
    }

    @Override
    public void updateHouseObserver() {
       House house= houseDetailController.getHouseDetailModel().getHouse();
        houseAddress.setText(house.getAddress());
        houseName.setText(house.getName());
    }

    private void  updateSpinner(){

        List<String>houseHistorique=new ArrayList<String>();
        List<Historique> historique=houseDetailController.getHouseDetailModel().getHouseHistorique();
        for (int i=0,len=historique.size();i<len;i++){
            houseHistorique.add(historique.get(i).getPeriode());
            consommation.add(String.valueOf(historique.get(i).getConsommation()));
        }

        ArrayAdapter<String> adapter= new ArrayAdapter(HouseDetailActivity.getlContext(),android.R.layout.simple_list_item_1,houseHistorique);
        historiqueDate.setAdapter(adapter);
        historiqueDate.setSelection(0);
    }
    private  String addSymbole(String a){
        return a+ " "+ Currency.getInstance(Locale.getDefault()).getSymbol();
    }

    public XYMultipleSeriesDataset getMonthlyConsumptionDataset(){
        CategorySeries series = new CategorySeries("Monthly Consumption");
        int month = 1;
        for(Historique historique : houseDetailController.getSortedHistoriquesByDate()){
            if(month > 6){
                break;
            }
            series.add(new HouseConsoVO(historique).getConsommation());
            month ++;
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());
        return dataset;
    }

    public XYMultipleSeriesRenderer getMonthlyConsumptionRenderer(){
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);

        renderer.setMargins(new int[]{30, 40, 15, 0});
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);

        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(10.5);
        renderer.setYAxisMin(0);
        double maxConsumption = 0;
        for(Historique historique : houseDetailController.getSortedHistoriquesByDate()) {
            HouseConsoVO houseConsoVO = new HouseConsoVO(historique);
            if(houseConsoVO.getConsommation() > maxConsumption) {
                maxConsumption = houseConsoVO.getConsommation();
            }
        }
        renderer.setYAxisMax(maxConsumption + 1);
        int month = 1;
        for(Historique historique : houseDetailController.getSortedHistoriquesByDate()){
            if(month > 6){
                break;
            }
            renderer.addXTextLabel(month++, new HouseConsoVO(historique).getMmYear());
        }
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setBarSpacing(1);
        renderer.setXTitle("Months");
        renderer.setYTitle("Consumption");
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
        renderer.setZoomEnabled(false, false);
        return renderer;
    }
}
