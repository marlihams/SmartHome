package com.smarthome.android;


        import android.content.Context;
        import android.os.Bundle;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ImageButton;

        import com.smarthome.R;
        import com.smarthome.controller.AcceuilController;
        import com.smarthome.controller.AcceuilControllerI;
        import com.smarthome.view.AcceuilView;
        import com.smarthome.view.SmartChangeView;
        import com.smarthome.view.SmartHomeView;
/**
 * Created by Mdiallo on 20/12/2015.
 */
public abstract class SmartMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    protected static Context lContext;
        Toolbar toolbar;
        DrawerLayout drawer;
        NavigationView navigationView;
        ActionBarDrawerToggle toggle;


    // view property

    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        lContext=this;

    }

    public static Context getlContext() {
        return lContext;
    }

   protected void initialize(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.home:
                        SmartChangeView.changeView(lContext,"acceuil");
                        break;
                    case R.id.devices:
                        SmartChangeView.changeView(lContext,"devices");
                        break;
                    case R.id.profil:
                        SmartChangeView.changeView(lContext,"profiles");
                        break;
                    case R.id.houses:
                        SmartChangeView.changeView(lContext,"houses");
                        break;
                    case R.id.bluetooth:
                        SmartChangeView.setBluetooth(true);
                        break;
                    case R.id.routeur: //TODO open the screen config for Routeur
                        SmartChangeView.setBluetooth(false);
                        break;
                    default:
                        break;

                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed(){

            if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            }else{
            super.onBackPressed();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
        //    getMenuInflater().inflate(R.menu.activity_connexion_drawer,menu);
            return true;
            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id=item.getItemId();


            //noinspection SimplifiableIfStatement
            if(id==R.id.action_settings){
            return true;
            }

            return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}