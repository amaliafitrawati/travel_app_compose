package com.dicoding.jetpacksubmission

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import org.junit.Rule
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetpacksubmission.model.PlaceData
import com.dicoding.jetpacksubmission.ui.navigation.Screen
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TravelAppTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetpackSubmissionTheme{
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                TravelApp(navController = navController)
            }
        }
    }

    private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("listPlace").performScrollToIndex(5)
        composeTestRule.onNodeWithText(PlaceData.places[5].name).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(PlaceData.places[5].name).assertIsDisplayed()
    }

    @Test
    fun navHost_clickItem_navigatesToBackPage() {
        composeTestRule.onNodeWithTag("listPlace").performScrollToIndex(6)
        composeTestRule.onNodeWithText(PlaceData.places[6].name).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun clickFavorite_displayFavoriteScreen() {
        composeTestRule.onNodeWithText(PlaceData.places[3].name).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithTag("favoriteBtn").performClick()
        composeTestRule.onNodeWithTag("backBtn").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithText(text = composeTestRule.activity.getString(R.string.menu_favorite),
            ignoreCase = true).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(PlaceData.places[3].name).assertIsDisplayed()
    }

    @Test
    fun undoClickFavorite_displayFavoriteScreen(){
        composeTestRule.onNodeWithText(PlaceData.places[0].name).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithTag("favoriteBtn").performClick()
        composeTestRule.onNodeWithTag("favoriteBtn").performClick()
        composeTestRule.onNodeWithTag("backBtn").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithText(text = composeTestRule.activity.getString(R.string.menu_favorite),
            ignoreCase = true).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithTag("emptyData").assertIsDisplayed()
    }




}