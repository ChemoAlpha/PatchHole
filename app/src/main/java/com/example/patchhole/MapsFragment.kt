package com.example.patchhole

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        val goa = LatLng(15.496777, 73.827827)

        // Markers
        googleMap.addMarker(MarkerOptions().position(goa).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_GREEN)).title("Pothole is Fixed!!"))
        googleMap.addMarker(MarkerOptions().position(LatLng(15.496650, 73.827800)).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_RED)).title("PotHole to be fixed"))
        googleMap.addMarker(MarkerOptions().position(LatLng(15.506750, 73.837750)).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_GREEN)).title("Pothole is Fixed!!"))
        googleMap.addMarker(MarkerOptions().position(LatLng(15.516730, 73.867700)).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_RED)).title("PotHole to be fixed"))
        googleMap.addMarker(MarkerOptions().position(LatLng(15.526690, 73.957650)).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_GREEN)).title("Pothole is Fixed!!"))
        googleMap.addMarker(MarkerOptions().position(LatLng(15.536670, 73.987770)).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_RED)).title("PotHole to be fixed"))
        googleMap.addMarker(MarkerOptions().position(LatLng(15.546635, 73.927670)).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_GREEN)).title("Pothole is Fixed!!"))
        googleMap.addMarker(MarkerOptions().position(LatLng(15.556725, 73.887730)).icon(BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_RED)).title("PotHole to be fixed"))


        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(goa,10F),5000,null)

        // Adding marker
        fun addCustomMarker(lt:Double,ln:Double) {
            val customMarker = LatLng(lt,ln)
            googleMap.addMarker(MarkerOptions().position(customMarker).icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED)).title("PotHole to be fixed"))

        }

        // Removing marker
        fun removeCustomMarker(m:Marker){
            m.remove()
        }


    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}