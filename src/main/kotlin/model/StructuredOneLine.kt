package model

data class StructuredOneLine(val line: OneLine, val child: MutableList<StructuredOneLine>)