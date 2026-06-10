package com.example.gamelist.feature.game.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.AsyncImagePainter
import com.example.gamelist.feature.game.domain.model.Game

@Composable
fun DetailScreen(game: Game, onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFAFAFA))
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Detail Game",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        SubcomposeAsyncImage(
            model = game.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFEDE7F6), Color(0xFFD1C4E9))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = game.name.take(1).uppercase(),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE)
                    )
                }
            } else {
                SubcomposeAsyncImageContent()
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = game.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111111),
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            val rating = run {
                val regex = """rating of ([0-9.]+)/5""".toRegex()
                regex.find(game.desc)?.groupValues?.get(1) ?: "0.0"
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐ $rating / 5",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFB300)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFEDE7F6),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = game.genre,
                        color = Color(0xFF6200EE),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF5F5F5)
                ) {
                    Text(
                        text = "Tahun: ${game.year}",
                        color = Color(0xFF616161),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            val cleanDesc = formatDescription(game.desc)
            Text(
                text = cleanDesc,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = Color(0xFF424242)
            )
        }
    }
}

private fun formatDescription(desc: String): String {
    val regex = """^Explore (.+), a game of genre (.+)\. Released on (.+) with a rating of ([0-9.]+)/5\.$""".toRegex()
    val matchResult = regex.find(desc) ?: return desc
    val (name, genres, released, rating) = matchResult.destructured
    
    val formattedDate = formatDateString(released)
    
    return "$name merupakan game bergenre $genres yang dirilis pada tanggal $formattedDate. Game ini mendapatkan rating sebesar $rating/5 di RAWG."
}

private fun formatDateString(dateStr: String): String {
    if (dateStr == "unknown date" || dateStr == "N/A" || dateStr.isBlank()) {
        return "yang belum ditentukan"
    }
    val parts = dateStr.split("-")
    if (parts.size != 3) return dateStr
    val year = parts[0]
    val month = when (parts[1]) {
        "01" -> "Januari"
        "02" -> "Februari"
        "03" -> "Maret"
        "04" -> "April"
        "05" -> "Mei"
        "06" -> "Juni"
        "07" -> "Juli"
        "08" -> "Agustus"
        "09" -> "September"
        "10" -> "Oktober"
        "11" -> "November"
        "12" -> "Desember"
        else -> parts[1]
    }
    val day = parts[2].toIntOrNull()?.toString() ?: parts[2]
    return "$day $month $year"
}