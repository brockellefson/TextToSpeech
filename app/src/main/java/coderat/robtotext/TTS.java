package coderat.robtotext;

/**
 * Created by Brock on 3/28/2018.
 */
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class TTS extends Thread implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private  Context context;
    public Handler handler;
    private String last;


    TTS(Context con){
        context = con;
        tts = new TextToSpeech(con, this);
        last = " ";
    }

    public void onInit(int status){
        if (status == TextToSpeech.SUCCESS){
            tts.setPitch(0);
            tts.setSpeechRate(0);
        }
        int result = tts.setLanguage(Locale.US);

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
            Toast.makeText(context, "yo i can speak this wookie crap", Toast.LENGTH_LONG).show();
        }
    }

    public void run(){
        Looper.prepare();
/*            handler = new Handler() {
                public void handleMessage(Message msg){
                    String response = msg.getData().getString("TT");
                    speak(response);
                }
            };*/
        Looper.loop();
    }

    public void speak(String text){
        if(last != text){
            last = text;
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

            while (tts.isSpeaking()){
                try {
                    Thread.sleep(200);
                }
                catch (Exception e){
                    //gross
                }
            }

        }
    }
}
