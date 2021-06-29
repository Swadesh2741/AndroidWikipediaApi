package com.example.wikipediaapitest.ui.viewmodel

import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wikipediaapitest.data.repository.WikiPagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.wikipediaapitest.base.BaseViewModel
import com.example.wikipediaapitest.data.model.WikiPages
import com.example.wikipediaapitest.util.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: WikiPagesRepository) : BaseViewModel() {

    val pages = MutableLiveData<List<WikiPages>>();
    val error = MutableLiveData<String>()
    val loading= MutableLiveData<Boolean>();
    val offlineMessage = MutableLiveData<Boolean>();

     fun setSearchedTitle(title: String) {
        viewModelScope.launch {
         //   repository.setCurrentTitle(title)
            repository.getPages(title).collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        showLoading(false)
                        if (!it.data.isNullOrEmpty()) {
                           pages.postValue(it.data!!);
                        }

                    }
                    Resource.Status.ERROR -> {
                        showLoading(false)
                        repository.getAllOfflineData().collect {
                            if (!it.isNullOrEmpty()) {
                                showMessage(true)
                                pages.postValue(it!!);
                            }else{
                                error.postValue("error while fetching data")
                            }
                            }
                    }

                    Resource.Status.LOADING -> {
                        showLoading(true)
                    }
                }
            }
        }
    }

    fun showMessage(value:Boolean){
        offlineMessage.postValue(value)
    }

    fun showLoading(value:Boolean){
        loading.postValue(value);
    }
}