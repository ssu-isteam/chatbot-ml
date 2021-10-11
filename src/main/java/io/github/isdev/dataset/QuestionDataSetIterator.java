package io.github.isdev.dataset;

import au.com.bytecode.opencsv.CSVReader;
import io.github.isdev.iterator.CSVSentenceIterator;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.util.FeatureUtil;
import org.nd4j.shade.guava.base.Functions;
import org.nd4j.shade.guava.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionDataSetIterator implements DataSetIterator {

    private final CSVReader reader;
    private TfidfVectorizer converter;
    private int nOut;
    private int nIn;

    private Iterator<String> questionIter;

    private final HashMap<Integer, String> answers;

    private List<String> labels;

    private LabelsSource source;

    private DataSetPreProcessor preProcessor;

    private int pos = 0;

    private QuestionDataSetIterator(File input, TfidfVectorizer converter, int answersCount) throws IOException {
        this.reader = new CSVReader(new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8));
        this.converter = converter;
        this.nOut = answersCount;
        this.answers = new HashMap<>();
    }

    private QuestionDataSetIterator(File input,int labelIndex, int questionIndex) throws IOException, InterruptedException {
        this(input, null, 0);
        //CSV 파일 맨 윗줄에 있는 레이블을 제거합니다.
        reader.readNext();
        String[] row;
        while ((row = reader.readNext()) != null) {
            answers.put(nOut, row[questionIndex]);
            nOut++;
        }

        source = new LabelsSource(Lists.transform(IntStream.range(0, nOut).boxed().collect(Collectors.toList()), Functions.toStringFunction()));

        questionIter = answers.values().iterator();
        converter = new TfidfVectorizer.Builder()
                .setIterator(new CSVSentenceIterator(input, questionIndex))
                .setTokenizerFactory(new DefaultTokenizerFactory())
                .build();
        converter.fit();
        //입력 노드의 수를 정합니다.
        nIn = converter.transform(answers.get(0)).columns();

        this.labels = IntStream.range(0,nOut).boxed().map( i -> Integer.toString(i)).collect(Collectors.toList());;
    }
    @Override
    public DataSet next(int i) {
        return null;
    }

    @Override
    public int inputColumns() {
        return nIn;
    }

    @Override
    public int totalOutcomes() {
        return nOut;
    }

    @Override
    public boolean resetSupported() {
        return true;
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public void reset() {
        questionIter = answers.values().iterator();
    }

    @Override
    public int batch() {
        return 0;
    }

    @Override
    public DataSetPreProcessor getPreProcessor() {
        return preProcessor;
    }

    @Override
    public void setPreProcessor(DataSetPreProcessor dataSetPreProcessor) {
        this.preProcessor = dataSetPreProcessor;
    }

    @Override
    public List<String> getLabels() {
        return labels;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return questionIter.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public DataSet next() throws NoSuchElementException {
        String q = questionIter.next();
        INDArray feature = converter.transform(q);
        INDArray label = FeatureUtil.toOutcomeVector(source.indexOf(Integer.toString(pos)), source.size());
        DataSet dataSet = new DataSet(feature, label);
        pos++;
        return dataSet;
    }
}
