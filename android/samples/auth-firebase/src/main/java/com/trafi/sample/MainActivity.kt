package com.trafi.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.trafi.sample.GoogleSignInAction.OneTapSignIn
import com.trafi.sample.GoogleSignInAction.SignIn
import com.trafi.sample.GoogleSignInAction.SignOut
import com.trafi.sample.idp.GoogleIdentityProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: SampleViewModel by viewModels()
    private val googleIdp = GoogleIdentityProvider(
        activity = this,
        onSignInSuccess = { viewModel.onSignInSuccess(it) },
        onSignInCanceled = { viewModel.onSignInCanceled() },
        onSignInError = { viewModel.onSignInError(it) },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.googleSignInAction.collect { action ->
                when (action) {
                    OneTapSignIn -> googleIdp.oneTapSignIn()
                    SignIn -> googleIdp.signIn()
                    SignOut -> googleIdp.signOut()
                }
            }
        }
        setContent {
            FirebaseAuthSampleApp(
                viewModel = viewModel,
            )
        }
    }
}
