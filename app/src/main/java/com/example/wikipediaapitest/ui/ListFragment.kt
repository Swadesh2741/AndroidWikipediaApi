package com.example.wikipediaapitest.ui

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikipediaapitest.R
import com.example.wikipediaapitest.adapter.WikiListAdapter
import com.example.wikipediaapitest.databinding.ListFragmentBinding
import com.example.wikipediaapitest.ui.viewmodel.ListViewModel
import com.example.wikipediaapitest.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import icom.example.wikipediaapitest.ui.navigator.Navigator
import com.example.wikipediaapitest.base.BaseFragment
import com.example.wikipediaapitest.util.isInternetConnected
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment @Inject constructor(): BaseFragment() {

    override val layoutId: Int = R.layout.list_fragment
    private lateinit var binding:ListFragmentBinding



    private  val listViewModel: ListViewModel by activityViewModels();

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var wikiListAdapter: WikiListAdapter
    private val wikiUrl : String = "https://en.m.wikipedia.org/wiki/"
    private val DEFAULT_SEARCH_TITLE ="Sachin"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ListFragmentBinding.bind(view)

        intiRecyclerView()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            listViewModel.setSearchedTitle(DEFAULT_SEARCH_TITLE);
        }

        listViewModel.pages.observe(viewLifecycleOwner){
            binding.recyclerview.isVisible = true
            wikiListAdapter.submitList(it)
        }

        listViewModel.error.observe(viewLifecycleOwner){
            showLoading(false)
            showNoDataMessage(true)
        }
        listViewModel.loading.observe(viewLifecycleOwner){
            val intternet = requireActivity().isInternetConnected()
            val show = it && requireActivity().isInternetConnected()
            binding.recyclerview.isVisible = !show
            showLoading(show)
        }

        listViewModel.offlineMessage.observe(viewLifecycleOwner){
            if(requireActivity().isInternetConnected()){
                showToast(getString(R.string.no_search_results))
            }else{
                showToast(getString(R.string.no_connection))
            }
        }

        wikiListAdapter.setOnContainerClickListener {
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireActivity(), Uri.parse(wikiUrl+it.title))
        }


    }

    private fun intiRecyclerView() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this.context)
                addItemDecoration(
                    DividerItemDecoration(
                    this.context,
                       LinearLayout.VERTICAL
                )
                )
                wikiListAdapter = WikiListAdapter()
                adapter = wikiListAdapter
            }
        }
    }

    private fun showToast(msg:String){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(showLoading: Boolean) {
        binding.shimmerFrameLayout.isVisible = showLoading
        if (showLoading) {
            binding.shimmerFrameLayout.startShimmer()
        }
        else {
            binding.shimmerFrameLayout.stopShimmer()
        }
    }

    private fun showNoDataMessage(show: Boolean) {
        if (show) {
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerFrameLayout.stopShimmer()
    }
}
