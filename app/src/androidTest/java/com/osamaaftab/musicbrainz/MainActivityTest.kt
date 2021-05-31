package com.osamaaftab.musicbrainz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.osamaaftab.musicbrainz.presentation.ui.MainActivity
import com.osamaaftab.musicbrainz.presentation.ui.SplashFragment
import com.osamaaftab.musicbrainz.util.SuccessDispatcher
import com.osamaaftab.musicbrainz.util.viewaction.CustomViewActions
import io.mockk.mockk
import io.mockk.verify
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    private var mockWebServer = MockWebServer()
    private val client: OkHttpClient by inject()

    @JvmField
    @Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Before
    fun setup() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                client
            )
        )
    }

    @Test
    fun OnSearchResultSuccess() {
        launchFragment(R.id.resultFragment)
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.search))
            .perform(CustomViewActions.typeSearchViewText("Albert"))
        onView(withText("Albert"))
            .perform(pressImeActionButton())
        mockWebServer.dispatcher = SuccessDispatcher()
        activityRule.launchActivity(null)
    }

    @Test
    fun OnSearchResultFails() {
        launchFragment(R.id.resultFragment)
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.search))
                .perform(CustomViewActions.typeSearchViewText("Albert"))
        onView(withText("Albert"))
                .perform(pressImeActionButton())
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }
        activityRule.launchActivity(null)
    }

    @Test
    fun OnSplashToResultNevigation() {
        val mockNavController = mockk<NavController>(relaxed = true)
        val firstScenario = launchFragmentInContainer<SplashFragment>()
        firstScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        onView(withId(R.id.navigate_button)).perform(click())
        verify { mockNavController.navigate(R.id.action_splashFragment_to_resultFragment) }
    }

    private fun launchFragment(destinationId: Int, argBundle: Bundle? = null) {
        val launchFragmentIntent = buildLaunchFragmentIntent(destinationId, argBundle)
        activityRule.launchActivity(launchFragmentIntent)
    }

    private fun buildLaunchFragmentIntent(destinationId: Int, argBundle: Bundle?): Intent = NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.main_nevigations)
            .setComponentName(MainActivity::class.java)
            .setDestination(destinationId)
            .setArguments(argBundle)
            .createTaskStackBuilder().intents[0]
}