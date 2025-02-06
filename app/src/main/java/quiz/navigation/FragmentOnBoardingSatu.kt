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
        binding.btnNextSatu.setOnClickListener {
            val mFragmentManager = parentFragmentManager
            mFragmentManager
                .beginTransaction().apply {
                    replace(
                        R.id.onBoarding,
                        FragmentOnBoardingDua(),
                        FragmentOnBoardingDua::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
        }

    }
}