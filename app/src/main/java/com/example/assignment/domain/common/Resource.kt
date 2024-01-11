package com.example.assignment.domain.common

/**
 * The wrapper sealed class used to encapsulate the result of some operation in the data layer of
 * the app. Usually, an instance of this class is returned by the functions of the Repository
 * classes and passed to the domain layer, specifically to UseCase classes. UseCase classes
 * consume the [Resource] objects and handle them accordingly.
 */
sealed class Resource<T> {

    /**
     * Indicates the successful operation in the data layer of the app and acts as the wrapper for
     * the resulting data. In case no data is passed, just simply pass in [Unit] as a param to the
     * [data] field.
     *
     * @param data the result of the operation in the data layer of the app.
     */
    class Success<T>(val data: T) : Resource<T>()


    /**
     * Indicates an error that occurred while performing some operation on the data layer of the
     * app. Acts as a wrapper for the instance of [BaseError] class that internally encapsulates
     * the exception that took place (for more info see [BaseError] docs).
     *
     * @param error an instance of [BaseError] that encapsulates the exception and all extra data
     * that describe the occurred error.
     */
    class Failed<T>(val message: String) : Resource<T>()
    class Error<T>(val message: String) : Resource<T>()
    class Loading<T>(val loading: Boolean) : Resource<T>()
}
