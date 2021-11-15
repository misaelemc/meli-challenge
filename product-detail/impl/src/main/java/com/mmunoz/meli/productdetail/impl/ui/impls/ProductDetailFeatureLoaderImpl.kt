package com.mmunoz.meli.productdetail.impl.ui.impls

import androidx.appcompat.app.AppCompatActivity
import com.mmunoz.meli.productdetail.api.ProductDetailFeatureLoader
import com.mmunoz.meli.productdetail.api.data.models.Product
import com.mmunoz.meli.productdetail.impl.ui.fragments.ProductDetailBottomSheet
import javax.inject.Inject

class ProductDetailFeatureLoaderImpl @Inject constructor() : ProductDetailFeatureLoader {

    override fun showBottomSheet(activity: AppCompatActivity, product: Product) {
        activity.supportFragmentManager.takeIf { !it.isDestroyed }?.run {
            if (findFragmentByTag(PRODUCT_DETAIL_TAG) == null) {
                ProductDetailBottomSheet.newInstance(product).apply {
                    show(activity.supportFragmentManager, PRODUCT_DETAIL_TAG)
                    executePendingTransactions()
                }
            }
        }
    }

    companion object {
        private const val PRODUCT_DETAIL_TAG = "product_detail_tag"
    }
}