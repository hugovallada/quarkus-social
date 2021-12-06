package com.github.hugovallada.post.model

import com.github.hugovallada.user.model.User
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Table(name = "TB_POST")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val text: String,
    @CreationTimestamp
    val createdDate: OffsetDateTime? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)