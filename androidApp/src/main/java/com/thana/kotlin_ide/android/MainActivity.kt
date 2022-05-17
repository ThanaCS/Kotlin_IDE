package com.thana.kotlin_ide.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.toArgb
import com.thana.kotlin_ide.android.theme.IDETheme
import com.thana.kotlin_ide.android.theme.PrimaryColor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IDETheme {
                Surface(color = MaterialTheme.colors.background) {
                    window?.statusBarColor = PrimaryColor.toArgb()
                    IdeScreen()
                }
            }
        }
    }
}
