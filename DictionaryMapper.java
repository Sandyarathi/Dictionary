package Dictionary;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class DictionaryMapper  extends Mapper<Text, Text, Text, Text> {
      
    //<EnglishWord>_<PartsOfSpeech> : <language>_<translatedWord>
    String language;

   public void setup(Context context) {
            String fileName =  ((FileSplit)context.getInputSplit()).getPath().getName();
            language = fileName.substring(0,fileName.length()-4);
    }
    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            if(!(key.toString().charAt(0)=='#')){
                    String[] splitValue=value.toString().split("\\[",2);
                    if(splitValue.length!=0 && splitValue.length!=1){
                            String keyMap= key + " : ["+splitValue[1].substring(0,splitValue[1].length()-1)+"]";
                            String valueMap = language+ " : "+splitValue[0];
                context.write(new Text(keyMap), new Text(valueMap));
            }
            
        }
        
    }
    
}