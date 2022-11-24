/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.duosamples.about.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.microsoft.device.display.samples.duosamples.R
import com.microsoft.device.display.samples.duosamples.about.ItemClickListener
import com.microsoft.device.display.samples.duosamples.about.LayoutInfoViewModel
import com.microsoft.device.display.samples.duosamples.about.LicensesConfig
import com.microsoft.device.display.samples.duosamples.about.addClickableLink
import com.microsoft.device.display.samples.duosamples.databinding.FragmentAboutTeamsAndLicensesBinding

class AboutTeamsAndLicensesFragment : Fragment() {
    private var binding: FragmentAboutTeamsAndLicensesBinding? = null

    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    private val itemClickListener = object : ItemClickListener<String> {
        override fun onClick(model: String?) {
            onLinkClicked(model)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutTeamsAndLicensesBinding.inflate(inflater, container, false)
        binding?.isDualMode = layoutInfoViewModel.isDualMode.value
        binding?.linksItems?.itemClickListener = itemClickListener
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        layoutInfoViewModel.isDualMode.observe(viewLifecycleOwner) { binding?.isDualMode = it }
    }

    private fun setupListeners() {
        binding?.licensePrivacyTitle?.setOnClickListener {
            onLinkClicked(LicensesConfig.PRIVACY_URL)
        }
        binding?.licenseTermsTitle?.setOnClickListener {
            onOssLicensesClicked()
        }
        setupDescriptionText()
    }

    private fun onOssLicensesClicked() {
        activity?.let {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.about_licenses_terms_title))
            startActivity(Intent(it, OssLicensesMenuActivity::class.java))
        }
    }

    private fun onLinkClicked(linkUrl: String?) {
        linkUrl?.takeIf { it.isNotBlank() }?.let { url -> openUrl(url) }
    }

    private fun setupDescriptionText() {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                openUrl(getString(R.string.github_issues_url))
            }
        }

        binding?.feedbackSingleScreenDescription?.addClickableLink(
            getString(R.string.about_feedback_description_clickable),
            clickableSpan
        )
    }

    private fun openUrl(url: String) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
