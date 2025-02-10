package quiz.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import quiz.navigation.databinding.FragmentOnBoardingSatuBinding

class FragmentOnBoardingSatu : Fragment() {
    private lateinit var binding: FragmentOnBoardingSatuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingSatuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getParcelable<OnBoardingData>("onBoardingData")
        // Set the data to the views
        if (data != null) {
            binding.tvBoardingTitle.text = data.name
            binding.tvBoardingDescription.text = data.content
            binding.btnNextSatu.text = data.buttonText
        }

        // Use the data (e.g., set text, images, etc.)
        data?.let {
            binding.tvBoardingTitle.text = it.name
            binding.tvBoardingDescription.text = it.content
            binding.ivBoardingIll.setImageResource(it.image)
            binding.btnNextSatu.text = it.buttonText
            // Set image or any other UI elements as needed
        }

        binding.btnNextSatu.setOnClickListener {
            // Get the current position of the ViewPager2 from the activity using the method
            val currentPosition = (requireActivity() as? ActivityOnBoarding)?.getViewPager()?.currentItem ?: 0

            // Calculate the next position
            val nextPosition = currentPosition + 1

            // Check if the next position is within bounds (i.e., not exceeding the number of items)
            if (nextPosition < (requireActivity() as? ActivityOnBoarding)?.getViewPager()?.adapter?.itemCount ?: 0) {
                // Move the ViewPager2 to the next position
                (requireActivity() as? ActivityOnBoarding)?.getViewPager()?.setCurrentItem(nextPosition, true)
            }else if(){
                
            }
        }

//        binding.tvBoardingTitle.setText(arguments?.getString("title"))
//        binding.tvBoardingDescription.setText(arguments?.getString("description"))
//        arguments?.getInt("image")?.let { binding.ivBoardingIll.setImageResource(it) }
//        binding.btnNextSatu.setText(arguments?.getString("buttonText"))
//        binding.btnNextSatu.setOnClickListener {
//
//        }

    }

    companion object {
        fun newInstance(data: OnBoardingData): FragmentOnBoardingSatu {
            val fragment = FragmentOnBoardingSatu()
            val args = Bundle()
            args.putParcelable("onBoardingData", data)  // Pass the individual OnBoardingData object
            fragment.arguments = args
            return fragment
        }
    }

//    companion object {
//        fun newInstance(data: OnBoardingData): FragmentOnBoardingSatu {
//            val fragment = FragmentOnBoardingSatu()
//            val args = Bundle()
//            args.putString("title", data.name)
//            args.putString("description", data.content)
//            args.putInt("image", data.image)
//            args.putString("buttonText", data.buttonText)
//            fragment.arguments = args
//            return fragment
//        }
//    }
}


