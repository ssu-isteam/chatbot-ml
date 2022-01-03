package dev.isteam.chatbot.dl.api.vector

import dev.isteam.chatbot.dl.api.dataset.PackedRawDataSet
import dev.isteam.chatbot.dl.api.dataset.RawDataSet
import dev.isteam.chatbot.dl.api.tokenizer.KoreanTokenizerFactory
import org.deeplearning4j.models.word2vec.Word2Vec
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.DataSetPreProcessor
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import java.util.concurrent.atomic.AtomicLong

class DataSource(
    private val packedRawDataSet: PackedRawDataSet,
    private val word2Vec: Word2Vec,
    private val koreanTokenizerFactory: KoreanTokenizerFactory,
    private val batchSize: Int = 100,
    private var preProcessor: DataSetPreProcessor? = null,
    private var iterator: Iterator<RawDataSet> = packedRawDataSet.rawDataSets.iterator(),
    private val labels: MutableList<String> = IntRange(
        0,
        packedRawDataSet.rawDataSets.size
    ).map { it.toString() }.toMutableList()
) : DataSetIterator {
    /**
     * Removes from the underlying collection the last element returned by this iterator.
     */
    override fun remove() {

    }

    /**
     * Returns `true` if the iteration has more elements.
     */
    override fun hasNext(): Boolean {
        return iterator.hasNext()
    }

    /**
     * Like the standard next method but allows a
     * customizable number of examples returned
     *
     * @param num the number of examples
     * @return the next data applyTransformToDestination
     */
    override fun next(num: Int): DataSet {
        return DataSet()
    }

    /**
     * Returns the next element in the iteration.
     */
    override fun next(): DataSet {
        var ret = Nd4j.create(1,inputColumns())

        var rawDatSet = iterator.next()

        var tokenizer = koreanTokenizerFactory.create(rawDatSet.question)

        var tokens = tokenizer.tokens

        var counts = HashMap<String,AtomicLong>()

        tokens.forEach {
            if(!counts.containsKey(it))
                counts.put(it, AtomicLong(0))

            counts[it]!!.incrementAndGet()
        }

        for(i in 0 until tokens.size){
            var idx = word2Vec.vocab().indexOf(tokens[i])
            if(idx >= 0){

            }
        }


        return DataSet()
0    }

    /**
     * Input columns for the dataset
     *
     * @return
     */
    override fun inputColumns(): Int {
        return word2Vec.vocab.numWords()
    }

    /**
     * The number of labels for the dataset
     *
     * @return
     */
    override fun totalOutcomes(): Int {
        return packedRawDataSet.rawDataSets.size
    }

    /**
     * Is resetting supported by this DataSetIterator? Many DataSetIterators do support resetting,
     * but some don't
     *
     * @return true if reset method is supported; false otherwise
     */
    override fun resetSupported(): Boolean {
        return true
    }

    /**
     * Does this DataSetIterator support asynchronous prefetching of multiple DataSet objects?
     * Most DataSetIterators do, but in some cases it may not make sense to wrap this iterator in an
     * iterator that does asynchronous prefetching. For example, it would not make sense to use asynchronous
     * prefetching for the following types of iterators:
     * (a) Iterators that store their full contents in memory already
     * (b) Iterators that re-use features/labels arrays (as future next() calls will overwrite past contents)
     * (c) Iterators that already implement some level of asynchronous prefetching
     * (d) Iterators that may return different data depending on when the next() method is called
     *
     * @return true if asynchronous prefetching from this iterator is OK; false if asynchronous prefetching should not
     * be used with this iterator
     */
    override fun asyncSupported(): Boolean {
        return true
    }

    /**
     * Resets the iterator back to the beginning
     */
    override fun reset() {
        iterator = packedRawDataSet.rawDataSets.iterator()
    }

    /**
     * Batch size
     *
     * @return
     */
    override fun batch(): Int {
        return batchSize
    }

    /**
     * Set a pre processor
     *
     * @param preProcessor a pre processor to set
     */
    override fun setPreProcessor(preProcessor: DataSetPreProcessor?) {
        this.preProcessor = preProcessor
    }

    /**
     * Returns preprocessors, if defined
     *
     * @return
     */
    override fun getPreProcessor(): DataSetPreProcessor {
        return preProcessor!!
    }

    /**
     * Get dataset iterator class labels, if any.
     * Note that implementations are not required to implement this, and can simply return null
     */
    override fun getLabels(): MutableList<String> {
        return labels
    }


}