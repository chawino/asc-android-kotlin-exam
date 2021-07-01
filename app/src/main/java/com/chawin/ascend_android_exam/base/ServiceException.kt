package com.chawin.ascend_android_exam.base

data class ServiceException(
    var errorCd: String,
    override var message: String?
) : Exception() {

    constructor(errorCd: String) : this(errorCd, null)

    constructor(exception: ServiceException, message: String?) : this(exception.errorCd, message)

    override fun toString(): String = "Error [$errorCd] ~> $message"
}
