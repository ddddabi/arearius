package com.example.arearius.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class AnalysisData(
    val data: UrlData
)

data class UrlData(
    val attributes: UrlAttributes,
    val type: String,
    val id: String,
    val links: UrlLinks
)

data class UrlAttributes(
    @SerializedName("last_http_response_content_sha256")
    val lastHttpResponseContentSha256: String,

    @SerializedName("html_meta")
    val htmlMeta: HtmlMeta,

    @SerializedName("last_http_response_code")
    val lastHttpResponseCode: Int,

    @SerializedName("last_analysis_results")
    val lastAnalysisResults: Map<String, AnalysisResult>,

    @SerializedName("last_final_url")
    val lastFinalUrl: String,

    @SerializedName("last_http_response_content_length")
    val lastHttpResponseContentLength: Int,

    val url: String,

    @SerializedName("last_analysis_date")
    val lastAnalysisDate: Long,

    val tags: List<String>,

    @SerializedName("last_analysis_stats")
    val lastAnalysisStats: AnalysisStats,

    @SerializedName("last_submission_date")
    val lastSubmissionDate: Long,

    @SerializedName("threat_names")
    val threatNames: List<String>,

    @SerializedName("last_http_response_headers")
    val lastHttpResponseHeaders: Map<String, String>,

    val reputation: Int,

    val categories: Map<String, String>,

    val tld: String,

    @SerializedName("last_modification_date")
    val lastModificationDate: Long,

    val title: String,

    @SerializedName("times_submitted")
    val timesSubmitted: Int,

    @SerializedName("first_submission_date")
    val firstSubmissionDate: Long,

    @SerializedName("total_votes")
    val totalVotes: Map<String, Int>
)

data class HtmlMeta(
    @SerializedName("og:url")
    val ogUrl: List<String>,

    @SerializedName("og:image")
    val ogImage: List<String>,

    val description: List<String>,

    @SerializedName("twitter:card")
    val twitterCard: List<String>,

    val Referrer: List<String>,

    @SerializedName("twitter:description")
    val twitterDescription: List<String>,

    @SerializedName("apple-mobile-web-app-title")
    val appleMobileWebAppTitle: List<String>,

    @SerializedName("og:description")
    val ogDescription: List<String>,

    val robots: List<String>,

    @SerializedName("og:title")
    val ogTitle: List<String>,

    @SerializedName("twitter:image")
    val twitterImage: List<String>,

    @SerializedName("twitter:url")
    val twitterUrl: List<String>,

    val viewport: List<String>
)

data class AnalysisResult(
    val category: String,
    val result: String,
    val method: String,

    @SerializedName("engine_name")
    val engineName: String
)

data class AnalysisStats(
    val harmless: Int,
    val malicious: Int,
    val suspicious: Int,
    val undetected: Int,
    val timeout: Int
)

data class UrlLinks(
    val self: String
)