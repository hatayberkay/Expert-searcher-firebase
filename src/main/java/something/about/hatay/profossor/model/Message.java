package something.about.hatay.profossor.model;

public class Message {



    String mesajText;
    String gonderici;
    String sıfat;

    public Message() {
    }

    public Message(String mesajText, String gonderici, String zaman) {
        this.mesajText = mesajText;
        this.gonderici = gonderici;
        this.sıfat = zaman;
    }

    public String getMesajText() {
        return mesajText;
    }

    public void setMesajText(String mesajText) {
        this.mesajText = mesajText;
    }

    public String getGonderici() {
        return gonderici;
    }

    public void setGonderici(String gonderici) {
        this.gonderici = gonderici;
    }

    public String getZaman() {
        return sıfat;
    }

    public void setZaman(String zaman) {
        this.sıfat = zaman;
    }
}
