package com.example.nearbites.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nearbites.AuthenticationViewModel
import com.example.nearbites.R
import com.example.nearbites.api.AuthenticationViewModelFactory
import com.example.nearbites.api.UserRepository
import com.example.nearbites.database.AppDataBase
import com.example.nearbites.database.UserEntity
import com.example.nearbites.navigation.ScreenName

val gradientStart = Color(0xFFFF1744)  // red accent
val gradientEnd = Color(0xFFFF8A65)    // warm orange
val buttonRed = Color(0xFFE53935)      // red button
val lightGray = Color(0xFFEEEEEE)

@Composable
fun AuthenticationScreen(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current

    val viewModelFactory = remember {
        AuthenticationViewModelFactory(
            UserRepository(AppDataBase.getInstance(context).userDao())
        )
    }

   val viewModel: AuthenticationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
       factory = viewModelFactory
   )

    Box(modifier = modifier.background(color = Color.White), contentAlignment = Alignment.Center){
        MainView(Modifier.padding(horizontal = 20.dp),viewModel,navController)
    }
}

@Composable
private fun MainView(
    modifier: Modifier,
    viewModel: AuthenticationViewModel,
    navController: NavHostController
) {
    var isSignIn by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.Top) {
        Image(
            painter = painterResource(id = R.drawable.app_image), // replace with your logo
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.FillBounds,
        )
        Card(
            modifier,
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(Modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 16.dp, start = 30.dp, end = 30.dp)
                        .border(1.dp, color = lightGray, shape = RoundedCornerShape(20.dp)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TabButton(
                        text = "Sign In",
                        isSelected = isSignIn,
                        onClick = { isSignIn = true },
                        activeColor = buttonRed
                    )
                    TabButton(
                        text = "Sign Up",
                        isSelected = !isSignIn,
                        onClick = { isSignIn = false },
                        activeColor = buttonRed
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter email or username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    singleLine = true
                )

                if(isSignIn)
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween
                        , verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = {},
                                colors = CheckboxDefaults.colors()
                            )
                            Text(
                                text ="Remember me",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            text ="Forgot Password?",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                if (!isSignIn)
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        singleLine = true
                    )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if(isSignIn){
                            if (email.isNotBlank() && password.isNotBlank()) {
                                viewModel.checkUserPassword(
                                    username = email,
                                    enteredPassword = password
                                ) { it ->
                                    when (it) {
                                        1 -> {
                                            Toast.makeText(
                                                context,
                                                "Signed in successfully!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navController.navigate(ScreenName.MainScreen.name)
                                        }

                                        0 -> Toast.makeText(
                                            context,
                                            "You'r not Registered User",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        2 -> Toast.makeText(
                                            context,
                                            "Incorrect Password",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Enter valid email and password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        else{
                            if(viewModel.checkPasswordAndConformPasswordMatch(password,confirmPassword)){
                                viewModel.insertUser(UserEntity(
                                    userName = email,
                                    password = password
                                ))
                                Toast.makeText(context, "Sign Up Successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate(route = ScreenName.MainScreen.name)
                            }
                            else{
                                Toast.makeText(context, "Password and Conform not matching", Toast.LENGTH_SHORT).show()
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonRed)
                ) {
                    Text(
                        text = if (isSignIn) "Sign In" else "Sign Up",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(Modifier.height(80.dp))
        Text(
            text ="Made In India",
            color = Color.Gray,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.fillMaxHeight())
    }

}

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit, activeColor: Color) {
    val backgroundColor = if (isSelected) activeColor else Color.Transparent
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
            .clickable(interactionSource = remember { MutableInteractionSource() }) { onClick() },

    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp),
            text = text,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            maxLines = 1
        )
    }
}


@Preview
@Composable
fun Preview(){
//    AuthenticationScreen(Modifier.fillMaxSize(), navController)
}