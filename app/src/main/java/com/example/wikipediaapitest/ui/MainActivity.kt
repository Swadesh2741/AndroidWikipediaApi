package com.example.wikipediaapitest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wikipediaapitest.R
import com.example.wikipediaapitest.ui.viewmodel.ListViewModel
import com.example.wikipediaapitest.util.isInternetConnected
import dagger.hilt.android.AndroidEntryPoint
import icom.example.wikipediaapitest.ui.navigator.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener{
    @Inject
    lateinit var navigator: Navigator
    private  val listViewModel: ListViewModel by viewModels()
    private lateinit var navHostFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = requireNotNull(supportFragmentManager.findFragmentById(R.id.fragmentContainer))

        val navController = navHostFragment.findNavController()
        navigator.navController = navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navListScreen))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!TextUtils.isEmpty(query)){
            if(isInternetConnected())
            listViewModel.setSearchedTitle(query!!)
            else
                Toast.makeText(this,R.string.no_connection,Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }

    override fun onBackPressed() {
        handleBackButton()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            handleBackButton();
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun handleBackButton(){
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0) {
            navigator.navigateBack()
        } else {
            finish()
        }
    }
}