/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.s_ball.lettersword.R

@Composable
fun InfoDialog(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = { },
) {
    AlertDialog(
        onDismissRequest = { onClose() },
        confirmButton = {
            Button(onClick = { onClose() }) {
                Text(stringResource(R.string.ok))
            }
        },
        modifier = modifier,
        title = {
            val manager = LocalContext.current.packageManager
            val pName = LocalContext.current.packageName
            val info = manager.getPackageInfo(pName, PackageManager.GET_ACTIVITIES)
            val version = info?.versionName ?: stringResource(R.string.version_not_found)
            val name = stringResource(R.string.app_name)
            Text(stringResource(R.string.name_and_version).format(name, version))
        },
        text = {
            Column (
                modifier = Modifier.verticalScroll(rememberScrollState())
            ){
                Text("© s-ball - 2025-current - MIT License\n")
                Text(stringResource(R.string.display_a_list))
                Text(stringResource(R.string.only_non_accented))
                Text(stringResource(R.string.if_a_letter))
                Text(stringResource(R.string.the_pattern_contains))
                Text(stringResource(R.string.for_example))
            }
        }
    )
}

@Preview
@Composable
fun DlgPreview() {
    InfoDialog {  }
}