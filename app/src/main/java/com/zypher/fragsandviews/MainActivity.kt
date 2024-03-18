package com.zypher.fragsandviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import com.zypher.fragsandviews.ui.theme.FragsandviewsTheme

class MainActivity : ComponentActivity(), InputFragment.InputListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FragsandviewsTheme {
                AppContent()
            }
        }

        // Korrekt initialisera InputFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                // Ändra 'newInstance' till direkt instansiering om din Fragment inte använder 'newInstance'-metoden
                replace(R.id.fragment_container, InputFragment.newInstance())
                commit()
            }
        }
    }

    @Composable
    fun AppContent() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FragmentContainer()
        }
    }

    @Composable
    fun FragmentContainer() {
        val context = LocalContext.current
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                // Skapa och returnera din FragmentContainerView här
                FragmentContainerView(ctx).apply {
                    id = R.id.fragment_container // Se till att detta ID stämmer överens med det i din XML-layout
                }
            }
        )
    }

    override fun onInputSent(input: String) {
        // Hantera input mottaget från InputFragment och starta DrawingFragment
        val drawingFragment = DrawingFragment().apply {
            arguments = Bundle().apply {
                putString("input_key", input)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, drawingFragment)
            addToBackStack(null) // Ger användaren möjlighet att navigera tillbaka
            commit()
        }
    }
}
