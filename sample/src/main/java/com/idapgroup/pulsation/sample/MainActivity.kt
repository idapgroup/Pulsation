package com.idapgroup.pulsation.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.idapgroup.pulsation.ContentType
import com.idapgroup.pulsation.Pulsation
import com.idapgroup.pulsation.PulsationType
import com.idapgroup.pulsation.sample.ui.theme.PulsationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PulsationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Pulsation(
            enabled = true,
            type = PulsationType.Races(
                duration = 2500,
                contentType = ContentType.Colored(Color.Green, CircleShape)
            )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Yellow, shape = CircleShape)
                    .size(124.dp)
            )
        }
        Pulsation(
            enabled = true,
            type = PulsationType.Linear(duration = 2000, delayBetweenRepeats = 1000)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Blue, shape = CircleShape)
                    .size(124.dp)
            )
        }

        Pulsation(
            enabled = true,
            type = PulsationType.Races(
                duration = 2500,
                contentType = ContentType.Colored(Color.Green, CircleShape)
            )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Yellow, shape = CircleShape)
                    .size(124.dp)
            )
        }
        Pulsation(
            enabled = true,
            type = PulsationType.Linear(
                duration = 2000,
                delayBetweenRepeats = 1000,
                contentType = ContentType.Colored(
                    Color.DarkGray,
                    CircleShape
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Red, shape = CircleShape)
                    .size(124.dp)
            )
        }
        Pulsation(enabled = true, type = PulsationType.Iterative()) {
            Box(
                modifier = Modifier
                    .background(Color.Green, shape = CircleShape)
                    .size(124.dp)
            )
        }
        Pulsation(
            enabled = true,
            type = PulsationType.Iterative(
                iterations = 5,
                iterationDelay = 0,
                iterationDuration = 1000,
                delayBetweenRepeats = 2000
            )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black, shape = CircleShape)
                    .size(124.dp)
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PulsationTheme {
        Greeting()
    }
}

