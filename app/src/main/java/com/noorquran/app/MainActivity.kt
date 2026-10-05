package com.noorquran.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFAF7F0)
                ) {
                    AlFatihahScreen()
                }
            }
        }
    }
}

@Composable
fun AlFatihahScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current

    // سورۃ الفاتحہ لوڈ کریں
    val ayahs = remember {
        QuranRepository(context).loadAlFatihah()
    }

    Scaffold(
        topBar = {
            Surface(color = Color(0xFF1F4A3A)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "سورۃ الفاتحہ",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { padding ->
        if (ayahs.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("متن نہیں ملا", color = Color.Red)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ayahs) { ayah ->
                    AyahCard(ayah)
                }
            }
        }
    }
}

@Composable
fun AyahCard(ayah: Ayah) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // آیت نمبر
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFD9B36C).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "${ayah.surah}:${ayah.ayah}",
                    fontSize = 12.sp,
                    color = Color(0xFF1F4A3A),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // عربی متن
            Text(
                text = ayah.arabic,
                fontSize = 24.sp,
                lineHeight = 44.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF232323)
            )

            // Roman Urdu ترجمہ
            if (!ayah.romanUrdu.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFEFEAE0))
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = ayah.romanUrdu,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = Color(0xFF6B6B6B)
                )
            }
        }
    }
}
