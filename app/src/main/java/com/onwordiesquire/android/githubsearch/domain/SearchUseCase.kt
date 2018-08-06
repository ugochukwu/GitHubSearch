package com.onwordiesquire.android.githubsearch.domain

import com.onwordiesquire.android.githubsearch.data.datasource.DataSourceResponse
import com.onwordiesquire.android.githubsearch.data.dto.ItemDto
import com.onwordiesquire.android.githubsearch.data.dto.OwnerDto
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import com.onwordiesquire.android.githubsearch.data.repository.DataRepository
import javax.inject.Inject

class SearchRepositoryUseCase @Inject constructor(private val dataRepository: DataRepository) {

    suspend fun searchForRepositories(searchTerm: String, pageNo: Int): UseCaseResponse {
        return findRepo(searchTerm, pageNo).run {
            when (this) {
                is DataSourceResponse.Success -> mapDtoToUseCaseResponse(this.payload)
                is DataSourceResponse.Failure -> UseCaseResponse(ResultState.Failure())
                is DataSourceResponse.NoInternet -> UseCaseResponse(ResultState.Failure())
            }
        }
    }

    private suspend fun findRepo(name: String, pageNo: Int): DataSourceResponse<SearchResponseDto> {
        return dataRepository.searchForRepository(searchTerm = name, pageNo = pageNo)
    }
}

private fun mapDtoToUseCaseResponse(body: SearchResponseDto?): UseCaseResponse {
    return body?.let { searchResponseDto ->
        with(searchResponseDto) {
            RepoPage(totalCount = totalCount.orDefault(),
                    incompleteResult = incompleteResults.orDefault(),
                    repositoryList = mapRepositoryList(items))
        }.run {
            UseCaseResponse(result = determineResultState(this))
        }
    } ?: UseCaseResponse(result = ResultState.Failure())
}

private fun determineResultState(repoPage: RepoPage) =
        if (repoPage.isDefault()) ResultState.Empty() else ResultState.Success(repoPage)

private fun mapRepositoryList(items: List<ItemDto>?): RepositoryList {
    return items?.map { dto ->
        with(dto) {
            Repo(id = id.orDefault(),
                    name = name.orDefault(),
                    fullName = fullName.orDefault(),
                    owner = mapOwner(owner),
                    description = description.orDefault(),
                    fork = fork.orDefault(),
                    forksUrl = forksUrl.orDefault(),
                    keysUrl = keysUrl.orDefault(),
                    subscribersUrl = subscribersUrl.orDefault(),
                    forksCount = forksCount.orDefault())
        }
    } ?: emptyList()
}

private fun mapOwner(owner: OwnerDto?): RepoOwner = owner?.let {
    with(it) {
        RepoOwner(id = id.orDefault(),
                login = login.orDefault(),
                avatarUrl = avatarUrl.orDefault(),
                gravatarId = gravatarId.orDefault(),
                type = type.orDefault())
    }
} ?: RepoOwner()