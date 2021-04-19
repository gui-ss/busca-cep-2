package com.stefanini.buscacep
import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.stefanini.buscacep.datasource.CepRemoteDataSource
import com.stefanini.buscacep.model.Cep
import com.stefanini.buscacep.presentation.CepPresenter
import java.io.IOException

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var progress: ProgressBar
    lateinit var cepUser: EditText
    lateinit var cepString: String
    lateinit var txtInfo: TextView

    lateinit var local: LatLng

    private lateinit var mMap: GoogleMap

//    val btnSearchCep = findViewById<Button>(R.id.btn_search_cep)


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { true })

        changeNameToolbar()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    @SuppressLint("ShowToast")
    fun searchCep(view: View){
        cepUser = findViewById(R.id.input_cep)
        cepString = cepUser.text.toString()
        val cep: String = cepString
        if (cep == ""){
            Toast.makeText(this, "Por favor digite o cep", Toast.LENGTH_LONG).show()
        }else {
            val dataSource: CepRemoteDataSource = CepRemoteDataSource()
            val presenter: CepPresenter = CepPresenter(this, dataSource)
            presenter.findCepBy(cep)


        }
    }

    fun changeNameToolbar(){
        supportActionBar?.title = "Início"
    }

    fun showProgressBar(){
        progress = findViewById(R.id.progress_bar)
        progress.visibility = ProgressBar.VISIBLE
    }

    fun hideProgressBar(){
        progress.visibility = ProgressBar.GONE
    }

    fun showCep(cep: Cep){
        txtInfo = findViewById(R.id.tv_info_cep)
        txtInfo.text = "CEP: ${cep.cepNumber}" +
                "\nRua: ${cep.street}" +
                "\nBairro: ${cep.neighborhood}" +
                "\nCidade: ${cep.city}" +
                "\nEstado: ${cep.state}"

        local = this!!.getLocationFromAddress(this, cep.cepNumber)!!
        mMap.addMarker(MarkerOptions().position(local).title(cep.cepNumber))
        mMap.setMinZoomPreference(15.0f)
        mMap.setMaxZoomPreference(20.0f)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(local))

    }

    fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            p1 = LatLng(location.latitude, location.longitude)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return p1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }


    fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    override fun onBackPressed() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.nav_home){

        }

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

}