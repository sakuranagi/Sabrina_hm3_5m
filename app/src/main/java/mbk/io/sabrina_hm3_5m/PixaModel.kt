package mbk.io.sabrina_hm3_5m

data class PixaModel(
    val hits: List<ImageModel>
)

data class ImageModel(
    val largeImageURL: String,

)