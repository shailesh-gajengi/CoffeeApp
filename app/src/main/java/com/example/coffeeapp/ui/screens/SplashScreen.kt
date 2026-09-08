package com.example.coffeeapp.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavController
import com.example.coffeeapp.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

private val CoffeeBrown = Color(0xFF6F4E37)
private val CoffeeDark = Color(0xFF4B2E1F)
private val CoffeeAccent = Color(0xFFD7B899)
private val CoffeeCream = Color(0xFFFFF9F3)
private val CoffeeLight = Color(0xFFF5EDE5)

@Composable
fun SplashScreen(
    navController: NavController
) {

    var progress by remember {
        mutableFloatStateOf(0f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 2200,
            easing = LinearEasing
        ),
        label = "splash_progress"
    )

    LaunchedEffect(Unit) {

        progress = 1f

        delay(2500)

        val destination =
            if (FirebaseAuth.getInstance().currentUser != null) {
                Routes.HomeScreen
            } else {
                Routes.WelcomeScreen
            }

        navController.navigate(destination) {
            popUpTo(Routes.SplashScreen) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeCream)
    ) {

        // ---------------------------------------------------------
        // Decorative circles
        // ---------------------------------------------------------

        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(
                    x = (-70).dp,
                    y = (-50).dp
                )
                .background(
                    CoffeeLight,
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(140.dp)
                .offset(
                    x = 290.dp,
                    y = 40.dp
                )
                .background(
                    CoffeeLight,
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(
                    x = (-70).dp,
                    y = 650.dp
                )
                .background(
                    CoffeeLight,
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(150.dp)
            )

            // -----------------------------------------------------
            // COFFEE LOGO
            // -----------------------------------------------------

            Box(
                modifier = Modifier
                    .size(150.dp),
                contentAlignment = Alignment.Center
            ) {

                // Steam
                Text(
                    text = "♨",
                    color = CoffeeBrown,
                    fontSize = 58.sp,
                    modifier = Modifier
                        .offset(y = (-35).dp)
                )

                // Cup
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(65.dp)
                        .background(
                            CoffeeBrown,
                            RoundedCornerShape(
                                topStart = 45.dp,
                                topEnd = 45.dp,
                                bottomStart = 25.dp,
                                bottomEnd = 25.dp
                            )
                        )
                )

                // Coffee
                Box(
                    modifier = Modifier
                        .width(72.dp)
                        .height(17.dp)
                        .offset(y = (-20).dp)
                        .background(
                            CoffeeDark,
                            RoundedCornerShape(50)
                        )
                )

                // Handle
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .offset(x = 50.dp)
                        .background(
                            CoffeeBrown,
                            CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(21.dp)
                        .offset(x = 50.dp)
                        .background(
                            CoffeeCream,
                            CircleShape
                        )
                )

                // Coffee bean on cup
                Box(
                    modifier = Modifier
                        .width(22.dp)
                        .height(35.dp)
                        .offset(y = 7.dp)
                        .rotate(-25f)
                        .background(
                            CoffeeDark,
                            RoundedCornerShape(50)
                        )
                )

                // Saucer
                Box(
                    modifier = Modifier
                        .width(125.dp)
                        .height(14.dp)
                        .offset(y = 40.dp)
                        .background(
                            CoffeeBrown,
                            RoundedCornerShape(50)
                        )
                )
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // -----------------------------------------------------
            // APP NAME
            // -----------------------------------------------------

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Coffee",
                    color = CoffeeDark,
                    fontSize = 42.sp,
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "App",
                    color = CoffeeAccent,
                    fontSize = 42.sp,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -----------------------------------------------------
            // TAGLINE
            // -----------------------------------------------------

            Text(
                text = "GOOD COFFEE",
                color = CoffeeDark,
                fontSize = 13.sp,
                letterSpacing = 6.sp
            )

            Text(
                text = "BRIGHTER DAYS",
                color = CoffeeDark,
                fontSize = 13.sp,
                letterSpacing = 5.sp
            )

            Spacer(
                modifier = Modifier.height(55.dp)
            )

            // -----------------------------------------------------
            // PROGRESS BAR
            // -----------------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(8.dp)
                    .background(
                        CoffeeAccent.copy(alpha = 0.45f),
                        RoundedCornerShape(50)
                    )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .background(
                            CoffeeBrown,
                            RoundedCornerShape(50)
                        )
                )
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Brewing something amazing...",
                color = CoffeeDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            // -----------------------------------------------------
            // BOTTOM TEXT
            // -----------------------------------------------------

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "—",
                    color = CoffeeAccent,
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Enjoy Every Sip",
                    color = CoffeeDark.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    letterSpacing = 2.sp
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "—",
                    color = CoffeeAccent,
                    fontSize = 18.sp
                )
            }

            Spacer(
                modifier = Modifier.height(45.dp)
            )
        }

        // ---------------------------------------------------------
        // Decorative coffee beans
        // ---------------------------------------------------------

        CoffeeBean(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = 25.dp,
                    y = (-20).dp
                )
                .rotate(-25f)
        )

        CoffeeBean(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(
                    x = (-30).dp,
                    y = (-12).dp
                )
                .rotate(20f)
        )

        CoffeeBean(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(
                    x = (-25).dp,
                    y = (-30).dp
                )
                .rotate(-15f)
        )
    }
}

@Composable
private fun CoffeeBean(
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier.size(42.dp)
    ) {

        rotate(25f) {

            drawOval(
                color = CoffeeDark,
                size = size
            )

            drawOval(
                color = CoffeeAccent,
                topLeft = androidx.compose.ui.geometry.Offset(
                    x = size.width * 0.45f,
                    y = size.height * 0.10f
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width * 0.12f,
                    height = size.height * 0.8f
                )
            )
        }
    }
}