package quiz.navigation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.rive.runtime.kotlin.core.Rive
import quiz.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val stateMachineName = "Login Machine"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Rive.init(this)

        binding.switch1.setOnClickListener {
            if (binding.switch1.isChecked == true){
                binding.Rive.controller.setBooleanState(stateMachineName, "isChecking", true)
            }else{
                binding.Rive.controller.setBooleanState(stateMachineName, "isChecking", false)

            }
        }

        binding.switch2.setOnClickListener {
            if (binding.switch2.isChecked == true){
                binding.Rive.controller.setBooleanState(stateMachineName, "isHandsUp", true)
            }else{
                binding.Rive.controller.setBooleanState(stateMachineName, "isHandsUp", false)

            }
        }

        binding.switch3.setOnClickListener {
            if (binding.switch3.isChecked == true) {
                binding.Rive.controller.fireState(stateMachineName, "trigSuccess");
            }
        }

        binding.switch4.setOnClickListener {
            if (binding.switch4.isChecked == true) {
                binding.Rive.controller.fireState(stateMachineName, "trigFail");
            }
        }







//        binding.email.setOnFocusChangeListener { view, b ->
//            if (b){
//                binding.loginCharacter.controller.setBooleanState(stateMachineName, "isChecking", true)
//            }else{
//                binding.loginCharacter.controller.setBooleanState(stateMachineName, "isChecking", false)
//
//            }
//        }
//
//        binding.passsword.setOnFocusChangeListener { view, b ->
//            if (b){
//                binding.loginCharacter.controller.setBooleanState(stateMachineName, "isHandsUp", true)
//            }else{
//                binding.loginCharacter.controller.setBooleanState(stateMachineName, "isHandsUp", false)
//
//            }
//        }
//
//
//        binding.email.addTextChangedListener(object  : TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//                try {
//                    binding.loginCharacter.controller.setNumberState(stateMachineName, "numLook", p0!!.length.toFloat())
//                } catch (e: Exception) {
//                }
//            }
//
//        })
//
//
//        binding.button.setOnClickListener {
//
//            binding.passsword.clearFocus()
//
//            Handler(mainLooper).postDelayed({
//                if (binding.email.text!!.isNotEmpty() && binding.passsword.text!!.isNotEmpty() &&
//                    ( binding.email.text.toString()=="admin@gmail.com" && binding.passsword.text.toString() == "123456")){
//                    binding.loginCharacter.controller.fireState(stateMachineName, "trigSuccess");
//
//                }else{
//                    binding.loginCharacter.controller.fireState(stateMachineName, "trigFail");
//                }
//            }, 2000)
//        }

    }
}