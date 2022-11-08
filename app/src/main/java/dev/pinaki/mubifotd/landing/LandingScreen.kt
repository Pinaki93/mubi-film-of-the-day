package dev.pinaki.mubifotd.landing

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.pinaki.mubifotd.R
import dev.pinaki.mubifotd.ui.theme.Typography

@Composable
fun LandingScreen(viewModel: LandingScreenViewModel, viewController: LandingScreenViewController) {
    when (val state = viewModel.state) {
        FilmOfTheDayState.FatalError -> FatalErrorView()
        FilmOfTheDayState.Loading -> LoadingView()
        FilmOfTheDayState.Offline -> OfflineView(onRetryClick = { viewModel.retry() })
        is FilmOfTheDayState.ServerError -> ServerErrorView(
            state = state,
            onRetryClick = { viewModel.retry() }
        )
        is FilmOfTheDayState.Success -> SuccessView(
            state = state,
            onShareClick = {
                viewController.share(state.shareText)
            },
            onWatchClick = {
                viewController.openMubi(state.url)
            }
        )
    }
}

@Composable
private fun FatalErrorView() {
    SimpleContentView(
        message = stringResource(R.string.fatal_error_description),
    )
}

@Composable
private fun LoadingView() {
    SimpleContentView(
        message = stringResource(R.string.loading),
    )
}

@Composable
private fun OfflineView(onRetryClick: () -> Unit) {
    SimpleContentView(
        message = stringResource(R.string.offline_message),
        button = {
            Button(onClick = onRetryClick) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    )
}

@Composable
private fun ServerErrorView(state: FilmOfTheDayState.ServerError, onRetryClick: () -> Unit) {
    SimpleContentView(
        message = stringResource(
            R.string.server_error_message,
            state.errorCode,
        ),
        button = {
            Button(onClick = onRetryClick) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    )
}

@Composable
private fun SimpleContentView(
    message: String,
    button: @Composable () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = message, style = Typography.subtitle2, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(8.dp))

            button()
        }
    }
}

@Composable
private fun SuccessView(
    state: FilmOfTheDayState.Success,
    onShareClick: () -> Unit,
    onWatchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.title, style = Typography.h6)

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = state.directors, style = Typography.subtitle2)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = state.synopsis, style = Typography.body2, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onShareClick, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.share))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onWatchClick, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.watch_on_mubi))
            }
        }
    }
}