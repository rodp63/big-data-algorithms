import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class InvertedIndex {
  public static class TokenizerMapper
      extends Mapper<Object, Text, Text, Text> {

    private Text word = new Text();
    private Text docID = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      String doc_id = value.toString().substring(0, value.toString().indexOf("$"));
      String value_raw =  value.toString().substring(value.toString().indexOf("$") + 1);

      StringTokenizer itr = new StringTokenizer(value_raw);
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        docID.set(doc_id);
        context.write(word, docID);
      }
    }
  }

  public static class JoinTextReducer
      extends Reducer<Text, Text, Text, Text> {

    private Text docList = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context
                       ) throws IOException, InterruptedException {
      HashMap<String,Integer> map = new HashMap<String,Integer>();
      for (Text val : values) {
        if (map.containsKey(val.toString())) {
          map.put(val.toString(), map.get(val.toString()) + 1);
        } else {
          map.put(val.toString(), 1);
        }
      }

      StringBuilder docValueList = new StringBuilder();
      for(String docID : map.keySet()) {
        docValueList.append(docID + ":[" + map.get(docID) + "] ");
      }
      docList.set(docValueList.toString());
      context.write(key, docList);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "inverted index");
    job.setJarByClass(InvertedIndex.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setReducerClass(JoinTextReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
