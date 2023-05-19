package com.example.restaurant.ui.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.restaurant.R
import com.example.restaurant.ui.dialogs.ProgressBarDialogFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.maps.android.SphericalUtil
import io.reactivex.disposables.CompositeDisposable


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {


    val disposable: CompositeDisposable = CompositeDisposable()
    private val progressBar: DialogFragment = ProgressBarDialogFragment()


    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun showProgressBar() {
        childFragmentManager.let {
            progressBar.isCancelable = false
            progressBar.show(it, "ShowProgressBar")
        }
    }

    fun dismissProgressBar() {
        progressBar.dismiss()
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun buildRectangleBounds(from: LatLng, distance: Double): RectangularBounds {
        val southWest = SphericalUtil.computeOffset(from, distance, 225.0)
        val northEast = SphericalUtil.computeOffset(from, distance, 45.0)

        return RectangularBounds.newInstance(southWest, northEast)
    }

    fun hideToolbar() {
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.m_toolbar).visibility =
            View.GONE
    }

    fun showToolbar() {
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.m_toolbar).visibility =
            View.VISIBLE
    }

    fun setTitle(fragmentNotImplemented: Int) {
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.m_toolbar).title =
            getString(fragmentNotImplemented)
    }


}