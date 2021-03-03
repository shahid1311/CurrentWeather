package com.synerzip.currentweather.network

/**
 * Network Exception for API call failures, wrapping the [ErrorResponseModel]
 *
 * @property errorResponseModel
 */
class NetworkException(val errorResponseModel: ErrorResponseModel) :
    Throwable(errorResponseModel.message)