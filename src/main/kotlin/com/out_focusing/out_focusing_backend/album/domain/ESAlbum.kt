package com.out_focusing.out_focusing_backend.album.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field

@Document(indexName = "album")
class ESAlbum (
    @Id
    val id: Long,
    @Field
    val title: String,
    @Field
    val content: String,
    @Field
    val writer: String
)