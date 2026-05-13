import speech_recognition as sr
import sys

def listen():
    r=sr.Recognizer()
    with sr.Microphone() as source:
        try:
            r.adjust_for_ambient_noise(source, duration=0.2)
            audio = r.listen(source, timeout=3, phrase_time_limit=4)
            text = r.recognize_google(audio, language="en-US")
            print(text)
            sys.stdout.flush()
        except:
            print("Error")
            sys.stdout.flush()

if __name__ == "__main__":
    listen()