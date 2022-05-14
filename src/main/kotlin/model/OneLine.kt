package model

@JvmInline
value class OneLine(private val value: String) {

    companion object {
        private const val ITEM_IDENTIFIER = "- "
    }

    val itemString: String
        get() = value.substring(ITEM_IDENTIFIER.length until value.length)

    fun hasItem(): Boolean = value.startsWith(ITEM_IDENTIFIER)

    fun extractItem(): Item {
        return Item.create(this)
    }
}