package com.mahdi.moneymanagemant.feature_management.presentation.splash_screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.mahdi.moneymanagemant.R
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.unit.RallyScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

      val context = LocalContext.current

      val imageLoader = ImageLoader.Builder(context)
            .components {
                  if (SDK_INT > 28){
                        add(ImageDecoderDecoder.Factory())
                  }else{
                        add(GifDecoder.Factory())
                  }
            }.build()

      // Animation
      LaunchedEffect(key1 = true) {

            // Customize the delay time
            delay(2800L)
            navController.navigate(RallyScreen.Overview.name){
                  navController.popBackStack()
            }
      }

      // Image
      Column(modifier = Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                  .fillMaxSize()
                  .weight(1f) , contentAlignment = Alignment.Center) {
                  Column {
                        Text(text = "MoneyManagement" , style = MaterialTheme.typography.h6 , color = Color.White.copy(0.85f))
                        Divider(modifier = Modifier.fillMaxWidth(0.5f) , color = Color.White.copy(0.3f))
                  }


            }
            Box(modifier = Modifier
                  .fillMaxSize()
                  .weight(2.5f) , contentAlignment = Alignment.TopCenter) {
                  Image(painter = rememberImagePainter(data = R.raw.money_gif , imageLoader = imageLoader) ,
                        contentDescription = "Logo" ,
                        modifier = Modifier.fillMaxSize(0.7f))
            }
            // Change the logo

      }
}