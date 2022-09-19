package com.out_focusing.out_focusing_backend.album.application

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.album.domain.AlbumBookmark
import com.out_focusing.out_focusing_backend.album.dto.*
import com.out_focusing.out_focusing_backend.album.repository.AlbumBookmarkRepository
import com.out_focusing.out_focusing_backend.album.repository.AlbumRepository
import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class AlbumApplication(
    private val albumRepository: AlbumRepository,
    private val userProfileRepository: UserProfileRepository,
    private val albumBookmarkRepository: AlbumBookmarkRepository
) {

    @Transactional
    fun generateAlbum(requestBody: GenerateAlbumRequest): GenerateAlbumResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile =
            userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

        with(requestBody) {
            val generationAlbum = Album(
                writerUserProfile = userProfile,
                title = title,
                content = content,
                secret = isSecret,
                thumbnail = thumbnail
            )

            albumRepository.save(generationAlbum)

            return GenerateAlbumResponse(generationAlbum.albumId)
        }
    }

    @Transactional
    fun removeAlbum(albumId: Long) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val removeAlbum =
            albumRepository.findById(albumId).orElseThrow { AlbumNotFoundException }

        if (removeAlbum.writerUserProfile.userId != userId) {
            throw AlbumDeleteForbiddenException
        }

        albumRepository.removeAlbum(removeAlbum)
    }

    @Transactional
    fun modifyAlbum(albumId: Long, requestBody: ModifyAlbumRequest) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val modifyAlbum =
            albumRepository.findById(albumId).orElseThrow { AlbumNotFoundException }

        if (modifyAlbum.writerUserProfile.userId != userId) {
            throw AlbumUpdateForbiddenException
        }

        requestBody.apply {
            title?.apply { modifyAlbum.title = this }
            content?.apply { modifyAlbum.content = this }
            isSecret?.apply { modifyAlbum.secret = this }
        }

        albumRepository.save(modifyAlbum)
    }

    @Transactional
    fun addAlbumBookmark(albumId: Long) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile =
            userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }
        val album = albumRepository.findById(albumId).orElseThrow { AlbumNotFoundException }

        val albumBookmark = AlbumBookmark(userProfile, album)

        if (albumBookmarkRepository.existsAlbumBookmarkByUserProfileAndAlbum(userProfile, album)) {
            throw AlreadyAlbumBookmarkedException
        }

        albumBookmarkRepository.save(albumBookmark)
    }

    @Transactional
    fun cancelAlbumBookmark(albumId: Long) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile =
            userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }
        val album = albumRepository.findById(albumId).orElseThrow { AlbumNotFoundException }

        if (!albumBookmarkRepository.existsAlbumBookmarkByUserProfileAndAlbum(userProfile, album))
            throw AlbumBookmarkNotFoundException

        albumBookmarkRepository.deleteAlbumBookmarkByUserProfileAndAlbum(userProfile, album)
    }

    fun getAlbumDetail(albumId: Long): AlbumDetailResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

        val album = albumRepository.getAlbumDetail(albumId, userProfile) ?: throw AlbumNotFoundException

        return AlbumDetailResponse.toAlbumDetailResponse(album, userProfile)
    }


    fun getMyAlbum(pageable: Pageable): List<AlbumSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile =
            userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

        return albumRepository.getMyAlbum(userProfile, pageable).map { album ->
            AlbumSummaryResponse.toAlbumSummaryResponse(album, userProfile)
        }
    }

    fun getUserAlbum(userId: String, pageable: Pageable): List<AlbumSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val writer = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }
        val userProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotFoundException }

        return albumRepository.getUserAlbum(writer, pageable)
            .map { album -> AlbumSummaryResponse.toAlbumSummaryResponse(album, userProfile) }
    }

    fun searchAlbumByKeyword(keyword: String, pageable: Pageable): List<AlbumSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val userProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        val resultAlbumIds = listOf<Long>()

        return albumRepository.findAlbumsByAlbumId(resultAlbumIds, userProfile)
            .map { album -> AlbumSummaryResponse.toAlbumSummaryResponse(album, userProfile) }
    }

    fun getUpdatedBookmarkAlbum(): List<AlbumSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile =
            userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

        return albumRepository.getUpdatedBookmarkAlbumAfterDate(userProfile, LocalDateTime.now().minusDays(3))
            .map { album -> AlbumSummaryResponse.toAlbumSummaryResponse(album, userProfile) }
    }


}