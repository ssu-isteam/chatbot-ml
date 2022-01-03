package dev.isteam.chatbot.dl.api.dataset.loader

import dev.isteam.chatbot.dl.api.dataset.iterator.RawDataSetIterator
import dev.isteam.chatbot.dl.api.dataset.preprocessor.KoreanTokenPreprocessor
import dev.isteam.chatbot.dl.api.tokenizer.KoreanTokenizerFactory
import org.deeplearning4j.models.embeddings.WeightLookupTable
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.models.word2vec.VocabWord
import org.deeplearning4j.models.word2vec.Word2Vec
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache
import org.junit.jupiter.api.Test
import java.io.File


internal class MINDsLabDataSetLoaderTest {

    @Test
    fun load() {
        var path = "C:\\Users\\Singlerr\\Desktop\\ISTEAM\\AI 데이터 셋\\일반상식\\02_squad_질문_답변_제시문_말뭉치\\ko_wiki_v1_squad.json"
        var loader = MINDsLabDataSetLoader(path)
        var packedRawDataSet = loader.load().get()

        var tokenizerFactory = KoreanTokenizerFactory()
        tokenizerFactory.tokenPreProcessor = KoreanTokenPreprocessor()

        var iterator = RawDataSetIterator(packedRawDataSet,RawDataSetIterator.IterativeType.QUESTION)
        val cache = InMemoryLookupCache()
        val table: WeightLookupTable<VocabWord> = InMemoryLookupTable.Builder<VocabWord>()
            .vectorLength(100)
            .useAdaGrad(false)
            .cache(cache)
            .lr(0.025).build()

        val vec: Word2Vec = Word2Vec.Builder()
            .minWordFrequency(5)
            .iterations(1)
            .epochs(1)
            .layerSize(100)
            .batchSize(100000)
            .seed(42)
            .windowSize(5)
            .iterate(iterator)
            .tokenizerFactory(tokenizerFactory)
            .lookupTable(table)
            .vocabCache(cache)
            .build()
        vec.fit()
    }
}