package com.example.gamelist.feature.game.presentation.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.AsyncImagePainter
import com.example.gamelist.feature.game.domain.model.Game
import timber.log.Timber

@Composable
fun GameItem(
    game: Game,
    navController: NavController,
    context: Context,
    onDetailClick: (Game) -> Unit
) {

    LaunchedEffect(Unit) {
        Timber.d("Data game masuk ke list: ${game.name}")
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDE7F6)
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 7.dp)
            .fillMaxWidth()
    ) {

        Row(modifier = Modifier.padding(20.dp)) {

            SubcomposeAsyncImage(
                model = game.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
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
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6200EE)
                        )
                    }
                } else {
                    SubcomposeAsyncImageContent()
                }
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = game.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = game.genre,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF6200EE)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "•",
                            fontSize = 12.sp,
                            color = Color(0xFF888888)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = game.year,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF666666)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    val cleanDesc = formatDescription(game.desc)
                    Text(
                        text = cleanDesc,
                        fontSize = 11.sp,
                        color = Color(0xFF555555),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 15.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {

                            Timber.d(
                                "Tombol Official ditekan: ${game.name}"
                            )

                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(game.url)
                            )

                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {

                        Text("Official", fontSize = 13.sp)
                    }

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Button(
                        onClick = {

                            Timber.d(
                                "Tombol Detail ditekan: ${game.name}"
                            )

                            onDetailClick(game)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {

                        Text("Detail", fontSize = 13.sp)
                    }
                }
            }
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