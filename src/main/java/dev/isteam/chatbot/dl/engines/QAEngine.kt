package dev.isteam.chatbot.dl.engines

import dev.isteam.chatbot.dl.api.dataset.DataSetLoader
import dev.isteam.chatbot.dl.api.dataset.PackedRawDataSet
import dev.isteam.chatbot.dl.api.tokenizer.KoreanTokenizerFactory
import dev.isteam.chatbot.dl.api.vector.KoreanTfidfVectorizer
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.util.ModelSerializer
import java.io.File
import java.io.InputStream

class QAEngine {

    private var koreanTfidfVectorizer: KoreanTfidfVectorizer? = null
    private var packedRawDataSet: PackedRawDataSet? = null
    private var network: MultiLayerNetwork? = null
    fun initEngine(vocabCachePath: String, modelPath: String, dataSetLoaders: Array<DataSetLoader>) {
        packedRawDataSet = PackedRawDataSet()
        for (dataSetLoader in dataSetLoaders) {
            var part = dataSetLoader.load().get()
            packedRawDataSet!!.rawDataSets.plus(part.rawDataSets)
        }
        var vocabCache = WordVectorSerializer.readVocabCache(File(vocabCachePath))

        var koreanTokenizerFactory = KoreanTokenizerFactory()

        koreanTfidfVectorizer = KoreanTfidfVectorizer(
            koreanTokenizerFactory = koreanTokenizerFactory,
            packedRawDataSet = packedRawDataSet!!,
            cache = vocabCache
        )

        network = ModelSerializer.restoreMultiLayerNetwork(modelPath)
    }

    fun initEngine(vocabCachePath: File, modelPath: File, dataSetLoaders: Array<DataSetLoader>) {
        packedRawDataSet = PackedRawDataSet()
        for (dataSetLoader in dataSetLoaders) {
            var part = dataSetLoader.load().get()
            packedRawDataSet!!.rawDataSets.plus(part.rawDataSets)
        }
        var vocabCache = WordVectorSerializer.readVocabCache(vocabCachePath)

        var koreanTokenizerFactory = KoreanTokenizerFactory()

        koreanTfidfVectorizer = KoreanTfidfVectorizer(
            koreanTokenizerFactory = koreanTokenizerFactory,
            packedRawDataSet = packedRawDataSet!!,
            cache = vocabCache
        )

        network = ModelSerializer.restoreMultiLayerNetwork(modelPath)
    }

    fun initEngine(vocabCachePath: InputStream, modelPath: InputStream, dataSetLoaders: Array<DataSetLoader>) {
        packedRawDataSet = PackedRawDataSet()
        for (dataSetLoader in dataSetLoaders) {
            var part = dataSetLoader.load().get()
            packedRawDataSet!!.rawDataSets.plus(part.rawDataSets)
        }
        var vocabCache = WordVectorSerializer.readVocabCache(vocabCachePath)

        var koreanTokenizerFactory = KoreanTokenizerFactory()

        koreanTfidfVectorizer = KoreanTfidfVectorizer(
            koreanTokenizerFactory = koreanTokenizerFactory,
            packedRawDataSet = packedRawDataSet!!,
            cache = vocabCache
        )

        network = ModelSerializer.restoreMultiLayerNetwork(modelPath)
    }

    fun getAnswer(question: String): String {
        var answerIndex = network!!.predict(koreanTfidfVectorizer!!.transform(question))[0]
        return packedRawDataSet!!.rawDataSets[answerIndex].answer
    }
}