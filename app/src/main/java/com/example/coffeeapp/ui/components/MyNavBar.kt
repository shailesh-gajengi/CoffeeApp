package com.example.coffeeapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffeeapp.R
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.theme.LightBrown

@Composable
fun MyNavBar(navController: NavController, CurrentScreen: String) {

    val NavItems = listOf(
        NavItem("Home", R.drawable.regular_outline_home, Routes.HomeScreen),
        NavItem("Cart", R.drawable.regular_outline_bag, Routes.CartScreen),
        NavItem("Favorite", R.drawable.regular_outline_heart,Routes.FavouriteScreen),
        NavItem("Profile", R.drawable.outline_account_circle_24,Routes.ProfileScreen)
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier  = Modifier.height(100.dp)
    ) {

        NavItems.forEachIndexed { index,item ->
            NavigationBarItem(

                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title

                    )
                },
                label = {Text(text = item.title)},
                modifier = Modifier.size(30.dp),
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                selected = item.title==CurrentScreen,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = LightBrown,
                    selectedIconColor = LightBrown,
                    indicatorColor = LightBrown.copy(0.1f),
                    unselectedIconColor = LightBrown,
                    unselectedTextColor = Color.LightGray
                )
            )
        }
    }


}
data class NavItem(
    val title: String,
    val icon: Int,
    val route: Routes
)