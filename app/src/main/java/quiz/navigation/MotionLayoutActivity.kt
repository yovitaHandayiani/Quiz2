package quiz.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import quiz.navigation.databinding.ActivityMotionLayoutBinding

class MotionLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMotionLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.motionButton.setOnClickListener {
            // The toggle behavior is defined in the MotionScene XML
            // but we could also manually control the transition:
            // if (binding.root.progress == 0f) {
            //     binding.root.transitionToEnd()
            // } else {
            //     binding.root.transitionToStart()
            // }
        }
    }
}




//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class MotionLayoutActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_motion_layout)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}