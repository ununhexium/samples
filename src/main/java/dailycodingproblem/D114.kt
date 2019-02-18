package dailycodingproblem

object D114 {
    fun reverseWordsOfASentence(s: String) =
        s.split(Regex(" ")).reversed().joinToString(separator = " ")
}
