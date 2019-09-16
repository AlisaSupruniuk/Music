package supruniuk.alisa;

import javax.sound.midi.*;

public class Play {

    private Synthesizer synthesizer;
    private MidiChannel channel;

    public Play() throws MidiUnavailableException {
        synthesizer = MidiSystem.getSynthesizer();
        Soundbank soundbank = synthesizer.getDefaultSoundbank();
        synthesizer.loadAllInstruments(soundbank);
        synthesizer.open();

        channel = synthesizer.getChannels()[0];

    }



    public void notePressed(int noteNumber){
        channel.noteOn(noteNumber, 100);
    }

    public void noteReleased(int noteNumber){
        channel.noteOff(noteNumber);
    }

}
