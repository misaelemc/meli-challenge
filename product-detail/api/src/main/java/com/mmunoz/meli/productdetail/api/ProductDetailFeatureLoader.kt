package com.mmunoz.meli.productdetail.api

import androidx.appcompat.app.AppCompatActivity
import com.mmunoz.meli.productdetail.api.data.models.Product

interface ProductDetailFeatureLoader {

    fun showBottomSheet(activity: AppCompatActivity, product: Product)
}