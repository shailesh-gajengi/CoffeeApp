package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.mapper.toProduct
import com.example.coffeeapp.ui.components.ProductCard
import com.example.coffeeapp.viewmodel.CartViewModel
import com.example.coffeeapp.viewmodel.ChatViewModel
import com.example.coffeeapp.viewmodel.FavouriteViewModel
import com.google.firebase.auth.FirebaseAuth

private val CoffeeBrown = Color(0xFF6F4E37)
private val CoffeeDark = Color(0xFF4B2E1F)
private val CoffeeLight = Color(0xFFF5EDE5)
private val CoffeeCream = Color(0xFFFFF9F3)
private val CoffeeAccent = Color(0xFFD7B899)

@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = viewModel()
) {

    val context = LocalContext.current

    val cartViewModel: CartViewModel = viewModel()
    val favouriteViewModel: FavouriteViewModel = viewModel()

    val favourites by favouriteViewModel.favourites.collectAsState()
    val messages by chatViewModel.messages.collectAsState()

    val userId = FirebaseAuth
        .getInstance()
        .currentUser
        ?.uid
        .orEmpty()

    var message by remember {
        mutableStateOf("")
    }

    var isSending by remember {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()

    // Load favourites
    LaunchedEffect(Unit) {

        if (userId.isNotBlank()) {

            favouriteViewModel.getFavourite(
                userId = userId,
                context = context
            )
        }
    }

    // Scroll to latest message
    LaunchedEffect(messages.size) {

        if (messages.isNotEmpty()) {

            listState.animateScrollToItem(
                messages.lastIndex
            )

            if (isSending) {
                isSending = false
            }
        }
    }

    Scaffold(

        containerColor = CoffeeCream,

        // ---------------------------------------------------------
        // TOP BAR
        // ---------------------------------------------------------

        topBar = {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CoffeeBrown,
                shadowElevation = 4.dp
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth().statusBarsPadding()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(CoffeeAccent),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Coffee,
                            contentDescription = "Coffee",
                            tint = CoffeeDark,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Column {

                        Text(
                            text = "AI Coffee Assistant",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Find your perfect coffee ☕",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        },

        // ---------------------------------------------------------
        // BOTTOM AREA
        // ---------------------------------------------------------

        bottomBar = {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CoffeeCream,
                shadowElevation = 8.dp
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp
                        )
                ) {

                    Text(
                        text = "How are you feeling?",
                        modifier = Modifier.padding(
                            start = 16.dp,
                            bottom = 6.dp
                        ),
                        color = CoffeeDark,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        AssistChip(
                            onClick = {
                                message = "I am feeling tired"
                            },
                            label = {
                                Text("😴 Tired")
                            }
                        )

                        AssistChip(
                            onClick = {
                                message = "I am feeling stressed"
                            },
                            label = {
                                Text("😌 Stressed")
                            }
                        )

                        AssistChip(
                            onClick = {
                                message = "I am feeling happy"
                            },
                            label = {
                                Text("😊 Happy")
                            }
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedTextField(
                            value = message,
                            onValueChange = {
                                message = it
                            },
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text(
                                    "Tell me how you're feeling..."
                                )
                            },
                            shape = RoundedCornerShape(24.dp),
                            singleLine = true
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        IconButton(
                            onClick = {

                                if (
                                    message.isNotBlank() &&
                                    userId.isNotBlank() &&
                                    !isSending
                                ) {

                                    isSending = true

                                    chatViewModel.sendMessage(
                                        userId = userId,
                                        message = message.trim()
                                    )

                                    message = ""
                                }
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    if (
                                        message.isNotBlank() &&
                                        !isSending
                                    ) {
                                        CoffeeBrown
                                    } else {
                                        CoffeeAccent
                                    }
                                )
                        ) {

                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }

    ) { paddingValues ->

        // ---------------------------------------------------------
        // CHAT CONTENT
        // ---------------------------------------------------------

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(messages) { msg ->

                ChatBubble(
                    message = msg.message,
                    isUser = msg.isUser
                )

                // Recommended coffees
                if (
                    !msg.isUser &&
                    msg.recommendedCoffees.isNotEmpty()
                ) {

                    Text(
                        text = "☕ Recommended for you",
                        color = CoffeeDark,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            bottom = 4.dp
                        )
                    )

                    msg.recommendedCoffees.forEach { coffee ->

                        ProductCard(
                            navController = navController,
                            product = coffee.toProduct(context),
                            cartViewModel = cartViewModel,
                            favouriteViewModel = favouriteViewModel,
                            favourites = favourites,
                            context = context
                        )
                    }
                }
            }

            // Thinking indicator
            if (isSending) {

                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(CoffeeAccent),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Coffee,
                                contentDescription = "AI",
                                tint = CoffeeDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Surface(
                            color = CoffeeLight,
                            shape = RoundedCornerShape(18.dp)
                        ) {

                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = CoffeeBrown,
                                    strokeWidth = 2.dp
                                )

                                Spacer(
                                    modifier = Modifier.width(8.dp)
                                )

                                Text(
                                    text = "Thinking...",
                                    color = CoffeeDark
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun ChatBubble(
    message: String,
    isUser: Boolean
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        },
        verticalAlignment = Alignment.Bottom
    ) {

        if (!isUser) {

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(CoffeeAccent),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Coffee,
                    contentDescription = "AI",
                    tint = CoffeeDark,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(0.82f),
            shape = if (isUser) {

                RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp,
                    bottomStart = 18.dp,
                    bottomEnd = 4.dp
                )

            } else {

                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 18.dp,
                    bottomStart = 18.dp,
                    bottomEnd = 18.dp
                )
            },
            color = if (isUser) {
                CoffeeBrown
            } else {
                CoffeeLight
            }
        ) {

            Text(
                text = message,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                color = if (isUser) {
                    Color.White
                } else {
                    CoffeeDark
                }
            )
        }
    }
}