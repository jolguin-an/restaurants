package com.example.restaurant.ui.places

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurant.R
import com.example.restaurant.databinding.FragmentNearbyPlacesBinding
import com.example.restaurant.domain.models.Result
import com.example.restaurant.domain.models.ResultsItem
import com.example.restaurant.extensions.addTo
import com.example.restaurant.ui.base.BaseFragment
import com.example.restaurant.utils.FLAG_KEY_SAVED_ELEMENTS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class NearbyPlacesFragment :
    BaseFragment<FragmentNearbyPlacesBinding>(FragmentNearbyPlacesBinding::inflate) {

    private var adapter: NearbyPlacesListAdapter? = null
    private val viewModel by viewModel<NearbyPlacesViewModel>()
    private var listener: Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException(getString(R.string.fragment_not_implemented))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.near_places)
        loadLocalData()

    }

    private fun loadLocalData() {
        viewModel.getSavedPlaces().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is Result.Success<List<ResultsItem>> -> {
                        binding.placesListRecyclerView.layoutManager = LinearLayoutManager(context)
                        adapter = NearbyPlacesListAdapter(it.data)
                        binding.placesListRecyclerView.adapter = adapter
                    }
                    else -> {
                        //TODO saveFlagToSharedPreferences(false, FLAG_KEY_SAVED_ELEMENTS)
                    }
                }
            }.addTo(disposable)
    }


    companion object {
        fun newInstance() = NearbyPlacesFragment()
    }

    interface Listener {
    }
}