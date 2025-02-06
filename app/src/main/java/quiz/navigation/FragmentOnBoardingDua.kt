package quiz.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import quiz.navigation.databinding.FragmentOnBoardingDuaBinding
import quiz.navigation.databinding.FragmentOnBoardingSatuBinding


class FragmentOnBoardingDua : Fragment() {
    private lateinit var binding: FragmentOnBoardingDuaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingDuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNextDua.setOnClickListener {
            val mFragmentManager = parentFragmentManager
            mFragmentManager
                .beginTransaction().apply {
                    replace(
                        R.id.onBoarding,
                        FragmentOnBoardingTiga(),
                        FragmentOnBoardingTiga::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
        }

    }

}