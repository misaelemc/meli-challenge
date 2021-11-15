package com.mmunoz.meli.ui.activities

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.mmunoz.base.TestIdlingResource
import com.mmunoz.meli.R
import com.mmunoz.meli.data.helpers.MockServer
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var mockServer: MockWebServer

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start(8080)
        IdlingRegistry.getInstance().register(TestIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(TestIdlingResource.countingIdlingResource)
    }

    @Test
    fun openingActivityAndShowSearchAnimation() {
        launchActivity()
        onView(withId(R.id.animationView_search_empty))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchingSomethingAndDisplayingResults() {
        launchActivity()
        searchSomething()
    }

    @Test
    fun searchingByCategoryId() {
        launchActivity()
        searchSubCategory()
    }

    @Test
    fun searchingByCategoryIdAndClearSelection() {
        launchActivity()

        searchSubCategory()

        onView(withId(R.id.item_clear))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.animationView_search_empty))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchingSomethingAndClearText() {
        launchActivity()
        searchSomething()

        onView(withId(R.id.imageButton_delete))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.animationView_search_empty))
            .check(matches(isDisplayed()))
    }


    @Test
    fun showingErrorViewWhenAnErrorHappen() {
        launchActivity(true)
        searchSomething()

        onView(withId(R.id.search_error_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun openingProductDetailFromSearch() {
        launchActivity()
        searchSomething()

        onView(withId(R.id.recyclerView_search))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.layout_product_detail))
            .check(matches(isDisplayed()))
    }

    @Test
    fun openingProductDetailAndCloseIt() {
        launchActivity()
        searchSomething()

        onView(withId(R.id.recyclerView_search))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.imageViewButton_back))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    @Test
    fun showingErrorViewWhenTryingToSeeCategories() {
        launchActivity(true)
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.category_error_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun showingErrorViewWhenTryingToSeeSubCategories() {
        launchActivity()
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        mockServer.setDispatcher(MockServer.ErrorDispatcher())

        onView(withId(R.id.recyclerView_categories))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.sub_category_error_view))
            .check(matches(isDisplayed()))
    }

    private fun searchSubCategory() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        onView(withId(R.id.recyclerView_categories))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.recyclerView_sub_categories))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
    }

    private fun searchSomething() {
        onView(withId(R.id.editText_search))
            .perform(clearText(), typeText("Sensor"))
            .perform(pressImeActionButton())
    }

    private fun launchActivity(withError: Boolean = false) {
        val dispatcher =
            if (!withError) MockServer.ResponseDispatcher() else MockServer.ErrorDispatcher()
        mockServer.setDispatcher(dispatcher)
        ActivityScenario.launch(MainActivity::class.java)
    }
}