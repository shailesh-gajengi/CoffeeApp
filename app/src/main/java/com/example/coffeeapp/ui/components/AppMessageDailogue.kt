package com.example.coffeeapp.ui.components

import android.os.Message
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
 fun AppMessageDailogue(
     show : Boolean,
     title: String,
     message: String,
     onDismiss: () -> Unit,
 ) {

     if(show){
         AlertDialog(
             onDismissRequest =  onDismiss,
             title = { Text(text = title) },
             text = { Text(text = message) },
             confirmButton = {
                 TextButton(
                     onClick = onDismiss
                 ) {
                     Text(text = "OK")
                 }
             }
         )
     }

}