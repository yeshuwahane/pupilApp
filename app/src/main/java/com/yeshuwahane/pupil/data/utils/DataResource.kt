package com.yeshuwahane.pupil.data.utils






data class DataResource<out T> constructor(
    var resourceState: ResourceState,
    val data: T?,
    val error: Throwable?,
    val loadingPercentage: Int? = null
) {
    /**
     * use the below companion functions to quickly create the [DataResource] instance
     */
    companion object {
        fun <T> initial(data: T? = null): DataResource<T> {
            return DataResource(ResourceState.INITIAL, data, null)
        }

        fun <T> loading(percentage: Int? = null, data: T? = null): DataResource<T> {
            return DataResource(ResourceState.LOADING, data, null, percentage)
        }

        fun <T> success(data: T): DataResource<T> {
            return DataResource(ResourceState.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable?, data: T? = null): DataResource<T> {
            return DataResource(ResourceState.ERROR, data, error)
        }
    }

    /** [isLoading], [isSuccess], [isError] helps us in doing quick comparison for [resourceState]*/
    fun isLoading() = resourceState == ResourceState.LOADING

    fun isSuccess() = resourceState == ResourceState.SUCCESS

    fun isError() = resourceState == ResourceState.ERROR
}

enum class ResourceState {
    INITIAL,
    LOADING,
    SUCCESS,
    ERROR
}