package com.mmunoz.meli.di.modules

import com.mmunoz.base.di.scopes.ActivityScope
import com.mmunoz.meli.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}