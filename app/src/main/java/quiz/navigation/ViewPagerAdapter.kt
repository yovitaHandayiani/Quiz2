package quiz.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter (fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3  // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentOnBoardingSatu()  // First tab
            1 -> FragmentOnBoardingDua() // Second tab
            2 -> FragmentOnBoardingTiga()  // Third tab
            else -> FragmentOnBoardingSatu()
        }
    }
}