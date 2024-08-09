package com.eggbucket.eggbucket_android.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    private val _amount = MutableLiveData<String>()
    val amount: LiveData<String> get() = _amount

    private val _pendingItems = MutableLiveData<String>()
    val pendingItems: LiveData<String> get() = _pendingItems

    fun setAmount(amount: String) {
        _amount.value = amount
    }

    fun setPendingItems(pendingItems: String) {
        _pendingItems.value = pendingItems
    }
}