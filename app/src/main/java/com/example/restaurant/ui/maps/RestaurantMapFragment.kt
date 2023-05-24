package com.example.restaurant.ui.maps

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.restaurant.R
import com.example.restaurant.databinding.FragmentRestaurantMapBinding
import com.example.restaurant.domain.models.Response
import com.example.restaurant.domain.models.Result
import com.example.restaurant.domain.models.ResultsItem
import com.example.restaurant.extensions.addTo
import com.example.restaurant.extensions.showToast
import com.example.restaurant.ui.base.BaseFragment
import com.example.restaurant.ui.home.HomeFragment
import com.example.restaurant.utils.FLAG_KEY_SAVED_ELEMENTS
import com.example.restaurant.utils.GeoLocationManager
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel


class RestaurantMapFragment :
    BaseFragment<FragmentRestaurantMapBinding>(FragmentRestaurantMapBinding::inflate),
    OnMapReadyCallback, MaterialSearchBar.OnSearchActionListener,
    SuggestionsAdapter.OnItemViewClickListener, OnMarkerClickListener {

    private lateinit var searchBar: MaterialSearchBar
    private lateinit var map: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var locationManager: GeoLocationManager
    private lateinit var placesClient: PlacesClient
    private lateinit var predictionsList: List<AutocompletePrediction>
    private val autoCompleteSessionToken = AutocompleteSessionToken.newInstance()
    private var locationTrackingRequested: Boolean = false
    private lateinit var latLng: LatLng
    private var currentLocationSet = false
    private val mapZoom = 19F
    private var listener: HomeFragment.Listener? = null
    private var placeResponse: FetchPlaceResponse? = null
    private val viewModel by viewModel<RestaurantMapViewModel>()
    private var apiKey: String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragment.Listener) {
            listener = context
        } else {
            throw RuntimeException(getString(R.string.fragment_not_implemented))
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView(savedInstanceState)
        configSearchBar()
        hideToolbar()

    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

    }


    override fun onPause() {
        super.onPause()
        locationManager.stopLocationTracking()
    }

    override fun onResume() {
        super.onResume()

        if (locationTrackingRequested) {
            locationManager.startLocationTracking(locationCallback)
        }
    }

    private fun requestLocationPermission(): Boolean {
        var permissionGranted = false
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                permissionGranted = true
                locationManager.startLocationTracking(locationCallback)

            } else {
                permissionGranted = false
                showToast(getString(R.string.permissions_not_enabled))
            }
        }

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        return permissionGranted
    }

    private fun configView(savedInstanceState: Bundle?) {
        try {
            MapsInitializer.initialize(requireContext())
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }

        apiKey = getString(R.string.google_maps_key)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }
        placesClient = Places.createClient(requireContext())
        locationManager = GeoLocationManager(requireContext())
        mapView = binding.restaurantMap
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        mapView.visibility = View.INVISIBLE

        if (requestLocationPermission()) {
            locationManager.startLocationTracking(locationCallback)
            locationTrackingRequested = true
        }

    }

    private fun configSearchBar() {
        searchBar = binding.searchbarKeyword
        searchBar.setSpeechMode(true)
        searchBar.setOnSearchActionListener(this)
        searchBar.setSuggestionsClickListener(this)
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 5) {
                    return
                }
                fetchPlacesPredictions(s.toString())
            }
        })
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            for (location in p0.locations) {
                val latitude = location.latitude
                val longitude = location.longitude
                latLng = LatLng(latitude, longitude)

                if (!currentLocationSet) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom))
                    mapView.visibility = View.VISIBLE
                    currentLocationSet = true
                    fetchNearPlaces()
                }
            }
        }
    }

    private fun fetchNearPlaces() {
        viewModel.getClosePlaces(
            latLng,
            500,
            "restaurant",
            apiKey
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is Result.Success<Response> -> {
                        saveToLocalData(it.data.results)
                        Log.d("MESSAGE", "Success")
                    }
                    else -> Log.d("MESSAGE", "Failed")
                }
            }.addTo(disposable)


    }

    private fun saveToLocalData(results: List<ResultsItem>) {
        viewModel.savePlaces(results).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is Result.Success<ResultsItem> -> {
                        saveFlagToSharedPreferences(true, FLAG_KEY_SAVED_ELEMENTS)
                    }
                    else -> {
                        saveFlagToSharedPreferences(false, FLAG_KEY_SAVED_ELEMENTS)
                    }
                }
            }.addTo(disposable)
    }


    private fun fetchPlaceDetails(placeId: String) {
        val placeFields = listOf(Place.Field.LAT_LNG, Place.Field.NAME)
        val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build()

        placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener {
            val place = it.place
            val placeLatLng = place.latLng
            placeResponse = it
            if (placeLatLng != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, mapZoom))
                map.addMarker(
                    MarkerOptions()
                        .position(placeLatLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmapDescriptorFromVector()))
                )
            }
            searchBar.hideSuggestionsList()

        }.addOnFailureListener {
            showToast(getString(R.string.place_not_found))
        }
    }

    private fun showBottomDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheet = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)

        placeResponse!!.let {
            it.place.priceLevel
            bottomSheet.findViewById<ImageView>(R.id.ivPlacePicture)
            bottomSheet.findViewById<TextView>(R.id.tvPlaceName).text = it.place.name
            bottomSheet.findViewById<TextView>(R.id.tvPlacePrice).text = it.place.address
            bottomSheet.findViewById<TextView>(R.id.tvPlaceInfo).text = it.place.phoneNumber
        }

        dialog.setContentView(bottomSheet)
        dialog.show()
    }


    fun fetchPlacesPredictions(searchString: String) {
        val distance = 5000.0
        val bounds = buildRectangleBounds(latLng, distance)

        val predictionsRequest = FindAutocompletePredictionsRequest.builder()
            .setCountries(listOf("us", "mx"))
            .setLocationBias(bounds)
            .setOrigin(latLng)
            .setSessionToken(autoCompleteSessionToken)
            .setQuery(searchString)
            .build()


        placesClient.findAutocompletePredictions(predictionsRequest)
            .addOnSuccessListener {
                val response = it
                if (response != null) {
                    predictionsList = response.autocompletePredictions
                    val suggestionList = ArrayList<String>()
                    predictionsList.forEach { x ->
                        if (x.placeTypes.contains(Place.Type.RESTAURANT)) {
                            suggestionList.add(
                                x.getFullText(null).toString()
                            )
                        }
                    }
                    searchBar.lastSuggestions = suggestionList

                    if (!searchBar.isSuggestionsVisible) {
                        searchBar.showSuggestionsList()
                    }
                }
            }
            .addOnFailureListener {
                showToast(getString(R.string.place_not_found))
            }
    }

    companion object {
        fun newInstance() = RestaurantMapFragment()
    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }

    override fun onSearchConfirmed(text: CharSequence?) {
        if (searchBar.isSuggestionsVisible) {
            searchBar.hideSuggestionsList()
        }
    }

    override fun onButtonClicked(buttonCode: Int) {
        if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
            searchBar.closeSearch()
        }
    }

    override fun OnItemClickListener(position: Int, v: View?) {
        if (position < predictionsList.size - 1) {
            val selectedPrediction = predictionsList[position]
            fetchPlaceDetails(selectedPrediction.placeId)
        }
    }

    override fun OnItemDeleteListener(position: Int, v: View?) {

    }

    override fun onMarkerClick(p0: Marker): Boolean {
        showBottomDialog()
        return true
    }
}

