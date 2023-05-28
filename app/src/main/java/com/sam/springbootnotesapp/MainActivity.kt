package com.sam.springbootnotesapp

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sam.springbootnotesapp.feature_authentication.presentation.google_authentication.GoogleAuthClient
import com.sam.springbootnotesapp.feature_authentication.presentation.login.LoginScreen
import com.sam.springbootnotesapp.feature_authentication.presentation.login.LoginViewModel
import com.sam.springbootnotesapp.feature_authentication.presentation.profile.ProfileScreen
import com.sam.springbootnotesapp.feature_authentication.presentation.sign_up.SignUpScreen
import com.sam.springbootnotesapp.feature_authentication.presentation.sign_up.SignUpViewModel
import com.sam.springbootnotesapp.feature_authentication.presentation.welcome.WelcomeScreen
import com.sam.springbootnotesapp.feature_notes.presentation.dashboard.DashBoard
import com.sam.springbootnotesapp.feature_notes.presentation.dashboard.DashBoardViewModel
import com.sam.springbootnotesapp.feature_notes.presentation.add_note.AddNoteScreen
import com.sam.springbootnotesapp.feature_notes.presentation.search_note.SearchNoteViewModel
import com.sam.springbootnotesapp.feature_notes.presentation.search_note.SearchScreen
import com.sam.springbootnotesapp.ui.theme.SpringBootNotesAppTheme
import com.sam.springbootnotesapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthClient: GoogleAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpringBootNotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MyApp(
                        googleAuthClient = googleAuthClient,
                        context = applicationContext
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyApp(
    googleAuthClient: GoogleAuthClient,
    context: Context
) {
    val auth = Firebase.auth

    val navController = rememberAnimatedNavController()
    LaunchedEffect(key1 = Unit) {
        if (auth.currentUser != null) {
            navController.navigate(Routes.DASHBOARD) {
                popUpTo(Routes.AUTHENTICATION) {
                    inclusive = true
                }
            }
        }
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Routes.AUTHENTICATION
    ) {
        navigation(
            startDestination = Routes.WELCOME,
            route = Routes.AUTHENTICATION
        ) {
            composable(
                route = Routes.WELCOME,
                exitTransition = {
                    scaleOutExitTransition()
                },
                popEnterTransition = {
                    scaleInPopEnterTransition()
                }
            ) {
                WelcomeScreen(
                    navigate = {
                        navController.navigate(it)
                    }
                )
            }
            composable(
                route = Routes.LOGIN,
                enterTransition = {
                    scaleInEnterTransition()
                },
                popExitTransition = {
                    scaleOutExitTransition()
                }
            ) {
                val scope = rememberCoroutineScope()
                val viewModel: LoginViewModel = hiltViewModel()
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == RESULT_OK) {
                            scope.launch {
                                val signInResult = googleAuthClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.signInWithGoogle(signInResult)
                            }
                        }
                    }
                )
                LoginScreen(
                    onGoogleSignIn = {
                        scope.launch {
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    googleAuthClient.signIn() ?: return@launch
                                ).build()
                            )
                        }
                    },
                    viewModel = viewModel,
                    onBackClicked = {
                        navController.popBackStack()
                    },
                    onLoginClicked = {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.AUTHENTICATION) {
                                inclusive = true
                            }
                        }
                    },
                    context = context
                )
            }
            composable(
                route = Routes.SIGN_UP,
                enterTransition = {
                    scaleInEnterTransition()
                },
                popExitTransition = {
                    scaleOutPopExitTransition()
                }
            ) {
                val viewModel: SignUpViewModel = hiltViewModel()
                SignUpScreen(
                    viewModel = viewModel,
                    onSignUpClicked = {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.AUTHENTICATION) {
                                inclusive = true
                            }
                        }
                    },
                    popBackStack = {
                        navController.popBackStack()
                    },
                    context = context
                )
            }
        }
        composable(
            route = Routes.DASHBOARD,
            enterTransition = {
                scaleInEnterTransition()
            },
            exitTransition = {
                scaleOutExitTransition()
            },
            popEnterTransition = {
                scaleInPopEnterTransition()
            }
        ) {
            val dashBoardViewModel: DashBoardViewModel = hiltViewModel()
            DashBoard(
                viewModel = dashBoardViewModel,
                onAddNote = {
                    navController.navigate(it)
                },
                onPhotoClicked = {
                    navController.navigate(Routes.PROFILE)
                },
                context = context,
                onNoteClicked = {
                    navController.navigate(Routes.ADD_NOTE + "?id=$it")
                },
                onSearchClicked = {
                    navController.navigate(Routes.SEARCH)
                }
            )
        }
        composable(
            route = Routes.SEARCH,
            enterTransition = {
                scaleInEnterTransition()
            },
            popExitTransition = {
                scaleOutPopExitTransition()
            },
            exitTransition = {
                scaleOutExitTransition()
            }
        ) {
            val viewModel = hiltViewModel<SearchNoteViewModel>()
            SearchScreen(
                viewModel = viewModel,
                onBackClicked = {
                    navController.popBackStack()
                },
                onDeleteClicked = {
                    navController.navigate(Routes.ADD_NOTE + "?id=$it")
                }
            )
        }
        composable(
            route = Routes.PROFILE,
            enterTransition = {
                scaleInEnterTransition()
            },
            popExitTransition = {
                scaleOutPopExitTransition()
            },
            exitTransition = {
                scaleOutExitTransition()
            }
        ) {
            ProfileScreen(
                context = context
            ) {
                navController.navigate(it) {
                    popUpTo(Routes.DASHBOARD) {
                        inclusive = true
                    }
                }
            }
        }
        composable(
            route = Routes.ADD_NOTE + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    defaultValue = -1
                }
            ),
            enterTransition = {
                scaleInEnterTransition()
            },
            popExitTransition = {
                scaleOutPopExitTransition()
            }
        ) {
            AddNoteScreen(
                popBack = {
                    navController.popBackStack()
                },
                context = context
            )
        }
    }
}


