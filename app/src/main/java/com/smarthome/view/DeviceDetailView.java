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

import com.smarthome.android.DeviceDetailActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.controller.DeviceDetailControllerI;
import com.smarthome.model.DeviceDetailModelI;
import com.smarthome.vo.ConsoVO;
import com.smarthome.vo.PieChartConsoVO;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PieChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class DeviceDetailView implements SmartView,DeviceObserver {

    private static int NB_MONTHS_TO_DISPLAY = 6;
    public static String SELECTEDdevice="selecteddevice";
    private DeviceDetailControllerI deviceDetailController;
    private DeviceDetailModelI deviceDetailModel;
    private EditText deviceName;
    private  EditText pieceName;
    private Spinner historiqueDate;
    private List<String> consommation;
    private ImageButton  submit;
    private TextView  consoPeriode;
    private LinearLayout consodeviceMonthly;
   private LinearLayout consodeviceComparaison;
    private LinearLayout consodeviceChartCircle;
    private TextView comparisonChartTitle;

    public DeviceDetailView(DeviceDetailControllerI deviceDetailController, DeviceDetailModelI deviceDetailModel) {
        this.deviceDetailController = deviceDetailController;
        this.deviceDetailModel=deviceDetailModel;
        consommation=new ArrayList<>();

        subscribeObserver();
    }

    @Override
    public void initializeWidget(View... views) {
        pieceName=(EditText)views[0];
        deviceName=(EditText)views[1];
        submit=(ImageButton)views[2];
        historiqueDate=(Spinner)views[3];
        consoPeriode=(TextView)views[4];
        consodeviceMonthly =(LinearLayout)views[5];

        consodeviceChartCircle = (LinearLayout) views[6];
        comparisonChartTitle=(TextView)views[7];
        consodeviceComparaison = (LinearLayout)views[8];
        displayWidgetContent();
    }



    @Override
    public void setListener() {
        SmartAnimation.init(DevicesActivity.getlContext());
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(SmartAnimation.fad_in);
            Device device = deviceDetailController.getDeviceDetailModel().getDevice();
            String name = deviceName.getText().toString();
            String piece = pieceName.getText().toString();

            if (name.equals(device.getName()) && piece.equals(device.getPieceName())) {
                Toast.makeText(DeviceDetailActivity.getlContext(), "nothing to submit", Toast.LENGTH_SHORT).show();
            } else {
                deviceDetailController.getDeviceDetailModel().updateDevice(name, piece);
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
        updateSpinner(); // fill spinner and select the first element
        if (!consommation.isEmpty())
                consoPeriode.setText(addSymbole(consommation.get(0)));

        Device device=deviceDetailController.getDeviceDetailModel().getDevice();
        pieceName.setText(device.getPieceName());
        deviceName.setText(device.getName());
        displayMonthlyConsumptionChart();
        displayLastMonthComparisonChart();
        displayLastMonthComparisonChartCircle();

    }


    private void displayMonthlyConsumptionChart() {
        XYMultipleSeriesRenderer renderer = getMonthlyConsumptionRenderer();
        GraphicalView chartView = ChartFactory.getBarChartView(DeviceDetailActivity.getlContext(), getMonthlyConsumptionDataset(), renderer, BarChart.Type.DEFAULT);
        consodeviceMonthly.addView(chartView);

    }

    private void displayLastMonthComparisonChart() {
        XYMultipleSeriesRenderer renderer = getLasMonthComparisonRenderer();
        GraphicalView chartView = ChartFactory.getBarChartView(DeviceDetailActivity.getlContext(),getLastMonthComparisonDataset(), renderer, BarChart.Type.DEFAULT);
        consodeviceComparaison.addView(chartView);
    }
    private void displayLastMonthComparisonChartCircle(){
        // Pie Chart Section Names
        String oldText = String.valueOf(comparisonChartTitle.getText());

        List<PieChartConsoVO> consoVOs= deviceDetailController.getDeviceDetailModel().getDeviceConsoVo();
        comparisonChartTitle.setText(oldText + " during " + consoVOs.get(0).getMmYear());


        CategorySeries distributionSeries = new CategorySeries("pie chart ");
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        NumberFormat percentFormat=NumberFormat.getPercentInstance(Locale.US);
        percentFormat.setMaximumFractionDigits(1);


        for(int i=0 ;i < consoVOs.size();i++){
            // Adding a slice with its values and name to the Pie Chart
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            distributionSeries.add(consoVOs.get(i).getName(), consoVOs.get(i).getConsommation());
            seriesRenderer.setColor(consoVOs.get(i).getColor());
            seriesRenderer.setDisplayBoundingPoints(true);
            seriesRenderer.setChartValuesFormat(percentFormat);

            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }
        defaultRenderer.setDisplayValues(true);
        defaultRenderer.setLabelsTextSize(40);
        defaultRenderer.setPanEnabled(false);

        defaultRenderer.setShowLegend(false);

        defaultRenderer.setZoomEnabled(false);
        GraphicalView chartView=ChartFactory.getPieChartView(DeviceDetailActivity.getlContext(),distributionSeries,defaultRenderer);
        consodeviceChartCircle.addView(chartView);
    }

    @Override
    public void subscribeObserver() {
    // no a subscriber
        deviceDetailModel.subscribeDeviceObserver((DeviceObserver) this);
    }

    private void  updateSpinner(){

        List<String> deviceHistorique=new ArrayList<String>();
        List<Historique> historique=deviceDetailController.getDeviceDetailModel().getDeviceHistorique();
        for (int i=0,len=historique.size();i<len;i++){
            deviceHistorique.add(historique.get(i).getPeriode());
            consommation.add(String.valueOf(historique.get(i).getConsommation()));
        }

        ArrayAdapter<String> adapter= new ArrayAdapter(DeviceDetailActivity.getlContext(),android.R.layout.simple_list_item_1,deviceHistorique);
        historiqueDate.setAdapter(adapter);
        historiqueDate.setSelection(0);
    }

    @Override
    public void updateDeviceObserver() {
        Device device= deviceDetailController.getDeviceDetailModel().getDevice();
        pieceName.setText(device.getPieceName());
        deviceName.setText(device.getName());
    }
    private  String addSymbole(String a){
        return a+ " "+ Currency.getInstance(Locale.getDefault()).getSymbol();
    }

    public XYMultipleSeriesDataset getMonthlyConsumptionDataset(){
        CategorySeries series = new CategorySeries("Monthly Consumption");
        int month = 1;
        for(Historique historique : deviceDetailController.getSortedHistoriquesByDate()){
            if(month > NB_MONTHS_TO_DISPLAY){
                break;
            }
            series.add(new ConsoVO(historique,false).getConsommation());
            month ++;
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());
        return dataset;
    }
    public XYMultipleSeriesDataset getLastMonthComparisonDataset() {
        CategorySeries series = new CategorySeries("Last Month Consumption comparison");
        List<Historique> lastConsosBydevice = deviceDetailController.getDeviceDetailModel().getLastConsumptionsByDevice();
        for(Historique historique : lastConsosBydevice){
            series.add(new ConsoVO(historique,false).getConsommation());
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());
        return dataset;
    }

    public XYMultipleSeriesRenderer getMonthlyConsumptionRenderer(){

        double maxConsumption = 0;
        List<Historique>historiques= deviceDetailController.getSortedHistoriquesByDate();
        for(Historique historique :historiques) {
            ConsoVO deviceConsoVO = new ConsoVO(historique,false);
            if(deviceConsoVO.getConsommation() > maxConsumption) {
                maxConsumption = deviceConsoVO.getConsommation();
            }
        }
        XYMultipleSeriesRenderer renderer = getBarChartRenderer(NB_MONTHS_TO_DISPLAY,maxConsumption );
        int month = 1;
        for(Historique historique :historiques){
            if(month > NB_MONTHS_TO_DISPLAY){
                break;
            }
            renderer.addXTextLabel(month++, new ConsoVO(historique,false).getMmYear());
        }
        return renderer;
    }

    public XYMultipleSeriesRenderer getLasMonthComparisonRenderer() {
        List<Historique> lastConsosBydevice = deviceDetailController.getDeviceDetailModel().getLastConsumptionsByDevice();
        double maxConsumption = 0;
        for(Historique historique : lastConsosBydevice) {
            ConsoVO deviceConsoVO = new ConsoVO(historique,false);
            if(deviceConsoVO.getConsommation() > maxConsumption) {
                maxConsumption = deviceConsoVO.getConsommation();
            }
        }
        XYMultipleSeriesRenderer renderer = getBarChartRenderer(lastConsosBydevice.size(), maxConsumption);
        int month = 1;
        for(Historique historique : lastConsosBydevice){
            renderer.addXTextLabel(month++, new ConsoVO(historique,false).getName());
        }
        return renderer;
    }

    private XYMultipleSeriesRenderer getBarChartRenderer(double xAxisMax, double yAxisMax) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setLabelsTextSize(30);
        renderer.setMargins(new int[]{30, 40, 15, 0});
     //   renderer.setMargins(new int[]{60, 40, 60, 30});
        renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        renderer.setXTitle("Month");
        renderer.setYTitle("Consumption");
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setAxisTitleTextSize(30);
        renderer.setLabelsColor(Color.BLACK);

        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(xAxisMax + 1);
        renderer.setYAxisMax(yAxisMax);
        renderer.setYAxisMin(0);
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setBarSpacing(2);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
        renderer.setZoomEnabled(false, false);
        renderer.setPanEnabled(false, false);
        renderer.setShowLegend(false);
        return renderer;
    }

    @Override
    public void updateDeviceLightObserver(int parent, int child, boolean ischecked) {

        // turn on or turn off
    }
}
