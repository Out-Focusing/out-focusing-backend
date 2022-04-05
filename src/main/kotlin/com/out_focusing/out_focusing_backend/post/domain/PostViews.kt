package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.annotation.CreatedDate
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(name = "post_views", uniqueConstraints = [UniqueConstraint(columnNames = ["post_id", "reader_user_id", "read_date"])])
class PostViews(
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post,

    @JoinColumn(name = "reader_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val readerUserProfile: UserProfile,

    @CreatedDate
    @Column(name = "read_date")
    val readDate: Date,
) {
    @Id
    @Column(name = "post_views_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postViewsId: Long = 0
}