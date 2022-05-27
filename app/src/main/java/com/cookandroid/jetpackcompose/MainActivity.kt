package com.cookandroid.jetpackcompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
private fun MyApp(): Unit {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }


    /*이벤트를 전달
    * continue 버튼을 클릭했을 때 앱에 알림*/
    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}


/*새 컴포저블과 새 미리보기 추가*/
@Composable
private fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material.Surface {
        Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Hello")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
private fun Greetings(
    names: List<String> = listOf(
        "위고 요리스", "맷 도허티", "레길론", "로메로", "호이비에르", "산체스", "손흥민",
        "윙크스", "케인"
    ), modifier: Modifier = Modifier
) {


    // LazyColumn과 LazyRow는 뷰의 RecyclerView와 동일일
    // LazyColumn은 items 요소 제공
    LazyColumn(
        modifier
            .padding(vertical = 4.dp)
    ) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}


@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {


    /*remember를 사용하여 변경 가능한 상태를 기억
    * MutableState는 어떤 값을 보유하고 그 값이 변경될 때마다 UI 업데이트(리컴포지션)하는 인터페이스*/
    var expanded by remember { mutableStateOf(false) }

    /*항목 펼치기*/
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    /*Surface는 앱에 넣고자 하는 공통 기능을 처리*/
    androidx.compose.material.Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        shape = CircleShape
    ) {

        /*Create Row*/
        Row(
            modifier
                .padding(24.dp)
        ) {
            /*Create Column*/
            Column(
                modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp)) // 밑에 항목 펼치기
            ) {
                Text(text = "Tottenham Hotspur")
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded) {
                    Text(
                        text = ("EPL, " + "Tottenham.").repeat(4),
                    )
                }

            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    JetPackComposeTheme {
        Greetings()
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(): Unit {
    JetPackComposeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
