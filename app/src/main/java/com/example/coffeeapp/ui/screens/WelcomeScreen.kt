package com.example.coffeeapp.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavController
import com.example.coffeeapp.R
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.theme.LightBrown
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(navController: NavController) {

    val context = LocalContext.current
    val activity = context as Activity
    val scope = rememberCoroutineScope()

    val credentialManager = CredentialManager.create(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(bottom = 80.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.image_splash),
            contentDescription = "Welcome Screen"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = "Fall in Love with Coffee in Blissful Delight",
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Welcome to our Cozy Coffee corner, where every cup is delight for you",
                color = Color.LightGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // -------------------------------------------------
            // LOGIN
            // -------------------------------------------------

            Button(
                onClick = {
                    navController.navigate(Routes.LoginScreen)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBrown
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -------------------------------------------------
            // REGISTER
            // -------------------------------------------------

            Button(
                onClick = {
                    navController.navigate(Routes.RegisterScreen)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "Register",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -------------------------------------------------
            // GOOGLE SIGN IN
            // -------------------------------------------------

            Button(
                onClick = {

                    scope.launch {

                        try {

                            val googleIdOption =
                                GetGoogleIdOption.Builder()
                                    .setServerClientId(
                                        context.getString(
                                            R.string.default_web_client_id
                                        )
                                    )
                                    .setFilterByAuthorizedAccounts(false)
                                    .build()

                            val request =
                                GetCredentialRequest.Builder()
                                    .addCredentialOption(googleIdOption)
                                    .build()

                            val result =
                                credentialManager.getCredential(
                                    context = context,
                                    request = request
                                )

                            val credential = result.credential

                            if (
                                credential is CustomCredential &&
                                credential.type ==
                                GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                            ) {

                                val googleIdTokenCredential =
                                    GoogleIdTokenCredential.createFrom(
                                        credential.data
                                    )

                                val firebaseCredential =
                                    GoogleAuthProvider.getCredential(
                                        googleIdTokenCredential.idToken,
                                        null
                                    )

                                FirebaseAuth.getInstance()
                                    .signInWithCredential(firebaseCredential)
                                    .addOnCompleteListener { task ->

                                        if (task.isSuccessful) {

                                            navController.navigate(
                                                Routes.HomeScreen
                                            ) {
                                                popUpTo(Routes.WelcomeScreen) {
                                                    inclusive = true
                                                }
                                            }

                                        } else {

                                            android.widget.Toast
                                                .makeText(
                                                    context,
                                                    task.exception?.message
                                                        ?: "Google sign-in failed",
                                                    android.widget.Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                    }
                            }

                        } catch (e: Exception) {

                            e.printStackTrace()

                            android.widget.Toast
                                .makeText(
                                    context,
                                    e.message ?: "Google sign-in failed",
                                    android.widget.Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "Continue with Google",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}