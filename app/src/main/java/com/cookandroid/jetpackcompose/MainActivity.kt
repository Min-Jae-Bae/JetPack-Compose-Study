package com.cookandroid.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cookandroid.jetpackcompose.ui.theme.JetPackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposeTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

/*Reusing Composable
* 상태 호이스팅*/
@Composable
fun MyApp(): Unit {
    var shouldShowOnboarding by remember { mutableStateOf(true)}

    if (shouldShowOnboarding) {
        OnboardingScreen()
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = listOf("World", "Compose")): Unit {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

/*새 컴포저블과 새 미리보기 추가*/
@Composable
fun OnboardingScreen(): Unit {

    /* = 기호는 by 키워드를 대신 사용하여 .value를 입력할 필요가 없다.*/
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    androidx.compose.material.Surface {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Hello")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = { shouldShowOnboarding = false }
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(): Unit {
    JetPackComposeTheme {
        MyApp()
    }
}

@Composable
fun Greeting(name: String) {


    /*remember를 사용하여 변경 가능한 상태를 기억
    * MutableState는 어떤 값을 보유하고 그 값이 변경될 때마다 UI 업데이트(리컴포지션)하는 인터페이스*/
    val expanded = remember { mutableStateOf(false) }

    /*항목 펼치기*/
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    /*Surface는 앱에 넣고자 하는 공통 기능을 처리*/
    androidx.compose.material.Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)

    ) {

        /*Create Row*/
        Row(modifier = Modifier.padding(24.dp)) {
            /*Create Column*/
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding) // 밑에 항목 펼치기
            ) {
                Text(text = "Hello,")
                Text(text = name)

            }
            /*Create OutlinedButton
            * 상태 변경 및 상태 변경사항에 반응*/
            OutlinedButton(onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }

        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    JetPackComposeTheme {
        MyApp()
    }
}