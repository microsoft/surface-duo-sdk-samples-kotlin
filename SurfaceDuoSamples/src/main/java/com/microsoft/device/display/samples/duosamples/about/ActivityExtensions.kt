package com.microsoft.device.display.samples.duosamples.about

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.microsoft.device.display.samples.duosamples.R

fun Activity.onOssLicensesClicked() {
    OssLicensesMenuActivity.setActivityTitle(getString(R.string.about_licenses_terms_title))
    startActivity(Intent(this, OssLicensesMenuActivity::class.java))
}

fun Activity.openUrl(url: String) {
    startActivity(
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}