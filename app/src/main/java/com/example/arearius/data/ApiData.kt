package com.example.arearius.data

import com.google.gson.annotations.SerializedName

// file
data class FileAnalysisData(
    val data: FileData
)

data class FileData(
    val attributes: FileAttributes,
    val type: String,
    val id: String,
    val links: FileLinks
)

data class FileAttributes(
    @SerializedName("type_description")
    val typeDescription: String,

    val tlsh: String,
    val vhash: String,

    @SerializedName("type_tags")
    val typeTags: List<String>,

    val names: List<String>,

    @SerializedName("last_modification_date")
    val lastModificationDate: Long,

    @SerializedName("type_tag")
    val typeTag: String,

    @SerializedName("times_submitted")
    val timesSubmitted: Int,

    @SerializedName("total_votes")
    val totalVotes: TotalVotes,

    val size: Int,

    @SerializedName("type_extension")
    val typeExtension: String,

    @SerializedName("last_submission_date")
    val lastSubmissionDate: Long,

    @SerializedName("last_analysis_results")
    val lastAnalysisResults: Map<String, FileAnalysisResult>,

    @SerializedName("crowdsourced_ids_stats")
    val crowdsourcedIdsStats: CrowdsourcedIdsStats,

    val trid: List<Trid>,

    @SerializedName("last_analysis_stats")
    val lastAnalysisStats: LastAnalysisStats,

    @SerializedName("sandbox_verdicts")
    val sandboxVerdicts: Map<String, SandboxVerdict>,

    val sha256: String,
    val tags: List<String>,

    @SerializedName("crowdsourced_ids_results")
    val crowdsourcedIdsResults: List<CrowdsourcedIdsResult>,

    @SerializedName("last_analysis_date")
    val lastAnalysisDate: Long,

    @SerializedName("unique_sources")
    val uniqueSources: Int,

    @SerializedName("first_submission_date")
    val firstSubmissionDate: Long,

    val sha1: String,
    val ssdeep: String,

    @SerializedName("bundle_info")
    val bundleInfo: BundleInfo,

    val md5: String,

    @SerializedName("androguard")
    val androguard: AndroguardInfo,

    val magic: String,
    val permhash: String,

    @SerializedName("meaningful_name")
    val meaningfulName: String,

    val reputation: Int
)

data class TotalVotes(
    val harmless: Int,
    val malicious: Int
)

data class FileAnalysisResult(
    val category: String,
    @SerializedName("engine_name") val engineName: String,
    @SerializedName("engine_version") val engineVersion: String,
    val result: String?,
    val method: String,
    @SerializedName("engine_update") val engineUpdate: String
)

data class CrowdsourcedIdsStats(
    val high: Int,
    val info: Int,
    val medium: Int,
    val low: Int
)

data class Trid(
    @SerializedName("file_type") val fileType: String,
    val probability: Double
)

data class LastAnalysisStats(
    val harmless: Int,
    @SerializedName("type-unsupported") val typeUnsupported: Int,
    val suspicious: Int,
    @SerializedName("confirmed-timeout") val confirmedTimeout: Int,
    val timeout: Int,
    val failure: Int,
    val malicious: Int,
    val undetected: Int
)

data class SandboxVerdict(
    val category: String,
    val confidence: Int,
    @SerializedName("sandbox_name") val sandboxName: String,
    @SerializedName("malware_classification") val malwareClassification: List<String>
)

data class CrowdsourcedIdsResult(
    @SerializedName("rule_category") val ruleCategory: String,
    @SerializedName("alert_severity") val alertSeverity: String,
    @SerializedName("rule_msg") val ruleMsg: String,
    @SerializedName("rule_raw") val ruleRaw: String,
    @SerializedName("alert_context") val alertContext: List<AlertContext>,
    @SerializedName("rule_url") val ruleUrl: String,
    @SerializedName("rule_source") val ruleSource: String,
    @SerializedName("rule_id") val ruleId: String
)

data class AlertContext(
    val url: String,
    val hostname: String,
    @SerializedName("dest_ip") val destIp: String,
    @SerializedName("dest_port") val destPort: Int
)

data class BundleInfo(
    @SerializedName("highest_datetime") val highestDatetime: String,
    @SerializedName("lowest_datetime") val lowestDatetime: String,
    @SerializedName("num_children") val numChildren: Int,
    val extensions: Map<String, Int>,
    @SerializedName("file_types") val fileTypes: Map<String, Int>,
    val type: String,
    @SerializedName("uncompressed_size") val uncompressedSize: Long
)

data class AndroguardInfo(
    @SerializedName("VTAndroidInfo") val vtAndroidInfo: Int,
    @SerializedName("AndroidApplicationError") val androidApplicationError: Boolean,
    @SerializedName("MinSdkVersion") val minSdkVersion: String,
    @SerializedName("AndroguardVersion") val androguardVersion: String,
    val Activities: List<String>,
    val certificate: CertificateInfo,
    @SerializedName("AndroidApplication") val androidApplication: Int,
    @SerializedName("RiskIndicator") val riskIndicator: RiskIndicator,
    val Services: List<String>,
    @SerializedName("AndroidVersionCode") val androidVersionCode: String,
    @SerializedName("main_activity") val mainActivity: String,
    val Package: String,
    @SerializedName("intent_filters") val intentFilters: Map<String, IntentFilter>,
    @SerializedName("AndroidVersionName") val androidVersionName: String,
    @SerializedName("TargetSdkVersion") val targetSdkVersion: String,
    @SerializedName("AndroidApplicationInfo") val androidApplicationInfo: String,
    val Providers: List<String>,
    @SerializedName("permission_details") val permissionDetails: Map<String, PermissionDetail>,
    val Receivers: List<String>,
    @SerializedName("StringsInformation") val stringsInformation: List<String>
)

data class CertificateInfo(
    val Subject: SubjectInfo,
    val validto: String,
    val serialnumber: String,
    val thumbprint: String,
    val validfrom: String,
    val Issuer: IssuerInfo
)

data class SubjectInfo(
    val DN: String,
    val C: String,
    val CN: String,
    val L: String,
    val O: String,
    val ST: String,
    val OU: String
)

data class IssuerInfo(
    val DN: String,
    val C: String,
    val CN: String,
    val L: String,
    val O: String,
    val ST: String,
    val OU: String
)

data class RiskIndicator(
    val PERM: Map<String, Int>
)

data class IntentFilter(
    val action: List<String>,
    val category: List<String>
)

data class PermissionDetail(
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("full_description") val fullDescription: String,
    @SerializedName("permission_type") val permissionType: String
)

data class FileLinks(
    val self: String
)
