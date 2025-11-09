package com.example.nearbites.common_widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nearbites.database.RestaurantEntity

@Composable
fun RestaurantsItemView(restaurant: RestaurantEntity, index: Int,onReviewClick:()->Unit) {
    var isLikeClick = remember { mutableStateOf(false) }
    var isDisLikeClick = remember { mutableStateOf(false) }
    val images = listOf(
        com.example.nearbites.R.drawable.chanwalrus,
        com.example.nearbites.R.drawable.chevanon,
        com.example.nearbites.R.drawable.daniela,
        com.example.nearbites.R.drawable.fotios_photos,
        com.example.nearbites.R.drawable.janetrangdoan,
        com.example.nearbites.R.drawable.nastyasensei,
        com.example.nearbites.R.drawable.olsson,
        com.example.nearbites.R.drawable.owpictures,
        com.example.nearbites.R.drawable.pixabay,
        com.example.nearbites.R.drawable.sheenawood,
        com.example.nearbites.R.drawable.valeriya,
        com.example.nearbites.R.drawable.vince)
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = images[index%12]),
                contentDescription = "food image",
                modifier = Modifier
                    .width(120.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = restaurant.name,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = com.example.nearbites.R.drawable.baseline_stars_24),
                        contentDescription = "reting",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        tint = Color(0xFF0F4D0F)
                    )
                    Text(
                        text = restaurant.rating,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = restaurant.dishes,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = restaurant.locality,
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .height(2.dp)
                        .background(Color.Gray)
                )

                Text(
                    text = "Timing: "+restaurant.timing,
                    color = Color(0xFF006400),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Icon(
                        painter = painterResource(id = com.example.nearbites.R.drawable.outline_rate_review_24),
                        contentDescription = "rate",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                onReviewClick()
                            }
                    )
                    Icon(
                        painter = painterResource(id = com.example.nearbites.R.drawable.baseline_thumb_up_24),
                        contentDescription = "like",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                isLikeClick.value = true
                                isDisLikeClick.value = false
                            },
                        tint = if(isLikeClick.value) Color(0xFF0F4D0F) else Color.DarkGray
                    )
                    Icon(
                        painter = painterResource(id = com.example.nearbites.R.drawable.baseline_thumb_down_24),
                        contentDescription = "dislike",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                isDisLikeClick.value = true
                                isLikeClick.value = false
                            },
                        tint = if(isDisLikeClick.value) Color(0xFFFF0038) else Color.DarkGray
                    )
                }
            }

        }
}



@Preview
@Composable
fun RestroPreview(){
    Box(Modifier.fillMaxSize()){
//        RestaurantsItemView(restaurants[index])
    }
}