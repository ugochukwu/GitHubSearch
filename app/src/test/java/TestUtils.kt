import com.onwordiesquire.android.githubsearch.data.datasource.DataSourceResponse
import com.onwordiesquire.android.githubsearch.data.dto.ItemDto
import com.onwordiesquire.android.githubsearch.data.dto.OwnerDto
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto

fun provideSampleResponse() = DataSourceResponse.Success(SearchResponseDto(
        totalCount = 1,
        incompleteResults = false,
        items = generateItemsDto(2)
), mutableMapOf())

fun generateItemsDto(numOfitems: Int) = (0..numOfitems).map {
    val name = generateFirstName(it)
    ItemDto(id = it,
            name = name,
            fullName = generateFullName(name, it),
            forksCount = 3 * it,
            fork = it.isEven,
            subscribersUrl = generateSubscribersUrl(it),
            keysUrl = "",
            forks = 3 * it,
            owner = generateOwnersDto(it),
            description = "",
            forksUrl = forksUrl)
}

private fun generateOwnersDto(numberOfOwners: Int) = OwnerDto(login = "random.code",
        avatarUrl = avatarUrl,
        id = 1,
        gravatarId = "",
        type = userType)

private fun generateFullName(name: String, it: Int) =
        "$name $it, ${if (it.isEven) "Ojo" else "Arinze"}"

private fun generateFirstName(it: Int) = if (it.isEven) "charles" else "Jane"

private fun generateSubscribersUrl(it: Int) =
        if (it.isEven) "" else subscribersUrl

val Int.isEven
    get() = this.rem(2) == 0

const val avatarUrl = "https://api.github.com/repos/exercism/kotlin/subscribers"
const val userType = "User"
const val forksUrl = "https://api.github.com/repos/exercism/kotlin/subscribers"
const val subscribersUrl = "https://api.github.com/repos/exercism/kotlin/subscribers"