package com.example.tamakkun.Service;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.springframework.stereotype.Service;


@Service
public class TextToSpeechService {
    private final String azureRegion = "eastasia";
    private final String azureKey = "2lk2N3hSt1rbqAW2npRjerwxM5S0CjnM1eCwlCt82fQa9oVnkLReJQQJ99BAAC3pKaRXJ3w3AAAYACOG79im";

    public byte[] convertTextToAudio(String text) {
        try {
            // إعداد SpeechConfig باستخدام المفتاح والمنطقة
            SpeechConfig speechConfig = SpeechConfig.fromSubscription(azureKey, azureRegion);

            // تحديد إعدادات الصوت (إخراج مباشر أو ملف)
            AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();

            // إعداد SpeechSynthesizer
            SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);

            // تحويل النص إلى صوت
            SpeechSynthesisResult result = synthesizer.SpeakText(text);

            // التحقق من النتيجةSynthesisResult
            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                return result.getAudioData(); // إرجاع الصوت كناتج
            } else {
                throw new RuntimeException("Error during TTS conversion: " + result.getReason());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert text to speech", e);
        }
    }
}