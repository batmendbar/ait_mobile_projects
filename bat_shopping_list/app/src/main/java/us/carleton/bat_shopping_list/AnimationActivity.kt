package us.carleton.bat_shopping_list

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import us.carleton.bat_shopping_list.databinding.SplashScreenBinding

class AnimationActivity : AppCompatActivity() {
    lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rotateAnimation = AnimationUtils.loadAnimation(this,
            R.anim.test_rotate)

        rotateAnimation.setAnimationListener(
            object: Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    val intent = Intent(this@AnimationActivity, ScrollingActivity::class.java)
                    this@AnimationActivity.startActivity(intent)
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }
            }
        )

        binding.root.startAnimation(rotateAnimation)
    }
}