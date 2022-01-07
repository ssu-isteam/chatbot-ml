package dev.isteam.chatbot.dl.engines

import dev.isteam.chatbot.dl.api.dataset.loader.MINDsLabDataSetLoader
import dev.isteam.chatbot.dl.api.dataset.preprocessor.KoreanTokenPreprocessor
import dev.isteam.chatbot.dl.api.tokenizer.KoreanTokenizerFactory
import dev.isteam.chatbot.dl.api.vector.KoreanTfidfVectorizer
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.util.ModelSerializer
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.util.*

internal class QAEngineTest {

    @Test
    fun initEngine() {
        var path = "C:\\Users\\Singlerr\\Desktop\\ISTEAM\\AI 데이터 셋\\일반상식\\02_squad_질문_답변_제시문_말뭉치\\ko_wiki_v1_squad.json"
        var path2 = "C:\\Users\\Singlerr\\Desktop\\ISTEAM\\AI 데이터 셋\\기계독해\\기계독해분야\\ko_nia_normal_squad_all.json"
        var loader = MINDsLabDataSetLoader(path)
        var loader2 = MINDsLabDataSetLoader(path2)
        var packedRawDataSet = loader.load().get()
        var packedRawDataSet2 = loader2.load().get()

        packedRawDataSet.rawDataSets.plus(packedRawDataSet2.rawDataSets)

        var tokenizerFactory = KoreanTokenizerFactory()
        tokenizerFactory.tokenPreProcessor = KoreanTokenPreprocessor()

        var vocabCache = WordVectorSerializer.readVocabCache(File("tfidVocab.cache"))
        var koreanTfidfVectorizer = KoreanTfidfVectorizer(koreanTokenizerFactory = tokenizerFactory, packedRawDataSet = packedRawDataSet, cache = vocabCache)

        var network = ModelSerializer.restoreMultiLayerNetwork("network.model")

        var result = network.predict(koreanTfidfVectorizer.transform("서울과 충북 괴산에서 '국제청소년포럼'을 여는 곳은?"))

        var answerPos = result[0]

        var answer = packedRawDataSet.rawDataSets[answerPos].answer
        println(answer)
    }
}