package com.example.coffeeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffeeapp.R
// Make sure this matches your project's package name

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchBar() {
    var tex by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = tex,
            onValueChange = { tex = it },
            // Use weight(1f) so it doesn't push the button off screen
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = "Search Coffee",
                color = Color.Gray) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.regular_outline_search),
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF2A2A2A),
                focusedContainerColor = Color(0xFF422A24),
                cursorColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true

        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = { },
            modifier = Modifier
                .background(
                    color = Color(0xFFC47B4D),
                    shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                ).height(56.dp),
        ) {
            Icon(
                // Ensure regular_outline_filter exists in res/drawable
                painter = painterResource(id = R.drawable.regular_outline_filter),
                contentDescription = "Filter",
                tint = Color.White
            )
        }
    }
}