package com.mmunoz.meli

import android.os.Bundle
import com.mmunoz.meli.categories.api.CategoriesFeatureLoader
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var categoriesFeatureLoader: CategoriesFeatureLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, categoriesFeatureLoader.getFragment(), "categories")
        }.commit()
    }
}