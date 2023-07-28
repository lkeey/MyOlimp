package com.developement.myolimp.feature_onborading.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developement.myolimp.feature_onborading.models.FragmentImg

class OnBoardingViewModel : ViewModel() {

    private val _items = mutableStateListOf<FragmentImg>()
    val items: SnapshotStateList<FragmentImg> = _items

    fun addItem(item: FragmentImg) {
        _items.add(item)
    }

    fun deleteAllItems() {
        _items.clear()
    }
}
