package com.github.hugovallada.errors.customErrors

import java.lang.RuntimeException

class NotFoundException(msg: String) : RuntimeException(msg)