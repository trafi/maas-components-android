package com.trafi.sample

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trafi.core.ApiResult
import com.trafi.ui.theme.CornerRadius
import com.trafi.ui.theme.MaasTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalCoroutinesApi::class,
)
@Composable
fun FirebaseAuthSampleApp(
    viewModel: SampleViewModel,
) {
    val user by viewModel.user.collectAsState()
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    var errorText by remember { mutableStateOf(defaultErrorMessage) }

    // workaround for animation being interrupted by new anchors
    // 1. errorText is updated
    // 2. sheetState.show() starts animation
    // 3. Swipeable.kt:535 updates anchors due to change in errorText and interrupts
    fun showBottomSheetAfterRecomposition() = scope.launch {
        delay(1)
        sheetState.show()
    }

    fun showError(failure: Error.Failure) {
        errorText = failure.result.developerMessage ?: defaultErrorMessage
        showBottomSheetAfterRecomposition()
    }

    fun showError(error: Error.Message) {
        errorText = error.message ?: defaultErrorMessage
        showBottomSheetAfterRecomposition()
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = MaasTheme.colors.surface,
        sheetShape = RoundedCornerShape(topStart = CornerRadius.xl, topEnd = CornerRadius.xl),
        sheetContent = {
            val composableScope = rememberCoroutineScope()
            AlertBottomSheet(errorText, onClick = {
                composableScope.launch {
                    sheetState.hide()
                }
            })
        },
        content = {
            NavHost(navController, startDestination = "welcome") {
                composable("welcome") {
                    WelcomeScreen(
                        inProgress = viewModel.actionInProgress == ActionInProgress.SignIn,
                        onLaterClick = { showError(Error.Message()) },
                        onContinueWithGoogleClick = { viewModel.onContinueWithGoogleClick() },
                    )
                }
                composable("profile") {
                    ProfileScreen(
                        profile = user?.profile,
                        updateInProgress = viewModel.actionInProgress == ActionInProgress.UpdateProfile,
                        onUpdateProfileClick = { viewModel.updateProfile(it) },
                        onLogOutClick = { viewModel.signOut() },
                        onCorruptTokenClick = { viewModel.corruptToken() },
                    )
                }
            }
        })

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
            if (controller.currentRoute == "welcome") viewModel.onWelcomeScreenOpen()
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }

    LaunchedEffect(Unit) {
        viewModel.userSignedIn.collect { signedIn ->
            if (signedIn) {
                sheetState.hide()
                navController.navigate("profile")
            } else {
                navController.popBackStack()
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.error.collect { result ->
            when (result) {
                is Error.Failure -> showError(result)
                is Error.Message -> showError(result)
            }
        }
    }
}

private const val defaultErrorMessage = "Oh, dear\nOops, something went wrong"

private val <T : Any> ApiResult.Failure<T>.developerMessage: String?
    get() = when (this) {
        is ApiResult.Failure.Unauthorized -> error?.developerMessage
        is ApiResult.Failure.Forbidden -> error?.developerMessage
        is ApiResult.Failure.Error -> error?.developerMessage
        is ApiResult.Failure.UserError -> error.developerMessage
        is ApiResult.Failure.MspError -> error.developerMessage
        is ApiResult.Failure.Generic -> throwable.message
    }

private val NavController.currentRoute: String?
    get() = currentBackStackEntry?.destination?.route
