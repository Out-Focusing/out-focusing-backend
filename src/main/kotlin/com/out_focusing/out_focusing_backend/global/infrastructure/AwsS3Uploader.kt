package com.out_focusing.out_focusing_backend.global.infrastructure

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class AwsS3Uploader(
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}")
    val bucket: String
) {

    private fun generateUrl(fileName: String, httpMethod: HttpMethod): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR, 1)

        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucket, fileName).apply {
            withMethod(httpMethod)
            withExpiration(calendar.time)
            addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString())
        }

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toExternalForm()
    }

    fun save(extension: String): String {
        val fileName = UUID.randomUUID().toString() + extension
        return generateUrl(fileName, HttpMethod.PUT)
    }

}