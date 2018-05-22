package com.onwordiesquire.android.githubsearch.domain

import com.onwordiesquire.android.githubsearch.data.dto.ItemDto
import com.onwordiesquire.android.githubsearch.data.dto.OwnerDto
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import com.onwordiesquire.android.githubsearch.data.repository.DataRepository
import io.reactivex.Single
import javax.inject.Inject

class SearchForRepositoryUseCase @Inject constructor(private val dataRepository: DataRepository) {

    fun fetchResults(searchTerm: String, pageNo: Int): Single<UseCaseResponse> {
        return dataRepository.searchForRepository(searchTerm = searchTerm, pageNo = pageNo)
                .flatMap { response ->
                    when {
                        response.isSuccessful -> mapDtoToUseCaseResponse(response.body())
                        else -> UseCaseResponse(ResultState.Failure())
                    }.run { Single.just(this) }
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
            if (repoPage.isDefault()) ResultState.Failure() else ResultState.Success(repoPage)

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
        } ?: listOf()
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
}