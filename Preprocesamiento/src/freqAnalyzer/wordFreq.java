
package freqAnalyzer;


public class wordFreq {
    private String word = "";
    private int freq = 0;

    public wordFreq(String word, int freq) {
        this.word = word;
        this.freq = freq;
    }

    public String getWord() {
        return word;
    }
    
    public int getFreq() {
        return freq;
    }
    
    public boolean esIgual(wordFreq par) {
        String[] otroPar = par.getWord().split(" ");
        // Si tienen las mismas palabras en distinto orden
        if (word.contains(otroPar[0]) && word.contains(otroPar[1]))
            freq += par.getFreq();
        return (word.contains(otroPar[0]) && word.contains(otroPar[1]));
    }

    @Override
    public String toString() {
        return "" + word + "\t\t\t\t-> " + freq;
    }
}
