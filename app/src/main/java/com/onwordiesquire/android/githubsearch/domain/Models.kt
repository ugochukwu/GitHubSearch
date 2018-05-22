package com.onwordiesquire.android.githubsearch.domain


data class RepoPage(val totalCount: Int = -1,
                    val incompleteResult: Boolean = false,
                    val repositoryList: RepositoryList = emptyList()) : DomainModel {
    override fun isDefault(): Boolean = totalCount == -1 && !incompleteResult && repositoryList.isEmpty()
}

data class Repo(val id: Int = -1,
                val name: String = "",
                val fullName: String = "",
                val owner: RepoOwner = RepoOwner(),
                val description: String = "",
                val fork: Boolean = false,
                val forksUrl: String = "",
                val keysUrl: String = "",
                val subscribersUrl: String = "",
                val forksCount: Int = -1,
                val forks: Int = -1) : DomainModel {
    override fun isDefault() =
            (id == -1 && name.isEmpty() && fullName.isEmpty() && owner.isDefault() && description.isEmpty()
                    && !fork && forksUrl.isEmpty() && keysUrl.isEmpty() && subscribersUrl.isEmpty()
                    && forksCount == -1 && forks == -1)
}

data class RepoOwner(val login: String = "",
                     val id: Int = -1,
                     val avatarUrl: String = "",
                     val gravatarId: String = "",
                     val type: String = "") : DomainModel {
    override fun isDefault() =
            login.isEmpty() && id == -1 && avatarUrl.isEmpty() && gravatarId.isEmpty() && type.isEmpty()
}

interface DomainModel {
    fun isDefault(): Boolean
}

typealias RepositoryList = List<Repo>