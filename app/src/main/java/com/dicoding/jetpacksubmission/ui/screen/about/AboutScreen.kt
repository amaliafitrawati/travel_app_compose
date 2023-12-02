package com.dicoding.jetpacksubmission.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme

@Composable
fun AboutScreen(modifier : Modifier = Modifier){
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center,
       modifier = Modifier
           .fillMaxSize().
           padding(16.dp)
   ){
       Image(
           painter = painterResource(R.drawable.profile),
           contentDescription = "profile_image",
           contentScale = ContentScale.Crop,
           modifier = modifier
               .size(100.dp)
               .clip(RoundedCornerShape(50.dp)))
       Text(
           text = "Amalia Fitrawati",
           fontWeight = FontWeight.Bold,
           modifier = modifier.padding(0.dp, 8.dp, 0.dp, 3.dp))
       Text(
           text = "amaliafitrawati13@gmail.com")
   }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    JetpackSubmissionTheme {
        AboutScreen()
    }
}