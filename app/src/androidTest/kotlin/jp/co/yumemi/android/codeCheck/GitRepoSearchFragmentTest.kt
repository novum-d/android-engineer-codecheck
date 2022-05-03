package jp.co.yumemi.android.codeCheck

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.Navigation.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.codeCheck.app.TopActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GitRepoSearchFragmentTest {

    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<TopActivity>()

    private lateinit var activity: ComponentActivity

    @Before
    fun navigateToPlantDetailFragment() {
        composeTestRule.activityRule.scenario.onActivity { topActivity ->
            activity = topActivity
            findNavController(activity, R.id.nav_host_fragment).navigate(R.id.gitRepoDetailFragment)
        }
    }

    @Test
    fun testPlantName() {
        composeTestRule.onNodeWithText("CodeCheck").assertIsDisplayed()
    }
}
