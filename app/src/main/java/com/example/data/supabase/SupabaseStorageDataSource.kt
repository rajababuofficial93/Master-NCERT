package com.example.data.supabase

import java.io.File
import kotlin.time.Duration.Companion.hours

class SupabaseStorageDataSource {
    private val supabase = SupabaseClientProvider.client

    suspend fun createSignedUrl(bucket: String, path: String, expiresInHours: Int = 1): String =
        supabase.storage.from(bucket).createSignedUrl(path = path, expiresIn = expiresInHours.hours)

    suspend fun downloadToFile(bucket: String, path: String, destination: File) {
        destination.parentFile?.mkdirs()
        supabase.storage.from(bucket).downloadAuthenticatedTo(path, destination)
    }

    suspend fun downloadBytes(bucket: String, path: String): ByteArray =
        supabase.storage.from(bucket).downloadAuthenticated(path)

    suspend fun uploadBytes(bucket: String, path: String, data: ByteArray, upsert: Boolean = false) {
        supabase.storage.from(bucket).upload(path, data) { this.upsert = upsert }
    }

    suspend fun uploadFile(bucket: String, path: String, file: File, upsert: Boolean = false) {
        supabase.storage.from(bucket).upload(path, file) { this.upsert = upsert }
    }

    suspend fun delete(bucket: String, path: String) {
        supabase.storage.from(bucket).delete(path)
    }

    fun lectureVideoPath(lectureId: String, fileName: String = "video.mp4") = "$lectureId/$fileName"
    fun lectureNotesPath(lectureId: String, fileName: String = "notes.pdf") = "$lectureId/$fileName"
    fun dppFilePath(dppId: String, fileName: String) = "$dppId/$fileName"
    fun assignmentPath(batchId: String, fileName: String) = "$batchId/$fileName"
    fun batchAssetPath(batchId: String, fileName: String) = "$batchId/$fileName"
    fun avatarPath(userId: String, fileName: String = "profile.webp") = "$userId/$fileName"
}
