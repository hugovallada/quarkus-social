package com.github.hugovallada.errors.customErrors

import java.lang.RuntimeException

class AlreadyFollowingException(msg: String) : RuntimeException(msg)
