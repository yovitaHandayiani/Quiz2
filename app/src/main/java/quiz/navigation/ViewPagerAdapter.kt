package quiz.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter (fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
//    private lateinit var listOfData: List<OnBoardingData>
//
//    init {
//        listOfData = listOf(
//            OnBoardingData("Welcome", "Discover amazing features", R.drawable.dimsums, "Next"),
//            OnBoardingData("Explore", "Find what you need quickly", R.drawable.dimsumd, "Next"),
//            OnBoardingData("Get Started", "Join our community today", R.drawable.dimsumt, "Get Started")
//        )
//    }
//
//    override fun getItemCount(): Int = listOfData.size // Number of tabs
//
//    override fun createFragment(position: Int): Fragment {
//        return FragmentOnBoardingSatu.newInstance(listOfData[position])
//    }

    override fun getItemCount(): Int = OnBoardingDataProvider.onBoardingItems.size

    override fun createFragment(position: Int): Fragment {
        return FragmentOnBoardingSatu.newInstance(OnBoardingDataProvider.onBoardingItems[position])
    }
}