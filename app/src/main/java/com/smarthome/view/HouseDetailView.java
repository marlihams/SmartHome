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
import com.smarthome.vo.ConsoVO;

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

    private static int NB_MONTHS_TO_DISPLAY = 6;
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
    private LinearLayout monthlyConsumption;
    private LinearLayout lasMonthComparisonChart;
    private TextView comparisonChartTitle;
    private EditText routeur;

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
        nbTurnOff=(TextView)views[3];
        nbTurnOn=(TextView)views[4];
        historiqueDate=(Spinner)views[5];
        consoPeriode=(TextView)views[6];
        submit=(ImageButton)views[7];
        monthlyConsumption =(LinearLayout)views[8];
        lasMonthComparisonChart = (LinearLayout)views[9];
        comparisonChartTitle = (TextView) views[10];
        routeur=(EditText)views[11];
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
        displayMonthlyConsumptionChart();
        displayLastMonthComparisonChart();
    }

    private void displayMonthlyConsumptionChart() {
        XYMultipleSeriesRenderer renderer = getMonthlyConsumptionRenderer();
        GraphicalView chartView = ChartFactory.getBarChartView(HouseDetailActivity.getlContext(),getMonthlyConsumptionDataset(), renderer, BarChart.Type.DEFAULT);
        monthlyConsumption.addView(chartView);

    }

    private void displayLastMonthComparisonChart() {
        XYMultipleSeriesRenderer renderer = getLasMonthComparisonRenderer();
        GraphicalView chartView = ChartFactory.getBarChartView(HouseDetailActivity.getlContext(),getLastMonthComparisonDataset(), renderer, BarChart.Type.DEFAULT);
        lasMonthComparisonChart.addView(chartView);
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
            if(month > NB_MONTHS_TO_DISPLAY){
                break;
            }
            series.add(new ConsoVO(historique,true).getConsommation());
            month ++;
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());
        return dataset;
    }
    public XYMultipleSeriesDataset getLastMonthComparisonDataset() {
        CategorySeries series = new CategorySeries("Last Month Consumption comparison");
        List<Historique> lastConsosByHouse = houseDetailController.getHouseDetailModel().getLastConsumptionsByHouse();
        for(Historique historique : lastConsosByHouse){
            series.add(new ConsoVO(historique,true).getConsommation());
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());
        return dataset;
    }
    public XYMultipleSeriesRenderer getMonthlyConsumptionRenderer(){

        double maxConsumption = 0;
        for(Historique historique : houseDetailController.getSortedHistoriquesByDate()) {
            ConsoVO consoVO = new ConsoVO(historique,true);
            if(consoVO.getConsommation() > maxConsumption) {
                maxConsumption = consoVO.getConsommation();
            }
        }
        XYMultipleSeriesRenderer renderer = getBarChartRenderer(NB_MONTHS_TO_DISPLAY,maxConsumption );
        int month = 1;
        for(Historique historique : houseDetailController.getSortedHistoriquesByDate()){
            if(month > NB_MONTHS_TO_DISPLAY){
                break;
            }
            renderer.addXTextLabel(month++, new ConsoVO(historique,true).getMmYear());
        }
        return renderer;
    }

    public XYMultipleSeriesRenderer getLasMonthComparisonRenderer() {
        List<Historique> lastConsosByHouse = houseDetailController.getHouseDetailModel().getLastConsumptionsByHouse();
        String oldText = String.valueOf(comparisonChartTitle.getText());
        comparisonChartTitle.setText(oldText + " during " + lastConsosByHouse.get(0).getPeriode());
        double maxConsumption = 0;
        for(Historique historique : lastConsosByHouse) {
            ConsoVO consoVO = new ConsoVO(historique,true);
            if(consoVO.getConsommation() > maxConsumption) {
                maxConsumption = consoVO.getConsommation();
            }
        }
        XYMultipleSeriesRenderer renderer = getBarChartRenderer(lastConsosByHouse.size(), maxConsumption);
        int month = 1;
        for(Historique historique : lastConsosByHouse){
            renderer.addXTextLabel(month++, new ConsoVO(historique,true).getName());
        }
        return renderer;
    }

    private XYMultipleSeriesRenderer getBarChartRenderer(double xAxisMax, double yAxisMax) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setLabelsTextSize(30);

        renderer.setMargins(new int[]{30, 40, 15, 0});
        renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        renderer.setXTitle("Month");
        renderer.setYTitle("Consumption");

        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(xAxisMax + 1);
        renderer.setYAxisMax(yAxisMax);
        renderer.setYAxisMin(0);
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setBarSpacing(1);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
        renderer.setZoomEnabled(false, false);
        renderer.setPanEnabled(false, false);
        renderer.setShowLegend(false);
        return renderer;
    }
}
