package dev.isteam.chatbot.dl.api.dataset.loader

import dev.isteam.chatbot.dl.api.dataset.iterator.RawDataSetIterator
import dev.isteam.chatbot.dl.api.dataset.preprocessor.KoreanTokenPreprocessor
import dev.isteam.chatbot.dl.api.tokenizer.KoreanTokenizerFactory
import dev.isteam.chatbot.dl.api.vector.DataSource
import dev.isteam.chatbot.dl.api.vector.KoreanTfidfVectorizer
import dev.isteam.chatbot.dl.engines.KoreanNeuralNetwork
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.util.ModelSerializer
import org.junit.jupiter.api.Test
import java.io.File


internal class MINDsLabDataSetLoaderTest {

    @Test
    fun load() {
        /*
        var path = "C:\\Users\\Singlerr\\Desktop\\ISTEAM\\AI 데이터 셋\\일반상식\\02_squad_질문_답변_제시문_말뭉치\\ko_wiki_v1_squad.json"
        var path2 = "C:\\Users\\Singlerr\\Desktop\\ISTEAM\\AI 데이터 셋\\기계독해\\기계독해분야\\ko_nia_normal_squad_all.json"
        var loader = MINDsLabDataSetLoader(path)
        var loader2 = MINDsLabDataSetLoader(path2)
        var packedRawDataSet = loader.load().get()
        var packedRawDataSet2 = loader2.load().get()

        packedRawDataSet.rawDataSets.plus(packedRawDataSet2.rawDataSets)

        var tokenizerFactory = KoreanTokenizerFactory()
        tokenizerFactory.tokenPreProcessor = KoreanTokenPreprocessor()

        var iterator = RawDataSetIterator(packedRawDataSet = packedRawDataSet, iterativeType = RawDataSetIterator.IterativeType.QUESTION)

        val vec = KoreanTfidfVectorizer(koreanTokenizerFactory = tokenizerFactory, rawDataSetIterator = iterator,packedRawDataSet = packedRawDataSet, _minWordFrequency = 5)

        vec.fit()
        vec.buildVocab()

        WordVectorSerializer.writeVocabCache(vec.vocabCache, File("tfidVocab.cache"))

         */

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


        var batchSize = 1000

        var dataSource = DataSource(packedRawDataSet = packedRawDataSet, ktfid = koreanTfidfVectorizer, batchSize = batchSize)

        var network = KoreanNeuralNetwork.buildNeuralNetwork(vocabCache.numWords(),packedRawDataSet.rawDataSets.size)
        network.init()


        var k = 0
        println("작업 시작")
        for(i in 0 until 50){
            while(dataSource.hasNext()){
                var dataSet = dataSource.next()
                network.fit(dataSet)
                k += batchSize
                println("현재 epoch: ${i}, 현재 epoch 진행도: ${k}/${dataSource.totalOutcomes()}")
            }
            k = 0
            println("다시 시작")
            dataSource.reset()
        }
        ModelSerializer.writeModel(network,"network.model",true)

    }
}