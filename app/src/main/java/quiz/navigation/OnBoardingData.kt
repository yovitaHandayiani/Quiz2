package quiz.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnBoardingData(
    val name: String,
    val content: String,
    val image: Int,
    val buttonText: String
) : Parcelable
