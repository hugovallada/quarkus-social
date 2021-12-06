package com.github.hugovallada.follower.model

import com.github.hugovallada.user.model.User
import javax.persistence.*

@Entity
@Table(name = "TB_FOLLOWERS")
class Follower(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
    @ManyToOne
    @JoinColumn(name = "follower_id")
    val follower: User
)