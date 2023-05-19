package com.example.restaurant.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.restaurant.R
import com.example.restaurant.databinding.FragmentHomeBinding
import com.example.restaurant.ui.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
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

        setTitle(R.string.app_name)
        initializeUi()

    }


    private fun initializeUi() {
        showToolbar()
        binding.btnNavigateToFind.setOnClickListener {
            listener?.navigateToMap()

        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    interface Listener {
        fun navigateToMap()
    }
}