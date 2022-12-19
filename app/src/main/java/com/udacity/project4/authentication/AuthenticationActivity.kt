package com.udacity.project4.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityAuthenticationBinding
import com.udacity.project4.locationreminders.RemindersActivity

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAuthenticationBinding
    companion object{
        const val Tag = "Login Fragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//         Done: Implement the create account and sign in using FirebaseUI, use sign in using email and sign in using Google
        binding = DataBindingUtil.setContentView(this,R.layout.activity_authentication)
        binding.authButton.setOnClickListener{
            launchSignInFlow()
        }
    }
    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), SIGN_IN_RESULT_CODE
        )

    }
//          Done: If the user was authenticated, send him to RemindersActivity

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i(Tag, "Successfully signed in user ")
                Toast.makeText(this, "SignIn Successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, RemindersActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.i(Tag, "Sign in unsuccessful")
                Toast.makeText(this, "SignIn Unsuccessfully ", Toast.LENGTH_LONG).show()
            }
        }
    }
    }

