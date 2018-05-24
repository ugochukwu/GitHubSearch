import com.onwordiesquire.android.githubsearch.data.datasource.DataSourceResponse
import com.onwordiesquire.android.githubsearch.data.dto.ItemDto
import com.onwordiesquire.android.githubsearch.data.dto.OwnerDto
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import com.onwordiesquire.android.githubsearch.domain.Repo
import com.onwordiesquire.android.githubsearch.domain.RepoOwner
import com.onwordiesquire.android.githubsearch.domain.RepoPage
import com.onwordiesquire.android.githubsearch.domain.ResultState
import com.onwordiesquire.android.githubsearch.domain.UseCaseResponse
import com.onwordiesquire.android.githubsearch.domain.orDefault
import com.onwordiesquire.android.githubsearch.utils.isEven

class SearchTestDataFactory {
    companion object {

        @JvmStatic
        inline fun <reified T> provideSampleDataSourceResponse(): T {
            val totalCount = 1
            val incompleteResults = false

            return when (T::class) {
                DataSourceResponse.Success::class -> DataSourceResponse.Success(SearchResponseDto(
                        totalCount = totalCount,
                        incompleteResults = incompleteResults,
                        items = generateItems(2)
                ), mutableMapOf()) as T
                UseCaseResponse::class -> UseCaseResponse(result = ResultState.Success(RepoPage(
                        totalCount = totalCount.orDefault(),
                        incompleteResult = incompleteResults.orDefault(),
                        repositoryList = generateItems(2)))) as T
                else -> throw IllegalArgumentException("Unsupported type ${T::class}")
            }
        }

        @JvmStatic
        inline fun <reified T> generateItems(numOfitems: Int): List<T> = (0..numOfitems).map {
            val name = generateFirstName(it)
            val id = it
            val fullName = generateFullName(name, it)
            val forksCount = 3 * it
            val fork = it.isEven
            val subscribersUrl = generateSubscribersUrl(it)
            val keysUrl = ""
            val forks = 3 * it
            val description = ""
            val forksUrl = forksUrl

            when (T::class) {
                ItemDto::class -> ItemDto(id, name, fullName, generateOwners<OwnerDto>(it), description, fork, forksUrl,
                        keysUrl, subscribersUrl, forksCount, forks) as T
                Repo::class -> Repo(id, name, fullName, generateOwners(it), description, fork, forksUrl,
                        keysUrl, subscribersUrl, forksCount, forks) as T
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }

        @JvmStatic
        inline fun <reified T> generateOwners(id: Int): T {
            val login = "random.code"
            val avatarUrl = avatarUrl
            val gravatarId = ""
            val type = userType

            return when (T::class) {
                OwnerDto::class -> OwnerDto(login = login,
                        avatarUrl = avatarUrl,
                        id = id,
                        gravatarId = gravatarId,
                        type = type) as T
                RepoOwner::class -> RepoOwner(login = login,
                        id = id,
                        avatarUrl = avatarUrl,
                        gravatarId = gravatarId,
                        type = type
                ) as T
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }

        @JvmStatic
        fun generateFullName(name: String, it: Int) =
                "$name $it, ${if (it.isEven) "Ojo" else "Arinze"}"

        @JvmStatic
        fun generateFirstName(it: Int) = if (it.isEven) "charles" else "Jane"

        @JvmStatic
        fun generateSubscribersUrl(it: Int) =
                if (it.isEven) "" else subscribersUrl

        @JvmField
        val avatarUrl = "https://api.github.com/repos/exercism/kotlin/subscribers"
        @JvmField
        val userType = "User"
        @JvmField
        val forksUrl = "https://api.github.com/repos/exercism/kotlin/subscribers"
        @JvmField
        val subscribersUrl = "https://api.github.com/repos/exercism/kotlin/subscribers"
    }
}
