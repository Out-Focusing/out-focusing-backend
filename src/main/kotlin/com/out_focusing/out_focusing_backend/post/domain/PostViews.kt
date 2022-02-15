package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.annotation.CreatedDate
import java.util.Date
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "post_views")
class PostViews(
    @EmbeddedId
    val postViewsId: PostViewsId
) {

    @Embeddable
    data class PostViewsId(
        @JoinColumn(name = "post_id")
        @ManyToOne(fetch = FetchType.LAZY)
        val post: Post,
        @JoinColumn(name = "reader_user_id")
        @ManyToOne(fetch = FetchType.LAZY)
        val readerUserProfile: UserProfile,
        @CreatedDate
        @Column(name = "read_date")
        val readDate: Date
    ) : java.io.Serializable

}