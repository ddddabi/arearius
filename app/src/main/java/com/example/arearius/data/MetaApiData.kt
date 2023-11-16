package com.example.arearius.data

import com.google.gson.annotations.SerializedName

data class ScanResult(
    @SerializedName("scan_result_history_length") val scanResultHistoryLength: Int,
    @SerializedName("file_id") val fileId: String,
    @SerializedName("data_id") val dataId: String,
    @SerializedName("sanitized") val sanitized: Sanitized,
    @SerializedName("process_info") val processInfo: ProcessInfo,
    @SerializedName("scan_results") val scanResults: ScanResults,
    @SerializedName("file_info") val fileInfo: FileInfo,
    @SerializedName("share_file") val shareFile: Int,
    @SerializedName("rest_version") val restVersion: String,
    @SerializedName("additional_info") val additionalInfo: List<Any>,
    @SerializedName("votes") val votes: Votes
)

data class Sanitized(
    @SerializedName("reason") val reason: String,
    @SerializedName("result") val result: String
)

data class ProcessInfo(
    @SerializedName("result") val result: String,
    @SerializedName("profile") val profile: String,
    @SerializedName("post_processing") val postProcessing: PostProcessing,
    @SerializedName("file_type_skipped_scan") val fileTypeSkippedScan: Boolean,
    @SerializedName("blocked_reason") val blockedReason: String
)

data class PostProcessing(
    @SerializedName("copy_move_destination") val copyMoveDestination: String,
    @SerializedName("converted_to") val convertedTo: String,
    @SerializedName("converted_destination") val convertedDestination: String,
    @SerializedName("actions_ran") val actionsRan: String,
    @SerializedName("actions_failed") val actionsFailed: String
)

data class ScanResults(
    @SerializedName("scan_details") val scanDetails: Map<String, ScanDetail>,
    @SerializedName("scan_all_result_i") val scanAllResultI: Int,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("total_time") val totalTime: Int,
    @SerializedName("total_avs") val totalAvs: Int,
    @SerializedName("total_detected_avs") val totalDetectedAvs: Int,
    @SerializedName("progress_percentage") val progressPercentage: Int,
    @SerializedName("scan_all_result_a") val scanAllResultA: String
)

data class ScanDetail(
    @SerializedName("threat_found") val threatFound: String,
    @SerializedName("scan_time") val scanTime: Int,
    @SerializedName("scan_result_i") val scanResultI: Int,
    @SerializedName("def_time") val defTime: String
)

data class FileInfo(
    @SerializedName("file_size") val fileSize: Int,
    @SerializedName("upload_timestamp") val uploadTimestamp: String,
    @SerializedName("md5") val md5: String,
    @SerializedName("sha1") val sha1: String,
    @SerializedName("sha256") val sha256: String,
    @SerializedName("file_type_category") val fileTypeCategory: String,
    @SerializedName("file_type_description") val fileTypeDescription: String,
    @SerializedName("file_type_extension") val fileTypeExtension: String,
    @SerializedName("display_name") val displayName: String
)

data class Votes(
    @SerializedName("up") val up: Int,
    @SerializedName("down") val down: Int
)
