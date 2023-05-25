package org.d3if3038.assesment1

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import org.d3if3038.assesment1.data.SettingDataStore
import org.d3if3038.assesment1.data.dataStore

class OnBoardingActivity : AppIntro2() {

    private val settingDataStore: SettingDataStore by lazy {
        SettingDataStore(applicationContext.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        // Flags for the onboarding page
        // prevent user from clicking back button
        isSystemBackButtonLocked = true
        isColorTransitionsEnabled = true

        setIndicatorColor(
            selectedIndicatorColor = Color.parseColor("#048ABF"),
            unselectedIndicatorColor = Color.parseColor("#4F4F4F")
        )

        // Set a new transition
        setTransformer(AppIntroPageTransformerType.Parallax(
            titleParallaxFactor = 1.0,
            imageParallaxFactor = -1.0,
            descriptionParallaxFactor = 2.0
        ))

        addSlide(AppIntroFragment.createInstance(
            title = getString(R.string.selamat_datang),
            titleTypefaceFontRes = R.font.poppins,
            titleColorRes = R.color.orange_700,
            descriptionColorRes = R.color.black,
            backgroundColorRes = R.color.orange_opaque,
            imageDrawable = R.drawable.art_welcome,
            description = getString(R.string.onboarding_desc_1),
            descriptionTypefaceFontRes = R.font.poppins,
        ))

        addSlide(AppIntroFragment.createInstance(
            title = getString(R.string.artikel),
            titleTypefaceFontRes = R.font.poppins,
            titleColorRes = R.color.blue_700,
            descriptionColorRes = R.color.black,
            backgroundColorRes = R.color.blue_opaque,
            imageDrawable = R.drawable.art_reading,
            description = getString(R.string.onboarding_desc_2),
            descriptionTypefaceFontRes = R.font.poppins,
        ))

        addSlide(AppIntroFragment.createInstance(
            title = "Kolaborasi",
            titleTypefaceFontRes = R.font.poppins,
            titleColorRes = R.color.red_700,
            descriptionColorRes = R.color.black,
            backgroundColorRes = R.color.red_opaque,
            imageDrawable = R.drawable.umm_logo,
            description = "Pembuatan Aplikasi ini Berkolaborasi dengan Universitas Muhammadiyah Malang.",
            descriptionTypefaceFontRes = R.font.poppins,
        ))

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        doneOnboarding()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        doneOnboarding()
        finish()
    }

    private fun doneOnboarding() {
        settingDataStore.putBoolean("is_boarding", true)
    }
}