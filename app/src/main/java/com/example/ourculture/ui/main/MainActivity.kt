package com.example.ourculture.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ourculture.R
import com.example.ourculture.databinding.ActivityMainBinding
import com.example.ourculture.ui.detection.DetectionActivity
import com.example.ourculture.ui.detection.DetectionFragment
import com.example.ourculture.ui.home.HomeFragment
import com.example.ourculture.ui.insertmarketplace.InsertMarketplaceActivity
import com.example.ourculture.ui.insertmarketplace.InsertMarketplaceFragment
import com.example.ourculture.ui.login.LoginActivity
import com.example.ourculture.ui.marketplace.MarketplaceFragment
import com.example.ourculture.ui.setting.ProfileActivity
import com.example.ourculture.ui.wishlist.WishlistFragment
import com.example.ourculture.util.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                if (!allPermissionsGranted()) {
                    requestPermissionLauncher.launch(REQUIRED_PERMISSION)
                }
                binding.progressBar.visibility = View.GONE
                binding.appBarLayout.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
//                val fragmentManager = supportFragmentManager
//                val homeFragment = HomeFragment()
//                val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
//                if (fragment !is HomeFragment) {
//                    fragmentManager
//                        .beginTransaction()
//                        .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
//                        .commit()
//                }

                binding.bottomNavigation.setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.navigation_home -> {
                            replaceFragment(HomeFragment())
                            true
                        }
                        R.id.navigation_camera -> {
                            replaceFragment(DetectionFragment())

//                            startActivity(Intent(this, DetectionActivity::class.java))
                            true
                        }
                        R.id.navigation_marketplace -> {
                            replaceFragment(MarketplaceFragment())
                            true
                        }
                        R.id.navigation_upload_market -> {
                            replaceFragment(InsertMarketplaceFragment())
                            InsertMarketplaceFragment.setOnItemClickCallback(object: InsertMarketplaceFragment.OnItemClickCallback{
                                override fun onItemClicked() {
                                    binding.bottomNavigation.selectedItemId = R.id.navigation_marketplace
                                }

                            })
                            true
                        }
                        R.id.navigation_wishlist -> {
                            replaceFragment(WishlistFragment())
                            true
                        }
                        else -> false
                    }
                }

            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_logout -> {
                    viewModel.logout()
                    finish()
                    true
                }
                R.id.menu_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}