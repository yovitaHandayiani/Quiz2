package quiz.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import quiz.navigation.databinding.FragmentOnBoardingDuaBinding
import quiz.navigation.databinding.FragmentOnBoardingTigaBinding

class FragmentOnBoardingTiga : Fragment() {
    private lateinit var binding: FragmentOnBoardingTigaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingTigaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java);
            requireActivity().startActivity(intent)
        }

    }
}