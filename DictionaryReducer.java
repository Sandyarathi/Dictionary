package Dictionary;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class DictionaryReducer extends Reducer<Text,Text,Text,Text> {
   
    //English-word: [part of speech] french:french-translation|german:german-translation|
    //italian:italian-translation|portuguese:portuguese-translation|spanish:spanish-translation
    private Text result = new Text();
    public void reduce(Text word, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
        boolean french=false;
        boolean german=false;
        boolean italian=false;
        boolean portugese=false;
        boolean spanish=false;

        String frenchWord="";
        String germanWord="";
        String italianWord="";
        String portugeseWord="";
        String spanishWord="";


        String translations = "";
        for (Text val : values){
            String[] language = val.toString().split(":",2);
            if(language[0].equals("french")){
                french=true;
                frenchWord=listValues(language[1]);
            }
            else if(language[0].equals("german")){
                german=true;
                germanWord=listValues(language[1]);
            }
            else if(language[0].equals("italian")){
                italian=true;
                italianWord=listValues(language[1]);
            }
            else if(language[0].equals("portugese")){
                portugese=true;
                portugeseWord=listValues(language[1]);
            }
            else if(language[0].equals("spanish")){
                spanish=true;
                spanishWord=listValues(language[1]);
            }
                
        }
        if(german==false){
            germanWord= "german : N/A";
        }
        if(italian == false) {
            italianWord= "italian : N/A";
        }
        if(french == false) {
            frenchWord= "french : N/A";
        }
        if(portugese == false) {
            portugeseWord= "portugese : N/A";
        }
        if(spanish == false) {
            spanishWord= "spanish : N/A";
        }
        
        translations += frenchWord +" | "+germanWord+" | "+italianWord+" | "+portugeseWord+" | "+spanishWord;
        result.set(translations);
        context.write(word, result);
   }

   private String listValues(String word){
        String result = "";
        String[]tokens = word.split(",|;");
        if(tokens.length >1){
            for(int i=0;i<tokens.length;i++){
                result+=tokens[i]+',';
            }
            result = result.substring(0,result.length()-1);
        }
        else
            result=word;
        return result;

   }
    
}
  