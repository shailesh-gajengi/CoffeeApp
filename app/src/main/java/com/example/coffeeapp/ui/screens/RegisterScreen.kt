package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.theme.LightBrown
import com.example.coffeeapp.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController
) {

    val authViewModel: AuthViewModel = viewModel()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val registerSuccess by authViewModel.registerSuccess.collectAsState()
    val error by authViewModel.error.collectAsState()

    // Navigate ONLY after Firebase registration succeeds
    LaunchedEffect(registerSuccess) {

        if (registerSuccess) {

            navController.navigate(Routes.HomeScreen) {

                popUpTo(Routes.WelcomeScreen) {
                    inclusive = true
                }

            }
        }
    }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Create Account",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(24.dp))


            // NAME
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Name")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))


            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text("Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))


            // PASSWORD
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text("Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))


            // CONFIRM PASSWORD
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = {
                    Text("Confirm Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))


            // ERROR MESSAGE
            if (error != null) {

                Text(
                    text = error ?: "",
                    color = androidx.compose.ui.graphics.Color.Red
                )

                Spacer(modifier = Modifier.height(12.dp))
            }


            // REGISTER BUTTON
            Button(
                onClick = {

                    when {

                        name.isBlank() -> {
                            // You can later make this a proper UI error
                            return@Button
                        }

                        email.isBlank() -> {
                            return@Button
                        }

                        password.isBlank() -> {
                            return@Button
                        }

                        confirmPassword.isBlank() -> {
                            return@Button
                        }

                        password != confirmPassword -> {
                            return@Button
                        }

                        else -> {

                            authViewModel.register(
                                name = name.trim(),
                                email = email.trim(),
                                password = password
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBrown
                )
            ) {

                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))


            // LOGIN
            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Text("Already have an account? Login")
            }
        }
    }
}