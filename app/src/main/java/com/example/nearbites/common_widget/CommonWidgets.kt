package com.example.nearbites.common_widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderView() {
    Row(
        Modifier.fillMaxWidth().height(70.dp).background(color = Color(0xFFFF1744)).padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically){
        Text(
            text = "NearBites",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
data class Review(val userName: String, val reviewText: String)

@Composable
fun ReviewsDialog(
    reviews: List<Review>,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var newReview by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Reviews") },
        text = {
            Column {
                // List of previous reviews
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(reviews.size) { index ->
                        Text(text = "${reviews[index].userName}: ${reviews[index].reviewText}")
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Input for new review
                OutlinedTextField(
                    value = newReview,
                    onValueChange = { newReview = it },
                    label = { Text("Write a review") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (newReview.isNotBlank()) {
                        onSubmit(newReview)
                        newReview = ""
                    }
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun ReviewExampleScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var reviews by remember { mutableStateOf(
        listOf(
            Review("Alice", "Great food!"),
            Review("Bob", "Nice ambiance."),
        )
    ) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { showDialog = true }) {
            Text("Show Reviews")
        }

        if (showDialog) {
            ReviewsDialog(
                reviews = reviews,
                onDismiss = { showDialog = false },
                onSubmit = { newReview ->
                    reviews = reviews + Review("You", newReview)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReviewScreen() {
    ReviewExampleScreen()
}
