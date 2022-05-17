package com.thana.kotlin_ide.android

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thana.kotlin_ide.android.theme.*

@Composable
fun Toolbar(
    viewModel: IdeViewModel = viewModel(),
    script: String,
    onClear: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = PrimaryColor
                )
            ),
        horizontalArrangement = Arrangement.Center

    ) {

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(14.dp)
                .clip(CircleShape)
                .background(LightRed)
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp)
                .size(14.dp)
                .clip(CircleShape)
                .background(LightYellow)

        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp)
                .size(14.dp)
                .clip(CircleShape)
                .background(LightGreen),

            )
        IconButton(onClick = {
            viewModel.getOutput(script)
            focusManager.clearFocus()
        }) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Run",
                tint = PrimaryColor,
            )
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(30.dp)
                .width(80.dp)
                .clip(CircleShape)
                .background(PrimaryVariant),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = "KOTLIN",
                color = SecondaryColor,
                fontWeight = FontWeight.Bold
            )
        }

        IconButton(onClick = { onClear() }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Clear",
                tint = PrimaryColor,
            )
        }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, script)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current

        IconButton(onClick = { context.startActivity(shareIntent) }) {
            Icon(
                imageVector = Icons.Rounded.Share,
                contentDescription = "Share",
                tint = PrimaryColor,
            )
        }
    }
}
