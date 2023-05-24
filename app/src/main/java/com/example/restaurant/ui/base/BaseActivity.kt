package com.example.restaurant.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.example.restaurant.ui.dialogs.ProgressBarDialogFragment
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    val disposable: CompositeDisposable = CompositeDisposable()
    lateinit var binding: VB

    @IdRes
    var fragmentContainerId: Int? = null
    private var progressBarDialog = ProgressBarDialogFragment()

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }


    override fun onBackPressed() {
        var handled = false
        for (fragment: Fragment in supportFragmentManager.fragments) {
            if (fragment is BaseFragment<*>) {
                handled = fragment.onBackPressed()
                if (handled) {
                    break
                }
            }
        }
        if (!handled) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun navigateTo(fragment: Fragment, fragmentTag: String, addToBackStack: Boolean) {
        fragmentContainerId ?: throw IllegalStateException("set fragmentContainerId first")
        fragmentContainerId?.let {
            supportFragmentManager.beginTransaction().apply {
                replace(it, fragment)
                if (addToBackStack) {
                    addToBackStack(fragmentTag)
                }
                commit()
            }
        }
    }

    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true) {
        navigateTo(fragment, fragment::class.java.simpleName, addToBackStack)
    }

    fun backToPrevious() {
        supportFragmentManager.popBackStack()
    }

    fun backTo(fragmentTag: String?, inclusive: Boolean) {
        val flags = if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0
        supportFragmentManager.popBackStack(fragmentTag, flags)
    }

    fun backTo(fragmentClass: Class<BaseFragment<*>>, inclusive: Boolean = false) {
        backTo(fragmentClass.simpleName, inclusive)
    }

    fun getRootView(): View {
        return findViewById<View>(android.R.id.content)
    }

    fun showProgressBar() {
        progressBarDialog.apply {
            isCancelable = false
        }.show(supportFragmentManager, "BaseActivityProgressBar")
    }

    fun dismissProgressBar() {
        progressBarDialog.dismiss()
    }


}