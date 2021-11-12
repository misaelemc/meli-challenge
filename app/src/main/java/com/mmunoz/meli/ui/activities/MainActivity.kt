package com.mmunoz.meli.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmunoz.base.ui.viewModels.AppViewModel
import com.mmunoz.meli.R
import com.mmunoz.meli.categories.api.CategoriesFeatureLoader
import com.mmunoz.meli.databinding.ActivityMainBinding
import com.mmunoz.meli.search.api.SearchFeatureLoader
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var searchFeatureLoader: SearchFeatureLoader

    @Inject
    lateinit var categoriesFeatureLoader: CategoriesFeatureLoader

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AppViewModel

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setupDrawer()
        setupViewModel()
        addSearchFragment()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        menu = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_clear) {
            binding.toolbar.subtitle = null
            menu?.getItem(0)?.isVisible = false
            viewModel.setAction(AppViewModel.Actions.ClearSearch)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        viewModel.data.observe(this, { action ->
            if (action is AppViewModel.Actions.OnSubCategorySelected) {
                menu?.getItem(0)?.isVisible = true
                binding.toolbar.subtitle = action.name
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        })
    }

    private fun setupDrawer() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_dehaze))
        toggle.setToolbarNavigationClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        binding.drawerLayout.addDrawerListener(toggle)
        addCategoriesFragment()
    }

    private fun addSearchFragment() {
        addFragment(
            binding.container.id,
            searchFeatureLoader.getFragment(),
            SEARCH_TAG
        )
    }

    private fun addCategoriesFragment() {
        addFragment(
            binding.navigationContainer.id,
            categoriesFeatureLoader.getFragment(),
            CATEGORIES_TAG
        )
    }

    private fun addFragment(@IdRes container: Int, fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            add(container, fragment, tag)
        }.commit()
    }

    companion object {
        private const val SEARCH_TAG = "search_tag"
        private const val CATEGORIES_TAG = "categories_tag"
    }

}