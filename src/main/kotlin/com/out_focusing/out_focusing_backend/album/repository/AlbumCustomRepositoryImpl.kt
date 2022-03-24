package com.out_focusing.out_focusing_backend.album.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
@Repository
class AlbumCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): AlbumCustomRepository {

}