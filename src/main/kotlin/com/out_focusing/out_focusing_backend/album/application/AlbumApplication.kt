import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.album.dto.GenerateAlbumRequest
import com.out_focusing.out_focusing_backend.album.dto.GenerateAlbumResponse
import com.out_focusing.out_focusing_backend.album.repository.AlbumRepository
import com.out_focusing.out_focusing_backend.global.error.CustomException
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AlbumApplication(
    private val albumRepository: AlbumRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    @Transactional
    fun generateAlbum(requestBody: GenerateAlbumRequest): GenerateAlbumResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile =
            userProfileRepository.findById(userId).orElseThrow { throw CustomException(HttpStatus.NOT_FOUND, "") }

        with(requestBody) {
            val generationAlbum = Album(
                writerUserProfile = userProfile,
                title = title,
                content = content,
                secret = isSecret
            )

            albumRepository.save(generationAlbum)

            return GenerateAlbumResponse(generationAlbum.albumId)
        }
    }

}