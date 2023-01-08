package servletpack;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class nlpPipeline {
    static StanfordCoreNLP pipeline;
    public static void init() 
    {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }
    public  static String estimatingSentiment(String text)
    {
   int sentimentInt;
      String sentimentName; 
      String ans = "";
      Annotation annotation = pipeline.process(text);
      for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
      {
         Tree tree = sentence.get(SentimentAnnotatedTree.class);
        sentimentInt = RNNCoreAnnotations.getPredictedClass(tree); 
                sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
        ans = "Your Message is "+sentimentName + " it has rating " + sentimentInt + " out of 4 ||||Message : " + sentence;
      }
      return ans;
     }
}

