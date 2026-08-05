package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeeapp.R
import com.example.coffeeapp.ui.components.MyNavBar

// Using the common brown color from your app
val LightBrown = Color(0xFFC67C4E)

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        bottomBar = { MyNavBar(navController,"Profile") }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize().padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // --- Header with hardcoded info ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                // Brown Background Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            color = LightBrown,
                            shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                        )
                )

                // Profile Card
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Image (using your account icon)
                    Image(
                        painter = painterResource(id = R.drawable.outline_account_circle_24),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(4.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Hardcoded Name
                    Text(
                        text = "Shailesh Gajengi",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    // Hardcoded Email
                    Text(
                        text = "shaileshgajengi@gmail.com",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // --- Settings Menu ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                ProfileMenuItem(icon = Icons.Default.Person, title = "Edit Profile") { }
                ProfileMenuItem(icon = Icons.Default.ShoppingCart, title = "My Orders") { }
                ProfileMenuItem(icon = Icons.Default.Favorite, title = "Wishlist") { }
                ProfileMenuItem(icon = Icons.Default.LocationOn, title = "Delivery Address") { }
                ProfileMenuItem(icon = Icons.Default.Settings, title = "Settings") { }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(10.dp))

                // Logout
                ProfileMenuItem(
                    icon = Icons.Default.Logout,
                    title = "Log Out",
                    textColor = Color.Red,
                    iconColor = Color.Red
                ) {
                    // Logic here
                }
            }
        }
    }

}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    iconColor: Color = LightBrown,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Note the parenthesis!
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconColor.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = iconColor
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (textColor == Color.Black) MaterialTheme.colorScheme.onBackground else textColor,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}
