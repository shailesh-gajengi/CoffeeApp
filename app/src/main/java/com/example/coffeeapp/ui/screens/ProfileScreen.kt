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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.components.MyNavBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

val LightBrown = Color(0xFFC67C4E)

@Composable
fun ProfileScreen(navController: NavController) {

    val currentUser = FirebaseAuth.getInstance().currentUser

    var showEditDialog by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf(currentUser?.displayName ?: "")
    }

    var displayName by remember {
        mutableStateOf(currentUser?.displayName ?: "Coffee User")
    }

    var nameError by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            MyNavBar(navController, "Profile")
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {

            // -------------------------
            // PROFILE HEADER
            // -------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            color = LightBrown,
                            shape = RoundedCornerShape(
                                bottomStart = 40.dp,
                                bottomEnd = 40.dp
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(
                            id = R.drawable.outline_account_circle_24
                        ),
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

                    // Dynamic Firebase name
                    Text(
                        text = displayName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    // Dynamic Firebase email
                    Text(
                        text = currentUser?.email ?: "No email",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // -------------------------
            // MENU
            // -------------------------

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {

                // EDIT PROFILE
                ProfileMenuItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profile"
                ) {

                    name = displayName
                    nameError = false
                    showEditDialog = true
                }


                // MY ORDERS
                ProfileMenuItem(
                    icon = Icons.Default.ShoppingCart,
                    title = "My Orders"
                ) {
                    navController.navigate(Routes.OrdersScreen)
                }


                // WISHLIST
                ProfileMenuItem(
                    icon = Icons.Default.Favorite,
                    title = "Wishlist"
                ) {
                    navController.navigate(Routes.FavouriteScreen)
                }


                // DELIVERY ADDRESS
                ProfileMenuItem(
                    icon = Icons.Default.LocationOn,
                    title = "Delivery Address"
                ) {
                    // Add address screen later
                }


                // SETTINGS
                ProfileMenuItem(
                    icon = Icons.Default.Settings,
                    title = "Settings"
                ) {
                    // Add settings later
                }


                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(10.dp))


                // LOGOUT
                ProfileMenuItem(
                    icon = Icons.Default.Logout,
                    title = "Log Out",
                    textColor = Color.Red,
                    iconColor = Color.Red
                ) {

                    FirebaseAuth.getInstance().signOut()

                    navController.navigate(Routes.WelcomeScreen) {

                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }


    // -------------------------
    // EDIT PROFILE DIALOG
    // -------------------------

    if (showEditDialog) {

        AlertDialog(

            onDismissRequest = {
                showEditDialog = false
            },

            title = {
                Text(
                    text = "Edit Profile"
                )
            },

            text = {

                Column {

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = false
                        },
                        label = {
                            Text("Name")
                        },
                        singleLine = true,
                        isError = nameError,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (nameError) {

                        Text(
                            text = "Name cannot be empty",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        val newName = name.trim()

                        if (newName.isEmpty()) {

                            nameError = true

                        } else {

                            val profileUpdates =
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(newName)
                                    .build()

                            currentUser
                                ?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { task ->

                                    if (task.isSuccessful) {

                                        // Update Compose UI immediately
                                        displayName = newName

                                        showEditDialog = false
                                    }
                                }
                        }
                    }
                ) {

                    Text("Save")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showEditDialog = false
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}


// --------------------------------------------------
// PROFILE MENU ITEM
// --------------------------------------------------

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
            .clickable {
                onClick()
            }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    iconColor.copy(alpha = 0.1f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = iconColor
            )
        }

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color =
                if (textColor == Color.Black)
                    MaterialTheme.colorScheme.onBackground
                else
                    textColor,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}