package com.mmunoz.meli.data.helpers

import androidx.test.InstrumentationRegistry
import com.mmunoz.meli.categories.impl.data.api.CATEGORIES_API
import com.mmunoz.meli.categories.impl.data.api.SUB_CATEGORIES_API
import com.mmunoz.meli.search.impl.data.api.SEARCH_API
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

private const val STATUS_OK = 200
private const val STATUS_NOT_FOUND = 404
private const val STATUS_BAD_REQUEST = 400
private const val SEARCH_FILE = "search.json"
private const val CATEGORIES_FILE = "categories.json"
private const val SUB_CATEGORIES_FILE = "sub_categories.json"

class MockServer {

    class ResponseDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val response = when {
                request.path.startsWith(CATEGORIES_API) -> {
                    CATEGORIES_FILE.toStringFile()
                }
                request.path.startsWith(SUB_CATEGORIES_API.replace("{id}", "")) -> {
                    SUB_CATEGORIES_FILE.toStringFile()
                }
                request.path.startsWith(SEARCH_API) -> {
                    SEARCH_FILE.toStringFile()
                }
                else -> null
            }
            return if (response != null) {
                MockResponse().setResponseCode(STATUS_OK).setBody(response)
            } else {
                MockResponse().setResponseCode(STATUS_NOT_FOUND)
            }
        }
    }

    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse =
            MockResponse().setResponseCode(STATUS_BAD_REQUEST)
    }
}

fun String.toStringFile(): String {
    val assets = InstrumentationRegistry.getContext().assets
    return assets.open(this).bufferedReader().use { it.readText() }
}