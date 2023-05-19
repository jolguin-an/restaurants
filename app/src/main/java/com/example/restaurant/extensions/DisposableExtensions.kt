package com.example.restaurant.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


fun Disposable.addTo(disposable: CompositeDisposable) {
    disposable.add(this)
}