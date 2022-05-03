package jp.co.yumemi.android.codeCheck.data.enumType

sealed class Urls(val url: String) {
    data class GoogleFavicon(private val iconUrl: String) :
        Urls("https://t1.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=$iconUrl/&size=64")
}
