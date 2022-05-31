package model

data class SummarizedItem(
    val title: String,
    val count: Int
) {
    operator fun plus(other: SummarizedItem): SummarizedItem {
        return this.copy(count = this.count + other.count)
    }
}