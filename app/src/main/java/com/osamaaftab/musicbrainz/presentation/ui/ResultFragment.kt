package com.osamaaftab.musicbrainz.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.osamaaftab.musicbrainz.R
import com.osamaaftab.musicbrainz.domain.model.PlaceModel
import com.osamaaftab.musicbrainz.presentation.viewmodel.ResultViewModel
import com.osamaaftab.musicbrainz.util.Util.Companion.bitmapDescriptorFromVector
import kotlinx.android.synthetic.main.fragment_result.*
import org.koin.android.viewmodel.ext.android.viewModel


class ResultFragment : Fragment(), OnMapReadyCallback {

    private val resultViewModel: ResultViewModel by viewModel()
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initObserver()
        return view
    }

    private fun initObserver() {
        resultViewModel.onAddPin().observe(this.viewLifecycleOwner, {
                addPlacePinToMap(it)
            })

        resultViewModel.onErrorShow().observe(this.viewLifecycleOwner, {
                Snackbar.make(container, it, Snackbar.LENGTH_LONG).show()
            })


        resultViewModel.onShowProgress().observe(this.viewLifecycleOwner, {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
        val searchView =
            SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                resultViewModel.onSearchPlaces("$query AND begin:[1990 TO *]")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun addPlacePinToMap(it: PlaceModel) {
        val latLng =
            LatLng(it.coordinates?.latitude!!.toDouble(), it.coordinates.longitude!!.toDouble())
        val markerOptions: MarkerOptions =
            MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptorFromVector(R.drawable.ic_pin))
                .snippet(it.address)

        val markerName = mMap.addMarker(markerOptions)
        val year = it.lifeSpan?.begin!!.split("-")[0].toInt()
        Thread {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                // Remove pin here
                markerName?.remove()
            }, ((year - 1990) * 1000).toLong())
        }.start()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setHasOptionsMenu(true)
    }
}