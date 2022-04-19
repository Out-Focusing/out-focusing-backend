package com.out_focusing.out_focusing_backend.post.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field

@Document(indexName = "post")
class ESPost(
    @Id
    val id: Long,
    @Field
    val hashtag: List<String>,
    @Field(name = "title")
    val albumTitle: String,
    @Field
    val writer: String
)